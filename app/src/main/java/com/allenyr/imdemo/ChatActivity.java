package com.allenyr.imdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.List;

/**
 * Created by Allen on 2016/12/6.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener,EMMessageListener{

    private TextView mTv_content;
    private EditText mEt_message;
    private Button mBtn_send;
    private static final String TAG = "allen";
    private String mUserID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mTv_content = (TextView) findViewById(R.id.tv_content);
        mEt_message = (EditText) findViewById(R.id.et_message);
        mBtn_send = (Button) findViewById(R.id.btn_send);

        mBtn_send.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        mUserID = bundle.getString("username");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send:
                //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
                EMMessage message = EMMessage.createTxtSendMessage(mEt_message.getText().toString(), mUserID);
                message.setChatType(EMMessage.ChatType.Chat);
                //发送消息
                EMClient.getInstance().chatManager().sendMessage(message);
                message.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i(TAG,"消息发送成功");
                    }

                    @Override
                    public void onError(int i, String s) {
                        Log.i(TAG,"消息发送失败"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {
                        Log.i(TAG,"消息发送中"+s);
                    }
                });
                break;
        }
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        for (final EMMessage message : list){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTv_content.setText(mTv_content.getText()+"\n"
                    + ((EMTextMessageBody)(message.getBody())).getMessage());

                }
            });

        }
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
