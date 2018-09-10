package com.personal.noncommercial.significantproject.moudle.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/4/19
 * E-mail:lizc@bsoft.com.cn
 * @类说明 极光推送类
 */

public class JPushActivity extends BaseActivity {

    private static final String TAG = JPushActivity.class.getSimpleName();

    @BindView(R.id.tv_content)
    TextView mTvContent;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        mTvContent.setText(url);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_jpush;
    }
}
