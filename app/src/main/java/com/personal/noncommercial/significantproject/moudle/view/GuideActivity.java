package com.personal.noncommercial.significantproject.moudle.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.GuideVPAdapter;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.utils.SystemUiVisibilityUtil;
import com.personal.noncommercial.significantproject.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnPageChange;
import butterknife.Unbinder;

/**
 * @author :lizhengcao
 * @date :2018/3/26
 * E-mail:lizc@bsoft.com.cn
 * @类说明 引导页面
 */

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.iv_red)
    ImageView ivRed;
    @BindView(R.id.btn_now_experience)
    Button mBtnNowExperience;

    //引导页面的图片
    private int[] guidePic = {R.mipmap.guide_1, R.mipmap.guide_2, R.mipmap.guide_3};
    //滑动图片的适配器
    private GuideVPAdapter guideVPAdapter;
    private Unbinder bind;
    //两个点之间的距离
    private int mPointDistance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏 即全屏操作
//        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        //绑定view
        bind = ButterKnife.bind(this);

        //初始化灰色圆
        for (int i = 0; i < guidePic.length; i++) {
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i > 0)
                params.leftMargin = DensityUtils.dp2px(10);
            iv.setLayoutParams(params);
            iv.setImageResource(R.drawable.round_gray);
            mLlContainer.addView(iv);
        }

        //引导页面的viewPager适配器
        guideVPAdapter = new GuideVPAdapter(guidePic);
        mVp.setAdapter(guideVPAdapter);

        //红色点的视图 onMeasure onLayout onDraw
        ivRed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除监听，避免重复回调
                ivRed.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //灰色点之间的距离
                mPointDistance = mLlContainer.getChildAt(1).getLeft() - mLlContainer.getChildAt(0).getLeft();

            }
        });
    }

    //关于红色点的页面滑动时的监听
    @OnPageChange(value = R.id.vp, callback = OnPageChange.Callback.PAGE_SCROLLED)
    public void onPageScrolled(int position, float positionOffset) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) ivRed.getLayoutParams();
        params.leftMargin = (int) (mPointDistance * (position + positionOffset));
        ivRed.setLayoutParams(params);
    }

    //立即体验按钮的隐藏和显示
    @OnPageChange(value = R.id.vp, callback = OnPageChange.Callback.PAGE_SELECTED)
    public void onPageSelected(int position) {
        if (position == guidePic.length - 1)
            //显示
            mBtnNowExperience.setVisibility(View.VISIBLE);
        else
            //隐藏
            mBtnNowExperience.setVisibility(View.GONE);

    }

    //立即体验按钮的点击 进入到启动页面
    @OnClick(R.id.btn_now_experience)
    public void doClick() {
        startActivity(new Intent(this, LoginActivity.class));
        //显示状态栏 否则进入下个页面状态栏与内容页面会产生点小问题
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), false);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        if (bind != null)
            bind.unbind();
    }
}
