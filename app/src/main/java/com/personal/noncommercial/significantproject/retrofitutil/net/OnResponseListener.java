package com.personal.noncommercial.significantproject.retrofitutil.net;

import android.content.Context;
import android.text.TextUtils;

import com.personal.noncommercial.significantproject.retrofitutil.model.ResultModel;
import com.personal.noncommercial.significantproject.retrofitutil.util.ToastUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class OnResponseListener<Entity> extends DefaultSubscriber<Entity> implements Callback<Entity> {


    public OnResponseListener(Context context) {
        super(context);
        if (dialog != null && needLoadingDialog())
            dialog.show();
    }


    @Override
    public void onResponse(Call<Entity> call, Response<Entity> response) {
        if (dialog != null)
            dialog.dismiss();
        if (response.body() != null && response.body() instanceof ResultModel) {
            if (dialog != null)
                dialog.dismiss();
            ResultModel commonModel = (ResultModel) response.body();
            if (commonModel.getCode() == 1) {
                if (showSuccessMessage()) {
                    ToastUtils.showToastShort(context, commonModel.getMsg());
                }
                onSuccess(response.body());
            } else {
                dealError(commonModel.getMsg());
            }

        }
    }

    public abstract void onSuccess(Entity entity);

    @Override
    public void onFailure(Call<Entity> call, Throwable t) {
        onError(t);
        dealError(null);
    }

    protected void dealError(String msg) {
        if (!TextUtils.isEmpty(msg) && showFailedMessage()) {
            ToastUtils.showToastShort(context, msg);
        }
    }

}
