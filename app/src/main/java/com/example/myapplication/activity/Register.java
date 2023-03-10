package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.authservice.PhoneAuth;
import com.example.myapplication.util.FunctionUtils;

import java.io.EOFException;

public class Register extends BaseActivity {

    private Button mybuttonregister2;
    private EditText myEtuser;
    private EditText myEtpassword;
    private EditText myConfirmEtpassword;
    private EditText myVerifyCode;

    private Button mybuttonhide;
    private Button mybuttonhide2;
    private Button myVerCode;

    private PhoneAuth phoneAuth = new PhoneAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myEtuser = findViewById(R.id.et_1);
        myEtpassword = findViewById(R.id.et_2);
        myConfirmEtpassword = findViewById(R.id.et_3);
        myVerifyCode = findViewById(R.id.et_4);

        mybuttonhide = findViewById(R.id.btn_hide);
        mybuttonhide2 = findViewById(R.id.btn_hide2);
        mybuttonregister2 = findViewById(R.id.btn_register);
        myVerCode = findViewById(R.id.get_vercode);

        myVerCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FunctionUtils.isFastDoubleClick()) {
                    String username = myEtuser.getText().toString();
                    String password = myEtpassword.getText().toString();
                    String confirmpassword = myConfirmEtpassword.getText().toString();

                    if (inputCheck(username, password, confirmpassword)) {
                        phoneAuth.requestVerifyCode(username, getApplicationContext());
                        countDownTime();
                    }
                }
            }
        });

        mybuttonregister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FunctionUtils.isFastDoubleClick()) {
                    String username = myEtuser.getText().toString();
                    String password = myEtpassword.getText().toString();
                    String confirmpassword = myConfirmEtpassword.getText().toString();
                    String verifycode = myVerifyCode.getText().toString();

                    if (!inputCheck(username, password, confirmpassword))
                        return;

                    //???????????????????????????
                    Intent intent = new Intent(getApplicationContext(), Bottom_bar.class);

                    if (phoneAuth.createUser(username, verifycode, password)) {
                        Toast.makeText(getApplicationContext(), "???????????????????????????...", Toast.LENGTH_SHORT).show();
                        // startActivity(intent);
                        PhoneAuth phoneAuth = new PhoneAuth();
                        phoneAuth.signOut();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "?????????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        mybuttonhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FunctionUtils.isFastDoubleClick()) {
                    int type = myEtpassword.getInputType();
                    if (myEtpassword.getInputType() == 129) {
                        mybuttonhide.setBackgroundResource(R.drawable.eye);
                        myEtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        mybuttonhide.setBackgroundResource(R.drawable.no_eye);
                        myEtpassword.setInputType(129);
                    }
                }
            }
        });

        mybuttonhide2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FunctionUtils.isFastDoubleClick()) {
                    int type = myConfirmEtpassword.getInputType();
                    if (myConfirmEtpassword.getInputType() == 129) {
                        mybuttonhide2.setBackgroundResource(R.drawable.eye);
                        myConfirmEtpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        mybuttonhide2.setBackgroundResource(R.drawable.no_eye);
                        myConfirmEtpassword.setInputType(129);
                    }
                }
            }
        });

    }

    /**
     * ??????????????????????????????
     * @param username ?????????
     * @param password ??????
     * @param confirmpassword ????????????
     * @return true??????????????????
     */
    private boolean inputCheck(String username, String password, String confirmpassword) {
        if (username.length() != 11) {
            Toast.makeText(getApplicationContext(), "?????????????????????", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(confirmpassword)) {
            Toast.makeText(getApplicationContext(), "???????????????????????????", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.length() < 8) {
            Toast.makeText(getApplicationContext(), "????????????????????????8", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void countDownTime() {
        //??????????????????CountDownTimer??????
        CountDownTimer mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                myVerCode.setText(millisUntilFinished / 1000 + 1 + "????????????");
            }

            @Override
            public void onFinish() {
                myVerCode.setEnabled(true);
                myVerCode.setText("???????????????");
                cancel();
            }
        };
        mTimer.start();
        myVerCode.setEnabled(false);
    }
}