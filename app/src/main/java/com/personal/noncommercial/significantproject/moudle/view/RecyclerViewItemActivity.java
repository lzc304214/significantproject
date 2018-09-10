package com.personal.noncommercial.significantproject.moudle.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.ExpandableAdapter;
import com.personal.noncommercial.significantproject.adapter.MultiItemQuickAdapter;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntity;
import com.personal.noncommercial.significantproject.moudle.bean.DataEntityExpandable;
import com.personal.noncommercial.significantproject.moudle.bean.ItemDataEntity;
import com.personal.noncommercial.significantproject.moudle.bean.ItemDataEntityExpandable;
import com.personal.noncommercial.significantproject.moudle.bean.MultiData;
import com.personal.noncommercial.significantproject.moudle.bean.MultiDataItem;
import com.personal.noncommercial.significantproject.moudle.bean.RecyclerItemEntity;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/5/18
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class RecyclerViewItemActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    private List<MultiItemEntity> mList;
    private ExpandableAdapter expandAdapter;
    private List<MultiDataItem> mMultiItemList;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int position = extras.getInt("position");
            switch (position) {
                case RecyRelationActivity.EXPADEABLE_POSITION:
                    //可展开适配器
                    expandableDataOperation();
                    break;
                case RecyRelationActivity.MULTI_POSITION:
                    //多重数据
                    multiDataOperation();
                    break;
            }
        }


    }

    /**
     * 多重数据
     */
    private void multiDataOperation() {
        mMultiItemList = new ArrayList<>();

        String json = StreamUtils.get(Utils.getContext(), R.raw.multilist);
        MultiData multiData = new Gson().fromJson(json, MultiData.class);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(Utils.getContext(), DividerItemDecoration.VERTICAL));
        MultiItemQuickAdapter adapter = new MultiItemQuickAdapter(mMultiItemList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Utils.getContext()));

        adapter.setNewData(multiData.getData());

    }

    /**
     * 可展开适配器
     */
    private void expandableDataOperation() {
        mList = new ArrayList<>();

        String json = StreamUtils.get(Utils.getContext(), R.raw.expandlist);
        RecyclerItemEntity entity = new Gson().fromJson(json, RecyclerItemEntity.class);

        for (DataEntity dataParent : entity.getData()) {
            //父类数据（第一层）
            DataEntityExpandable parentItem = new DataEntityExpandable(dataParent.getAddress());

            for (ItemDataEntity dataChild : dataParent.getItemData()) {
                //子类（第二层）
                ItemDataEntityExpandable childItem = new ItemDataEntityExpandable(dataChild.getInclude());
                parentItem.addSubItem(childItem);
            }
            mList.add(parentItem);
        }

        mRecyclerView.addItemDecoration(new DividerItemDecoration(Utils.getContext(), DividerItemDecoration.VERTICAL));
        expandAdapter = new ExpandableAdapter(mList);
        mRecyclerView.setAdapter(expandAdapter);
        // important! setLayoutManager should be called after setAdapter
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Utils.getContext()));
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_item_rv;
    }
}
