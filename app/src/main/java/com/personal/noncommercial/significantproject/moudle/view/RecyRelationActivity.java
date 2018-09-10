package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.RecylerRelationAdapter;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.RecylerListEntity;
import com.personal.noncommercial.significantproject.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明 列表相关
 */

public class RecyRelationActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private String[] mheads = {"可展开列表", "多重数据"};
    private List<RecylerListEntity> mRecylerList;
    private RecylerRelationAdapter adapter;
    public static final int EXPADEABLE_POSITION = 0;
    public static final int MULTI_POSITION = 1;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        //数据
        mRecylerList = new ArrayList<>();
        for (String head : mheads) {
            mRecylerList.add(new RecylerListEntity(head));
        }

        mRecyclerView.addItemDecoration(new DividerItemDecoration(Utils.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecylerRelationAdapter();
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Utils.getContext()));

        adapter.setNewData(mRecylerList);

        adapter.setOnItemClickListener((baseQuickAdapter, view, position) -> {
            Bundle extras = new Bundle();
            extras.putInt("position", position);
            lauch(RecyclerViewItemActivity.class, extras);
        });
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_relation_recy;
    }
}
