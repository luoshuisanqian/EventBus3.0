package com.day05018.eventbus170518;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    private Button btn_send;
    private TextView textView;
    private Button sen_sticky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_send = (Button) findViewById(R.id.btn_send);

        //跳转到发送数据页面
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EventBusSendActivity.class);
                startActivity(intent);
            }
        });
        /**
         * 2。发送粘性事件
         */
        sen_sticky = (Button) findViewById(R.id.sen_sticky);
        //发送粘性事件到发送页面
        sen_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 发送粘性事件
                 */
                EventBus.getDefault().postSticky(new StickyEvent("我是粘性事件"));
                Intent intent = new Intent(MainActivity.this, EventBusSendActivity.class);
                startActivity(intent);
            }
        });
        textView = (TextView) findViewById(R.id.textView);


        //1.在需要使用的地主注册， 传递接收者对象， 这里传递当前activity对象
        EventBus.getDefault().register(this);
        //2。在onDestory取消注册
        //3.发送数据
        //4。接收事件
    }


    /**
     * 接收消息， 接收的前提是加上注解
     */
    @Subscribe(threadMode = ThreadMode.MAIN)//主线程接收消息
    public void messageEventBus(MyEvent event) {
        //显示接收消息
        textView.setText(event.content);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册, 传递和注册相同的对象
        EventBus.getDefault().unregister(this);
    }
}
