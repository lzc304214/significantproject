package com.personal.noncommercial.significantproject.moudle.model;

import com.google.gson.Gson;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.bean.PersonalData;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.Utils;

/**
 * @author :lizhengcao
 * @date :2018/5/30
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class PersonalModelImp implements PersonalModel {

    @Override
    public void getRemoteDataModel(OnPersonalModelListener listener) {
        String json = StreamUtils.get(Utils.getContext(), R.raw.personal);
        PersonalData data = new Gson().fromJson(json, PersonalData.class);
        listener.personalModelListener(data);
    }
}
