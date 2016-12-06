package com.allenyr.imdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by Allen on 2016/12/6.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mBtn_sign_in;
    private Button mBtn_sign_up;
    private EditText mEt_username;
    private EditText mEt_password;
    private static final String TAG = "allen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEt_username = (EditText) findViewById(R.id.et_username);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mBtn_sign_in = (Button) findViewById(R.id.btn_sign_in);
        mBtn_sign_up = (Button) findViewById(R.id.btn_sign_up);

        mBtn_sign_in.setOnClickListener(this);
        mBtn_sign_up.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
                signin();
                break;
            case R.id.btn_sign_up:
                signup();
                break;
        }
    }

    private void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //注册失败会抛出HyphenateException
                try {
                    EMClient.getInstance().createAccount(mEt_username.getText().toString(), mEt_password.getText().toString());//同步方法
                    Log.i(TAG,"注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e(TAG,"注册失败");
                }
            }
        }).start();
    }

    private void signin(){
        EMClient.getInstance().login(mEt_username.getText().toString(),mEt_password.getText().toString(),new EMCallBack() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(LoginActivity.this,ContactsActivity.class));
                finish();
                Log.i(TAG,"登录成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e(TAG,"登录失败"+s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e(TAG,"登录中"+s);
            }//回调

        });
    }
}
