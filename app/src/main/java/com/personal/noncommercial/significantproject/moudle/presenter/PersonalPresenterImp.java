package com.personal.noncommercial.significantproject.moudle.presenter;

import com.personal.noncommercial.significantproject.moudle.bean.PersonalData;
import com.personal.noncommercial.significantproject.moudle.fragment.IMyFragment;
import com.personal.noncommercial.significantproject.moudle.fragment.MyFragment;
import com.personal.noncommercial.significantproject.moudle.model.OnPersonalModelListener;
import com.personal.noncommercial.significantproject.moudle.model.PersonalModel;
import com.personal.noncommercial.significantproject.moudle.model.PersonalModelImp;

/**
 * @author :lizhengcao
 * @date :2018/5/30
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class PersonalPresenterImp implements PersonalPresenter {

    private IMyFragment mMyFragment;
    private PersonalModel mPersonalModel;

    public PersonalPresenterImp(IMyFragment myFragment) {
        this.mMyFragment = myFragment;
        this.mPersonalModel = new PersonalModelImp();
    }


    @Override
    public void getRemoteDataPresenter() {
        mPersonalModel.getRemoteDataModel(new OnPersonalModelListener() {
            @Override
            public void personalModelListener(PersonalData data) {
                mMyFragment.getRemoteData(data);
            }
        });
    }
}
