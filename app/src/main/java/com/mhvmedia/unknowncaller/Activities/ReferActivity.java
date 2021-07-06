package com.mhvmedia.unknowncaller.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mhvmedia.unknowncaller.R;
import com.mhvmedia.unknowncaller.Variables.Variables;
/** Created by AwsmCreators * */
public class ReferActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView claim_reward, refer_code, refer_title, refer_subtitle_one, refer_subtitle_two;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private LinearLayout refer_code_layout;
    private ImageView btn_back;
    private RelativeLayout share_whatsapp, share_message, share_social;
    String packagename ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer);

        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); }

        changeStatusBarColor();

        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        claim_reward = findViewById(R.id.claim_reward);
        refer_code = findViewById(R.id.refer_code);
        refer_code_layout = findViewById(R.id.refer_code_layout);
        refer_title = findViewById(R.id.refer_title);
        refer_subtitle_one = findViewById(R.id.refer_subtitle_one);
        refer_subtitle_two = findViewById(R.id.refer_subtitle_two);
        btn_back = findViewById(R.id.btn_back);
        share_whatsapp = findViewById(R.id.share_whatsapp);
        share_message = findViewById(R.id.share_message);
        share_social = findViewById(R.id.share_social);
        share_whatsapp.setOnClickListener(this);
        share_message.setOnClickListener(this);
        share_social.setOnClickListener(this);

        packagename = "http://play.google.com/store/apps/details?id=" + getPackageName();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refer_title.setText("Earn Upto "+ Variables.REFERRAL_POINTS.intValue() + " Points");
                    refer_subtitle_one.setText("Get "+Variables.REFERRAL_POINTS.intValue()+" points for every friend you invite.");
                    refer_subtitle_two.setText("Your friend also earns "+ Variables.REFERRAL_POINTS.intValue()+ " Points");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        displayReferCode();

        claim_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog;
                dialog = new Dialog(ReferActivity.this);
                dialog.setContentView(R.layout.custom_progress_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                        (Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                reference.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            try {
                                Boolean isRewardTaken = dataSnapshot.child("refer_taken").getValue(Boolean.class);
                                if (isRewardTaken){
                                    //reward already taken
                                    dialog.dismiss();
                                    showErrorDialog();
                                }else {
                                    dialog.dismiss();
                                    startActivity(new Intent(ReferActivity.this, ClaimRewardActivity.class));

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }
                        }else {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void displayReferCode() {
        if (mAuth.getCurrentUser() != null){
            reference.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        try {
                            String refercode = dataSnapshot.child("refer_code").getValue(String.class);
                            if (refercode != null){
                                refer_code.setText(refercode);
                                initCopyButton();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void initCopyButton() {
        refer_code_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("refer code", refer_code.getText().toString());
                    if (clipboard == null || clip == null) return;
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ReferActivity.this, "Refer code copied to the clipboard", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showErrorDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReferActivity.this);
        alertDialogBuilder.setTitle(R.string.reward_taken);
        alertDialogBuilder
                .setMessage(R.string.reward_taken_msg)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.understand), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.share_whatsapp){
            share("Whatsapp");
        }else if (view.getId() == R.id.share_message){
            share("Message");
        }else if (view.getId() == R.id.share_social){
            share("Social");
        }
    }

    private void share(String type) {
        Dialog dialog;
        dialog = new Dialog(ReferActivity.this);
        dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable
                (Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();

        if (mAuth.getCurrentUser() != null){
            reference.child("users").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        try {
                            String refercode = dataSnapshot.child("refer_code").getValue(String.class);
                            if (refercode != null){
                                if (type.equals("Whatsapp")){
                                    dialog.dismiss();
                                    shareOnWhatsapp(refercode);
                                }else if (type.equals("Message")){
                                    try {
                                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                        sendIntent.setType("text/plain");
                                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                        String sAux = getString(R.string.refer_description);
                                        String rcode = "\nuse my refer code: "+ refercode+"\n\n";
                                        sAux = sAux + rcode + packagename;
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, sAux);
                                        startActivity(Intent.createChooser(sendIntent, "choose one"));
                                        dialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dialog.dismiss();
                                    }

                                }else if (type.equals("Social")){
                                    try {
                                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                        sendIntent.setType("text/plain");
                                        sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                                        String sAux = getString(R.string.refer_description);
                                        String rcode = "\nuse my refer code: "+ refercode+"\n\n";
                                        sAux = sAux + rcode + packagename;
                                        sendIntent.putExtra(Intent.EXTRA_TEXT, sAux);
                                        startActivity(Intent.createChooser(sendIntent, "choose one"));
                                        dialog.dismiss();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        dialog.dismiss();
                                    }
                                }
                            }else {
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            dialog.dismiss();
                        }
                    }else {
                        dialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void shareOnWhatsapp(String refercode) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        String sAux = getString(R.string.refer_description);
        String rcode = "\nuse my refer code: "+ refercode+"\n\n";
        sAux = sAux + rcode + packagename;
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, sAux);
        whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "What's app is not installed", Toast.LENGTH_SHORT).show();
        }
    }
}