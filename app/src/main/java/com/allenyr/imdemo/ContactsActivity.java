package com.allenyr.imdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by Allen on 2016/12/6.
 */

public class ContactsActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEt_user_id;
    private Button mBtn_launch_chat;
    private Button mBtn_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        mEt_user_id = (EditText) findViewById(R.id.et_user_id);
        mBtn_launch_chat = (Button) findViewById(R.id.btn_launch_chat);
        mBtn_logout = (Button) findViewById(R.id.btn_logout);

        mBtn_launch_chat.setOnClickListener(this);
        mBtn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_launch_chat:
                Intent intent = new Intent(ContactsActivity.this,ChatActivity.class);
                intent.putExtra("username",mEt_user_id.getText().toString());
                startActivity(intent);

                break;
            case R.id.btn_logout:
                EMClient.getInstance().logout(true, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(int code, String message) {
                        // TODO Auto-generated method stub

                    }
                });
                break;
        }
    }
}
