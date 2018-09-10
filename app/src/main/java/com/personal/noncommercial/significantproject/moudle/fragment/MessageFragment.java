package com.personal.noncommercial.significantproject.moudle.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.factory.FragmentFactory;
import com.personal.noncommercial.significantproject.moudle.base.BaseFragment;
import com.personal.noncommercial.significantproject.moudle.bean.Book;

import javax.security.auth.login.LoginException;

import butterknife.BindView;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 消息
 */

public class MessageFragment extends BaseFragment {


    public static MessageFragment getInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initOnViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setCurrentFragmentTitle(mainTab[FragmentFactory.MESSAGE_FRAGMENT_POSITION]);
    }
}
