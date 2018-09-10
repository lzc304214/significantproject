package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.event.MessageEvent;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :lizhengcao
 * @date :2018/4/19
 * E-mail:lizc@bsoft.com.cn
 * @类说明 eventbus返回信息类
 */

public class EventBusFucReturnActivity extends BaseActivity {


    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle("eventBus数据返回类");
    }

    @OnClick(R.id.btn_return_data)
    public void doClick() {
        String msg = "数据返回成功";
        EventBus.getDefault().post(new MessageEvent(msg));
        finish();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_eventbus_return;
    }
}
