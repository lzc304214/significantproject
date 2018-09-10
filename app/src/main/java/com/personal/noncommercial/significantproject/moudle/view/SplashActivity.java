package com.personal.noncommercial.significantproject.moudle.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.BaseApplication;
import com.personal.noncommercial.significantproject.app.Constant;
import com.personal.noncommercial.significantproject.dialog.CustomDialog;
import com.personal.noncommercial.significantproject.greendao.UserDao;
import com.personal.noncommercial.significantproject.moudle.bean.Book;
import com.personal.noncommercial.significantproject.moudle.bean.UserBean;
import com.personal.noncommercial.significantproject.utils.BarUtil;
import com.personal.noncommercial.significantproject.utils.SpUtils;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.SystemUiVisibilityUtil;
import com.personal.noncommercial.significantproject.utils.Utils;
import com.personal.noncommercial.significantproject.utils.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author :lizhengcao
 * @date :2018/3/27
 * E-mail:lizc@bsoft.com.cn
 * @类说明 启动页面
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.iv_splash)
    ImageView mIvSplash;

    private Unbinder bind;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏 即全屏操作
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);// 如果有底部导航栏，会遮蔽
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);
        mContext = this;

        AlphaAnimation animation = new AlphaAnimation(0.5f, 1.0f);
        animation.setDuration(2000);
        mIvSplash.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //第一次进入的  进入GuideActivity页面
                //非第一次进入时 进入登录页面
                Intent intent;
                Boolean isSplashFirst = SpUtils.getBoolean(mContext, Constant.SPLASH_FIRST, false);
                if (!isSplashFirst) {
                    //第一次进入 splash--guide--login
                    intent = new Intent(mContext, GuideActivity.class);
                    SpUtils.putBoolean(mContext, Constant.SPLASH_FIRST, true);
                } else {
                    //非第一次进入  1.splash--login  2.splash--main
                    Boolean isLoginFirst = SpUtils.getBoolean(mContext, Constant.LOGIN_FIRST, false);
                    if (!isLoginFirst) {
                        //未从登录页面进入主页面
                        intent = new Intent(mContext, LoginActivity.class);
                    } else {
                        //进入过主页面
                        intent = new Intent(mContext, MainTabActivity.class);
                    }
                }


                BaseApplication app = (BaseApplication) getApplication();
                //第一次进入时，开始更新userDao数据库
                if (app.getIsFirstUserDaoData()) {
                    UserDao userDao = app.getUserDaoInstance();
                    String json = StreamUtils.get(Utils.getContext(), R.raw.user);
                    UserBean userBean = new Gson().fromJson(json, UserBean.class);
                    List<UserBean> userBeans = new ArrayList<>();
                    userBeans.add(userBean);
                    userDao.insertUserList(userBeans);
                    app.setIsFirstUserDaoData(false);
                }

                startActivity(intent);
                //显示状态栏 否则进入下个页面状态栏与内容页面会产生点小问题
                SystemUiVisibilityUtil.hideStatusBar(getWindow(), false);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null)
            bind.unbind();
    }
}
