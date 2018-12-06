package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.webview.Html5WebView;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/4/23
 * E-mail:lizc@bsoft.com.cn
 * @类说明 调用H5界面
 */

public class H5CallActivity extends BaseActivity {

    @BindView(R.id.web_view)
    Html5WebView mHtml5WebView;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        mHtml5WebView.loadUrl("https://www.baidu.com");
        mHtml5WebView.setWebsiteChangeListener(new Html5WebView.WebsiteChangeListener() {
            @Override
            public void onWebsiteChange(String title) {
                setCurrentTitle(title);
            }

            @Override
            public void onUrlChange(String url) {
                mHtml5WebView.loadUrl(url);
            }
        });
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_callh5;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mHtml5WebView.canGoBack()) {
            mHtml5WebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
