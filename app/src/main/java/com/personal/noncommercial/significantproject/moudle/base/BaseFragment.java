package com.personal.noncommercial.significantproject.moudle.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.BaseApplication;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 基类fragment
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @BindView(R.id.tv_fragment_title)
    TextView mTvFragmentTitle;
    @BindArray(R.array.main_tab_fragment_title)
    protected String[] mainTab;

    private View mLayoutView;
    private Unbinder bind;
    private BaseActivity mActivity;
    protected BaseApplication app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBaseActivity();
        app = (BaseApplication) getBaseActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = inflater.inflate(getRootLayout(), container, false);
        }
        return mLayoutView;
    }


    /**
     * 跟布局
     *
     * @return
     */
    protected abstract @LayoutRes
    int getRootLayout();

    /**
     * 设置当前碎片的标题
     */
    protected void setCurrentFragmentTitle(String msg) {
        if (!TextUtils.isEmpty(msg))
            mTvFragmentTitle.setText(msg);
    }

    /**
     * 获得activity
     *
     * @return
     */
    protected BaseActivity getBaseActivity() {
        if (mActivity == null)
            mActivity = (BaseActivity) getActivity();

        return mActivity;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        initOnViewCreated(view, savedInstanceState);
    }

    protected abstract void initOnViewCreated(View view, @Nullable Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bind != null)
            bind.unbind();
    }

    /**
     * 进入下一个界面，不传递任何信息
     *
     * @param clazz
     */
    protected void lauch(Class clazz) {
        startActivity(new Intent(getBaseActivity(), clazz));

    }

    /**
     * 带着传递参数进入下一个界面
     *
     * @param clazz
     * @param extras
     */
    protected void lauch(Class clazz, Bundle extras) {
        Intent intent = new Intent(getBaseActivity(), clazz);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
