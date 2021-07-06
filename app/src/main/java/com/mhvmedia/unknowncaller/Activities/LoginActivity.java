package com.mhvmedia.unknowncaller.Activities;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.skydoves.elasticviews.ElasticImageView;
import com.mhvmedia.unknowncaller.R;

/** Created by AwsmCreators * */
public class LoginActivity extends AppCompatActivity {
    EditText et_number, et_code;
    Button btn_continue;
    Button close;
    ElasticImageView back;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Boolean isAgreed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= 21) { getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN); }

        changeStatusBarColor();
        preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = preferences.edit();

        isAgreed = preferences.getBoolean("agreement", false);

        if (isAgreed){

        }else {
            showAgreementDialog();
        }

        et_number = findViewById(R.id.et_number);
        et_code = findViewById(R.id.et_code);
        btn_continue = findViewById(R.id.btn_continue);
        close = findViewById(R.id.close);
        back = findViewById(R.id.back);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_code.getText().toString();
                String number = et_number.getText().toString();

                if (TextUtils.isEmpty(code)){
                    Toast.makeText(LoginActivity.this, "Enter country code", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(LoginActivity.this, "Please enter your number", Toast.LENGTH_SHORT).show();
                    return;
                }

                String result = et_code.getText().toString()+et_number.getText().toString();
                Intent intent = new Intent(LoginActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("number", result);
                startActivity(intent);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showAgreementDialog() {
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
                editor.putBoolean("agreement",false).commit();
                finish();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_accept)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("agreement",true).commit();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.bt_decline)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("agreement",false).commit();
                finish();
            }
        });

        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setAttributes(lp);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}