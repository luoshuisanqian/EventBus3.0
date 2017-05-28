package com.day05018.eventbus170518;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus的发送事件页面
 */
public class EventBusSendActivity extends AppCompatActivity {

    private Button send_sticky;
    private Button send_main;
    private TextView result;
    boolean firstFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_send);

        send_main = (Button) findViewById(R.id.send_main);
        //主线程发送数据按钮点击事件处理
        send_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 发送消息
                 */
                EventBus.getDefault().post(new MyEvent("主线程发送过来的数据"));
                finish();
            }
        });

        send_sticky = (Button) findViewById(R.id.send_sticky);
        //接收粘性事件数据按钮的点击事件处理
        send_sticky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 注册当前页面的粘性事件
                 */
                if (firstFlag) {
                    firstFlag = false;
                    //只能注册一次
                    EventBus.getDefault().register(EventBusSendActivity.this);
                }

            }
        });


        result = (TextView) findViewById(R.id.result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(EventBusSendActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void StickyEventBus (StickyEvent event) {
        //显示接收的数据
        result.setText(event.msg);
    }
}
