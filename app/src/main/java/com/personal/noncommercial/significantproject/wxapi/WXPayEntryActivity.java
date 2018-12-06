package com.personal.noncommercial.significantproject.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TabHost;

import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author :lizhengcao
 * @date :2018/5/23
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;
    private static final String APP_ID = "wxd930ea5d5a258f4f";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        String result;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:  //0 成功
                result = "支付成功，开始采集吧~";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL: //-2 取消
                result = "已取消支付，请完成支付后进行采集~";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "支付中断，请完成支付后进行采集~";
                break;
            default:
                result = "支付异常，请完成支付后进行采集~";
                break;
        }
        ToastUtils.showToastShort(result);
        finish();
    }
}
