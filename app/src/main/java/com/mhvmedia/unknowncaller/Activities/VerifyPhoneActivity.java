package com.mhvmedia.unknowncaller.Activities;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticImageView;
import com.mhvmedia.unknowncaller.Model.User;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Variables.Variables;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
/** Created by AwsmCreators * */
public class VerifyPhoneActivity extends AppCompatActivity {
    private static final String TAG = "PhoneAuth";
    EditText et1, et2, et3, et4, et5, et6;
    Button verify;
    Button resend;
    Dialog dialog;

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth mAuth;
    String number;
    ElasticImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); }

        changeStatusBarColor();

        Intent intent = getIntent();
        number = intent.getStringExtra("number");

        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);
        et5 = findViewById(R.id.et5);
        et6 = findViewById(R.id.et6);
        verify = findViewById(R.id.verify);
        resend = findViewById(R.id.resend);
        back = findViewById(R.id.back);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        dialog.setCancelable(false);

        mAuth = FirebaseAuth.getInstance();

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCode();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendCode();
            }
        });

        if (number!=null){
            sendCode(number);
        }

        editTextListener();
    }

    private void editTextListener() {
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et1.getText().toString().length()==1){
                    et2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et2.getText().toString().length()==1){
                    et3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et3.getText().toString().length()==1){
                    et4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et4.getText().toString().length()==1){
                    et5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et5.getText().toString().length()==1){
                    et6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if (et5.getText().toString().length()==1){
                        et5.requestFocus();
                    }else if (et4.getText().toString().length()==1){
                        et4.requestFocus();
                    }else if (et3.getText().toString().length()==1){
                        et3.requestFocus();
                    }else if (et2.getText().toString().length()==1){
                        et2.requestFocus();
                    }else if (et1.getText().toString().length()==1){
                        et1.requestFocus();
                    }
                }
                return false;
            }
        });
        et5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if (et4.getText().toString().length()==1){
                        et4.requestFocus();
                    }else if (et3.getText().toString().length()==1){
                        et3.requestFocus();
                    }else if (et2.getText().toString().length()==1){
                        et2.requestFocus();
                    }else if (et1.getText().toString().length()==1){
                        et1.requestFocus();
                    }
                }
                return false;
            }
        });
        et4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if (et3.getText().toString().length()==1){
                        et3.requestFocus();
                    }else if (et2.getText().toString().length()==1){
                        et2.requestFocus();
                    }else if (et1.getText().toString().length()==1) {
                        et1.requestFocus();
                    }
                }
                return false;
            }
        });
        et3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if (et2.getText().toString().length()==1){
                        et2.requestFocus();
                    }else if (et1.getText().toString().length()==1){
                        et1.requestFocus();
                    }
                }
                return false;
            }
        });
        et2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    if (et1.getText().toString().length()==1){
                        et1.requestFocus();
                    }
                }
                return false;
            }
        });

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void sendCode(String number) {

        setUpVerificatonCallbacks();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        dialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).exists()){
                                        User user = new User();
                                        user.setCredits(Variables.WELCOME_CREDITS);
                                        user.setNumber(number);
                                        user.setRefer_taken(false);
                                        user.setRefer_code(getFirstTwoLetters(number.substring(number.length() - 3)));
                                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            dialog.dismiss();
                                                            startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                                                            finish();
                                                            //update ui
                                                        }else {
                                                            dialog.dismiss();
                                                            Toast.makeText(VerifyPhoneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }else {
                                        dialog.dismiss();
                                        startActivity(new Intent(VerifyPhoneActivity.this, MainActivity.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(VerifyPhoneActivity.this, ""+databaseError, Toast.LENGTH_SHORT).show();

                                }
                            });

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                dialog.dismiss();
                            }
                        }
                    }
                });
    }

    public void resendCode() {

        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    public void verifyCode() {
        String edittext1 = et1.getText().toString();
        String edittext2 = et2.getText().toString();
        String edittext3 = et3.getText().toString();
        String edittext4 = et4.getText().toString();
        String edittext5 = et5.getText().toString();
        String edittext6 = et6.getText().toString();

        if (TextUtils.isEmpty(edittext1)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(edittext2)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(edittext3)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(edittext4)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(edittext5)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(edittext6)){
            Toast.makeText(this, "Enter valid code", Toast.LENGTH_SHORT).show();
            return;
        }

        String code = edittext1+edittext2+edittext3+edittext4+edittext5+edittext6;
        try {
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        } catch (Exception e) {
            Toast.makeText(this, "Verification code is wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {

                        resend.setEnabled(false);
                        verify.setEnabled(false);
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        Toast.makeText(VerifyPhoneActivity.this, "Verification code sent successfully", Toast.LENGTH_SHORT).show();
                    }
                };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public String getFirstTwoLetters(String name) {
        return name.length() < 2 ? name : name.substring(0, 2) + UUID.randomUUID().toString().replace("_","").substring(0,6).toUpperCase();
    }

}
