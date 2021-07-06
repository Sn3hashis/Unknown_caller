package com.mhvmedia.unknowncaller.Activities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.AudioController;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;
import com.skyfishjy.library.RippleBackground;
import com.mhvmedia.unknowncaller.Extra.AudioPlayer;
import com.mhvmedia.unknowncaller.Extra.Function;
import com.mhvmedia.unknowncaller.Model.User;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Service.CallService;
import com.mhvmedia.unknowncaller.Variables.Variables;

import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
/** Created by AwsmCreators * */
public class CallScreenActivity extends BaseActivity {

    static final String TAG = CallScreenActivity.class.getSimpleName();

    private DatabaseReference mDatabase;
    FirebaseUser currentUser;
    Double previousPoints;
    Double callrate;
    Double finalcost;
    Handler handler;

    private AudioPlayer mAudioPlayer;
    private Timer mTimer;
    private UpdateCallDurationTask mDurationTask;
    RippleBackground rippleBackground;

    private String mCallId;

    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;
    ImageButton speaker, record;
    Boolean isLoudSpeaker = false;

    private class UpdateCallDurationTask extends TimerTask {

        @Override
        public void run() {
            CallScreenActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateCallDuration();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.callscreen);

        mAudioPlayer = new AudioPlayer(this);
        mCallDuration = (TextView) findViewById(R.id.callDuration);
        mCallDuration.setText("Duration");
        mCallerName = (TextView) findViewById(R.id.remoteUser);
        mCallState = (TextView) findViewById(R.id.callState);
        ImageButton endCallButton = (ImageButton) findViewById(R.id.hangupButton);
        speaker = findViewById(R.id.speaker);
        record = findViewById(R.id.record);
        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        handler = new Handler();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        RetriveData();

        speaker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoudSpeaker) {
                    isLoudSpeaker = false;
                    AudioController audioController = getSinchServiceInterface().getAudioController();
                    audioController.disableSpeaker();
                    speaker.setImageDrawable(getResources().getDrawable(R.drawable.ic_speaker));
                }else {
                    isLoudSpeaker = true;
                    AudioController audioController = getSinchServiceInterface().getAudioController();
                    audioController.enableSpeaker();
                    speaker.setImageDrawable(getResources().getDrawable(R.drawable.ic_mute));
                }
            }
        });


        endCallButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endCall();
            }
        });

        mCallId = getIntent().getStringExtra(CallService.CALL_ID);
        String number = getIntent().getStringExtra("number");
        callrate = Double.valueOf(Function.checkCountry(number));

        finalcost = callrate/60;

        //runTimer();
    }

    private void RetriveData() {
        mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    User user = dataSnapshot.getValue(User.class);
                    previousPoints = user.getCredits();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onServiceConnected() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            mCallerName.setText(call.getRemoteUserId());
            mCallState.setText(call.getState().toString());
        } else {
            Log.e(TAG, "Started with invalid callId, aborting.");
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mDurationTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();
        mDurationTask = new UpdateCallDurationTask();
        mTimer.schedule(mDurationTask, 0, 500);
    }

    @Override
    public void onBackPressed() {
        // User should exit activity by ending call, not by going back.
    }

    private void endCall() {
        mAudioPlayer.stopProgressTone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
            stopTimer();
        }
        rippleBackground.stopRippleAnimation();
        finish();
    }

    private String formatTimespan(int totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateCallDuration() {
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            mCallDuration.setText(formatTimespan(call.getDetails().getDuration()));
        }
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended. Reason: " + cause.toString());
            mAudioPlayer.stopProgressTone();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
            //String endMsg = "Call ended: " + call.getDetails().toString();
            //Toast.makeText(CallScreenActivity.this, endMsg, Toast.LENGTH_LONG).show();
            stopTimer();
            endCall();
        }

        @Override
        public void onCallEstablished(Call call) {
            runTimer(call);
            //Toast.makeText(CallScreenActivity.this, "call attended", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Call established");
            mAudioPlayer.stopProgressTone();
            mCallState.setText(call.getState().toString());
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            AudioController audioController = getSinchServiceInterface().getAudioController();
            audioController.disableSpeaker();
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.d(TAG, "Call progressing");
            mAudioPlayer.playProgressTone();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // Send a push through your push provider here, e.g. GCM
        }

    }

    private void runTimer(Call call) {
        if (Variables.ENABLE_ADVANCE_CREDITS_DEDUCTION){
            Double TotalPoints = previousPoints-=callrate;
            mDatabase.child("users").child(currentUser.getUid()).child("credits").setValue(TotalPoints);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (previousPoints >= 1){
                    if (Variables.ENABLE_ADVANCE_CREDITS_DEDUCTION){
                        if (call.getDetails().getDuration() >= 60){
                            Double TotalPoints = previousPoints-=finalcost;
                            mDatabase.child("users").child(currentUser.getUid()).child("credits").setValue(TotalPoints);
                        }
                    }else {
                        Double TotalPoints = previousPoints-=finalcost;
                        mDatabase.child("users").child(currentUser.getUid()).child("credits").setValue(TotalPoints);
                    }
                }else {
                    stopTimer();
                    endCall();
                }
                //Do something after 20 seconds
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void stopTimer(){
        handler.removeCallbacksAndMessages(null);
    }
}
