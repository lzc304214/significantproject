package com.personal.noncommercial.significantproject.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.BaseApplication;
import com.personal.noncommercial.significantproject.moudle.bean.HomeUIBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :lizhengcao
 * @date :2018/4/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class HomeRvAdapter extends BaseQuickAdapter<HomeUIBean, BaseViewHolder> {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    private int width;

    public HomeRvAdapter() {
        super(R.layout.item_adapter_rv_home);
        width = BaseApplication.screenWidth / 4;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeUIBean homeUIBean) {
        ButterKnife.bind(this, baseViewHolder.itemView);
        //使其子布局的宽高一致，为了美观
        ViewGroup.LayoutParams params = llRoot.getLayoutParams();
        params.width = width;
        params.height = width;
        llRoot.setLayoutParams(params);

        ivPicture.setImageResource(homeUIBean.getImage());
        tvTitle.setText(homeUIBean.getBottomText());
    }
}
