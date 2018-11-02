package com.personal.noncommercial.significantproject.moudle.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.BaseApplication;
import com.personal.noncommercial.significantproject.utils.TranslucentUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author :lizhengcao
 * @date :2018/3/27
 * E-mail:lizc@bsoft.com.cn
 * @类说明 基类activity 用于一些通用的activity的继承
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder bind;
    protected Context mContext;
    private LinearLayout mRootView;
    private Toolbar mToolbar;
    private ImageButton mIbBack;
    private TextView mTvTitle;
    protected BaseApplication app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TranslucentUtil.translucent(this);
        //调用父类的方法
        super.setContentView(R.layout.activity_base);
        //初始化父类对象
        initParentView();

        setContentView(getRootLayout());
        app = (BaseApplication) getApplication();
        //绑定控件
        bind = ButterKnife.bind(this);

        initOnCreate(savedInstanceState);


    }

    protected abstract void initOnCreate(@Nullable Bundle savedInstanceState);

    private void initParentView() {
        mContext = this;
        mRootView = $(R.id.root_view);
        mToolbar = $(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mIbBack = $(R.id.ib_back);
        mTvTitle = $(R.id.tv_title);
        //初始化沉浸式
        TranslucentUtil.setOrChangeTranslucentColor(mToolbar, this);

        //返回键的处理
        mIbBack.setOnClickListener(v -> finish());
        //隐藏返回键
        if (!isHideBack()) {
            //显示
            mIbBack.setVisibility(View.VISIBLE);

        } else
            mIbBack.setVisibility(View.GONE);

        //隐藏toolbar
        if (!isHideToolbar())
            mToolbar.setVisibility(View.VISIBLE);
        else
            mToolbar.setVisibility(View.GONE);
    }


    /**
     * 是否隐藏返回键
     *
     * @return false 显示  true 隐藏
     */
    protected boolean isHideBack() {
        return false;
    }

    /**
     * 是否隐藏toolbar
     *
     * @return false 显示  true 隐藏
     */
    protected boolean isHideToolbar() {
        return false;
    }

    /**
     * 设置当前页面的标题
     *
     * @param msg
     */
    protected void setCurrentTitle(String msg) {
        if (!TextUtils.isEmpty(msg))
            mTvTitle.setText(msg);
    }

    /**
     * @return 跟布局文件
     */
    protected abstract @LayoutRes
    int getRootLayout();


    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(mContext).inflate(layoutResID, null);
        setContentView(view);
    }

    @Override
    public void setContentView(View view) {
        if (mRootView == null) return;
        mRootView.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null)
            //解除绑定
            bind.unbind();
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }


    @SuppressWarnings("unchecked")
    protected <T extends View> T $(View parent, @IdRes int id) {
        if (parent == null) {
            return null;
        }
        return (T) parent.findViewById(id);
    }

    /**
     * 进入下一个界面，不传递任何信息
     *
     * @param clazz
     */
    protected void lauch(Class clazz) {
        startActivity(new Intent(mContext, clazz));
    }

    /**
     * 带着传递参数进入下一个界面
     *
     * @param clazz
     * @param extras
     */
    protected void lauch(Class clazz, Bundle extras) {
        Intent intent = new Intent(mContext, clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
