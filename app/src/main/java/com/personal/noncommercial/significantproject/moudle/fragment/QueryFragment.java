package com.personal.noncommercial.significantproject.moudle.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.factory.FragmentFactory;
import com.personal.noncommercial.significantproject.moudle.base.BaseFragment;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 查询
 */

public class QueryFragment extends BaseFragment {

    public static QueryFragment getInstance() {
        QueryFragment fragment = new QueryFragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_query;
    }

    @Override
    protected void initOnViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setCurrentFragmentTitle(mainTab[FragmentFactory.QUERY_FRAGMENT_POSITION]);
    }
}
