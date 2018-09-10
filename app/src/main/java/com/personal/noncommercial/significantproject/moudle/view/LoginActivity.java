package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.Constant;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.Login;
import com.personal.noncommercial.significantproject.utils.SpUtils;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.ToastUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import fit.Fit;

/**
 * @author :lizhengcao
 * @date :2018/3/28
 * E-mail:lizc@bsoft.com.cn
 * @类说明 登录
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindString(R.string.login_title)
    String login;
    @BindString(R.string.login_empty_judge)
    String loginJudge;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.iv_delete_username)
    ImageView ivDeleteUsername;
    @BindView(R.id.iv_delete_password)
    ImageView ivDeletePassword;


    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle(login);
    }

    /**
     * 用户名输入文本改变时的操作
     *
     * @param s 输入的文本值
     */
    @OnTextChanged(value = R.id.et_username, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterUsernameTextChanged(Editable s) {
        String content = s.toString().trim();
        if (!TextUtils.isEmpty(content))
            ivDeleteUsername.setVisibility(View.VISIBLE);
        else
            ivDeleteUsername.setVisibility(View.GONE);
    }

    /**
     * 用户名输入的光标的聚焦情况
     *
     * @param hasFocus 是否聚焦
     */
    @OnFocusChange(R.id.et_username)
    public void onFocusUsernameChange(boolean hasFocus) {
        //聚焦，清除图片显示
        String userName = etUsername.getText().toString().trim();
        if (!TextUtils.isEmpty(userName) && hasFocus)
            ivDeleteUsername.setVisibility(View.VISIBLE);
        else
            ivDeleteUsername.setVisibility(View.GONE);

    }


    /**
     * @param v
     */
    @OnClick({R.id.iv_delete_username, R.id.iv_delete_password, R.id.btn_login})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.iv_delete_username:
                //清空用户名信息
                etUsername.setText("");
                break;
            case R.id.iv_delete_password:
                //清空密码信息
                etPassword.setText("");
                break;
            case R.id.btn_login:
                //登录时需要保存的信息
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

                    ToastUtils.showToastShort(loginJudge);
                    return;
                }


                String loginJson = StreamUtils.get(mContext, R.raw.login);
                Login login = new Gson().fromJson(loginJson, Login.class);
                //保存登录时的对象
                Fit.save(mContext, login);
                //第一次登录时，显示登录界面
                SpUtils.putBoolean(mContext, Constant.LOGIN_FIRST, true);
                lauch(MainTabActivity.class);
                finish();
                break;
        }
    }


    @Override
    protected boolean isHideBack() {
        return true;
    }

    /**
     * 密码输入文本改变时的操作
     *
     * @param s 输入的文本值
     */
    @OnTextChanged(value = R.id.et_password, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void afterPasswordTextChanged(Editable s) {
        String content = s.toString().trim();
        if (!TextUtils.isEmpty(content))
            ivDeletePassword.setVisibility(View.VISIBLE);
        else
            ivDeletePassword.setVisibility(View.GONE);
    }

    /**
     * 密码输入的光标的聚焦情况
     *
     * @param hasFocus 是否聚焦
     */
    @OnFocusChange(R.id.et_password)
    public void onFocusPasswordChange(boolean hasFocus) {
        //聚焦，清除图片显示
        String password = etPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(password) && hasFocus)
            ivDeletePassword.setVisibility(View.VISIBLE);
        else
            ivDeletePassword.setVisibility(View.GONE);
    }


    @Override
    protected int getRootLayout() {
        return R.layout.activity_login;
    }
}
