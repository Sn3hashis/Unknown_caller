package com.mhvmedia.unknowncaller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Variables.Variables;

import java.util.HashMap;
/** Created by AwsmCreators * */
public class ClaimRewardActivity extends AppCompatActivity {
    private EditText enter_refer_code;
    private Button redeem_now;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private String key = "";
    private ImageView back_btn;
    private TextView refer_claim_subtitle, refer_tos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim_reward);

        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); }

        changeStatusBarColor();

        enter_refer_code = findViewById(R.id.enter_refer_code);
        redeem_now = findViewById(R.id.redeem_now);
        back_btn = findViewById(R.id.back_btn);
        refer_claim_subtitle = findViewById(R.id.refer_claim_subtitle);
        refer_tos = findViewById(R.id.refer_tos);

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        refer_tos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_terms_of_service();
            }
        });

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refer_claim_subtitle.setText("referral reward of "+ Variables.REFERRAL_POINTS.intValue()+ " points");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        redeem_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refercode = enter_refer_code.getText().toString();

                if (TextUtils.isEmpty(refercode)){
                    Toast.makeText(ClaimRewardActivity.this, R.string.enter_refer_code, Toast.LENGTH_SHORT).show();
                }else {
                    Dialog dialog;
                    dialog = new Dialog(ClaimRewardActivity.this);
                    dialog.setContentView(R.layout.custom_progress_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                            (Color.TRANSPARENT));
                    dialog.setCancelable(false);
                    dialog.show();

                    reference.child("users").orderByChild("refer_code").equalTo(refercode).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    key = snapshot.getKey();
                                }
                                Log.i("key", key);
                                if (key != null){
                                    if (key.equals(mAuth.getCurrentUser().getUid())){
                                        dialog.dismiss();
                                        Toast.makeText(ClaimRewardActivity.this, R.string.own_referral_code, Toast.LENGTH_SHORT).show();
                                    }else {
                                        reference.child("users").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()){
                                                    Double credits = dataSnapshot.child("credits").getValue(Double.class);
                                                    reference.child("users").child(key).child("credits").setValue(credits + Variables.REFERRAL_POINTS).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()){
                                                                reference.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        if (dataSnapshot.exists()){
                                                                            Double my_credits = dataSnapshot.child("credits").getValue(Double.class);
                                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                                            hashMap.put("credits", my_credits + Variables.REFERRAL_POINTS);
                                                                            hashMap.put("refer_taken", true);
                                                                            reference.child("users").child(mAuth.getCurrentUser().getUid()).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()){
                                                                                        dialog.dismiss();
                                                                                        showSucessDialog();
                                                                                    }else {
                                                                                        dialog.dismiss();
                                                                                    }
                                                                                }
                                                                            });
                                                                        }else {
                                                                            dialog.dismiss();
                                                                            Toast.makeText(ClaimRewardActivity.this, R.string.something_problem, Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                        dialog.dismiss();
                                                                    }
                                                                });
                                                            }else {
                                                                dialog.dismiss();
                                                                Toast.makeText(ClaimRewardActivity.this, R.string.something_problem, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                }else {
                                                    Toast.makeText(ClaimRewardActivity.this, R.string.something_problem, Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }else {
                                    dialog.dismiss();
                                    Toast.makeText(ClaimRewardActivity.this, R.string.something_problem, Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                dialog.dismiss();
                                Toast.makeText(ClaimRewardActivity.this, R.string.invalid_referral_code, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ClaimRewardActivity.this, R.string.something_problem, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void showSucessDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ClaimRewardActivity.this);
        alertDialogBuilder.setTitle(R.string.congratulations);
        alertDialogBuilder
                .setMessage("You have received " + Variables.REFERRAL_POINTS.intValue()+ " Points in your wallet")
                .setCancelable(false)
                .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void show_terms_of_service() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_term_of_services);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
    }
}