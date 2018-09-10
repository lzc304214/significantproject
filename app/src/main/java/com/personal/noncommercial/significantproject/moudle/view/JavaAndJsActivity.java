package com.personal.noncommercial.significantproject.moudle.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.utils.ToastUtils;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/4/19
 * E-mail:lizc@bsoft.com.cn
 * @类说明 java与js的互调
 */

public class JavaAndJsActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView mWebView;
    private String javaCallJs = "java调用js方法";

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {

        setCurrentTitle("java与js互调页面");

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //java调用js的方法
                mWebView.loadUrl("javascript:javaCallJs('" + javaCallJs + "')");
            }
        });

        mWebView.loadUrl("file:///android_asset/test.html");


        //js调用java的方法
        mWebView.addJavascriptInterface(new AndroidAndJavascriptInterface(), "android");
    }


    @Override
    protected int getRootLayout() {
        return R.layout.activity_java_js;
    }

    class AndroidAndJavascriptInterface {
        @JavascriptInterface
        public void showToast() {
            ToastUtils.showToastShort("Js调用android方法");
        }

    }
}
