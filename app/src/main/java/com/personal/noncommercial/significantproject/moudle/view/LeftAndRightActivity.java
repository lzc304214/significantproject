package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.LeftRecAdapter;
import com.personal.noncommercial.significantproject.adapter.RightRecAdapter;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntity;
import com.personal.noncommercial.significantproject.moudle.bean.RecyclerItemEntity;
import com.personal.noncommercial.significantproject.moudle.inter.OnLeftAndRightListener;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.Utils;
import com.personal.noncommercial.significantproject.utils.util.JsonUtils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/12/11
 * E-mail:lizc@bsoft.com.cn
 * @类说明 左右列表数据
 */

public class LeftAndRightActivity extends BaseActivity implements OnLeftAndRightListener {

    @BindView(R.id.recycler_view_left)
    RecyclerView mRecyclerViewLeft;
    @BindView(R.id.recycler_view_right)
    RecyclerView mRecyclerViewRight;
    @BindColor(R.color.white)
    int white;
    @BindColor(R.color.gray_round)
    int gray;
    private int leftCurrentPos;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle("左右列表数据");
        leftCurrentPos = 0;
        String json = StreamUtils.get(Utils.getContext(), R.raw.expandlist);
        RecyclerItemEntity entity = JsonUtils.parseObject(json, RecyclerItemEntity.class);
        List<DataEntity> data = entity.getData();
        //对左边的列表处理
        LeftRecAdapter leftAdapter = new LeftRecAdapter(this);
        mRecyclerViewLeft.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerViewLeft.setAdapter(leftAdapter);
        leftAdapter.setNewData(data);

        //右边的列表处理
        mRecyclerViewRight.addItemDecoration(new DividerItemDecoration(Utils.getContext(), DividerItemDecoration.VERTICAL));
        RightRecAdapter rightAdapter = new RightRecAdapter();
        mRecyclerViewRight.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerViewRight.setAdapter(rightAdapter);
        rightAdapter.setNewData(data.get(leftCurrentPos).getItemData());
        //左边的适配器监听
        leftAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (leftCurrentPos == position) {
                return;
            }
            leftCurrentPos = position;
            leftAdapter.notifyDataSetChanged();
            rightAdapter.setNewData(data.get(leftCurrentPos).getItemData());

        });

    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_left_and_right;
    }

    @Override
    public void onLeftChildPosListener(int pos, View itemView) {
        if (pos == leftCurrentPos) {
            itemView.setSelected(true);
        } else {
            itemView.setSelected(false);
        }
    }
}
