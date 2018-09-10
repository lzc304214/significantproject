package com.personal.noncommercial.significantproject.moudle.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.factory.FragmentFactory;
import com.personal.noncommercial.significantproject.greendao.UserDao;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.base.BaseFragment;
import com.personal.noncommercial.significantproject.moudle.bean.Login;
import com.personal.noncommercial.significantproject.moudle.bean.PersonalData;
import com.personal.noncommercial.significantproject.moudle.bean.UserBean;
import com.personal.noncommercial.significantproject.moudle.presenter.PersonalPresenter;
import com.personal.noncommercial.significantproject.moudle.presenter.PersonalPresenterImp;
import com.personal.noncommercial.significantproject.utils.Utils;

import java.util.List;

import butterknife.BindView;
import fit.Fit;

/**
 * @author :lizhengcao
 * @date :2018/3/29
 * E-mail:lizc@bsoft.com.cn
 * @类说明 我的
 */

public class MyFragment extends BaseFragment implements IMyFragment {

    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;

    public static MyFragment getInstance() {
        MyFragment fragment = new MyFragment();

        return fragment;
    }

    @Override
    protected int getRootLayout() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initOnViewCreated(View view, @Nullable Bundle savedInstanceState) {
        setCurrentFragmentTitle(mainTab[FragmentFactory.MY_FRAGMENT_POSITION]);
        PersonalPresenter personalPresenter = new PersonalPresenterImp(this);
        personalPresenter.getRemoteDataPresenter();
    }

    @Override
    public void getRemoteData(PersonalData data) {
        UserDao userDao = app.getUserDaoInstance();
        List<UserBean> userBeans = userDao.queryAllUserList();
        mTvBirthday.setText(
                data.getBirthday() + "---id：" + userBeans.get(0).getId() + "size："+userBeans.size());
    }
}
