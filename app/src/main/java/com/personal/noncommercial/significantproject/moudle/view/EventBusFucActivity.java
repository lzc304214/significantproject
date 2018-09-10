package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.event.MessageEvent;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author :lizhengcao
 * @date :2018/4/19
 * E-mail:lizc@bsoft.com.cn
 * @类说明 eventbus类
 */

public class EventBusFucActivity extends BaseActivity {

    @BindView(R.id.btn_next)
    Button mBtnNext;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle("eventBus注册类");
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btn_next)
    public void doClick() {
        lauch(EventBusFucReturnActivity.class);
    }

    @SuppressWarnings("unused")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(MessageEvent event) {
        mBtnNext.setText(event.getMsg());
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_eventbus;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

