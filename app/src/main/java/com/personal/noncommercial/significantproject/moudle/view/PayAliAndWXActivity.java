package com.personal.noncommercial.significantproject.moudle.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.WXPayResult;
import com.personal.noncommercial.significantproject.moudle.pay.PayResult;
import com.personal.noncommercial.significantproject.moudle.pay.util.OrderInfoUtil2_0;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;
import com.personal.noncommercial.significantproject.utils.StreamUtils;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.utils.Utils;
import com.personal.noncommercial.significantproject.utils.util.JsonUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;


import java.util.Map;


import butterknife.OnClick;

/**
 * @author :lizhengcao
 * @date :2018/5/22
 * E-mail:lizc@bsoft.com.cn
 * @类说明 支付类
 */

public class PayAliAndWXActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String result = payResult.getResult();
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    private IWXAPI mIWXApi;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 支付宝支付业务：入参app_id APPID为真实账号，请勿付款
     */
    public static final String APPID = "2018112362304449";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCtqvF0s7B1BOc1xt0OJZK180ivCqo5Vmeyg6MJwhABn35pCbedDxb+F8DPq7IAxJ8lNCqsiJfXQCy8RIQUjHrFPVTec4FT9klXoT26xpa49tNjI45WnyU3mNH4DfLrAgUWAak4YoHDeEpm2UvQVNWJ6+upAwQG7tvDBrZWiUFm/9t9EcKLf4Jd/9z6CeGbrXMRlUvi+VTXNzN7TirvDVFnF2fYl3GG0ZL3UURxqzMg4DdJnL417wEbna+oR3kjFMUjZ8P+/HCBUjJA7YsRBRxEVLt6Ngsyz0XJD5Rgfa/HoyGBEBMut1INHBrEnBhy6Onho/IGZAe9Z3wF/c20fBfxAgMBAAECggEAXMYPCura/whJg37ipCYKQ8zdLTUjTlIMP172/8V9y4AqhW49elePTtndlJMCGkrvQW6mxViK1OBhhYmMjXWmOCvVZKiLlBDjiT6HvXlNFy40KqfZ6AhIV3T55et9s3F2yhTFE2v8KjHefSbjyz0mffFcCQOlaRa8q2iwi3XzTFPM3xQG3pzos9sWhPDU43GzZPEcoNoN2uWJlKDdjvnFIbT+NEJkWXj1ssUlKjBaxUQbzFH45IWfEninHbap4niP+Ew/1Tn3222m3+CCWPJL686Ok37IeB6GI7zn3OtUIQSRPW3XCAu5LfBK+MuO5VR23La8jTb9LUKh1NA2IY3F4QKBgQDVdDRwcWRkhTIDBO6RVaYkOz9BSp1LI+l1fCAFU57VlRdHO5AlrVUDOEK0k1qSZkSx7xmpKYWPY8ISN2bXnl3jeN42jhSJ6CT/xy7v8pOR7oHAHb1wbEsj/jZJwwc68w2VJTbI0Ywee1YpWJLX9tj4mjZU8GKQUBOoyTJmyGndHQKBgQDQSJdQal3AFZWpZ30jK37e8Ppg+1FbXuVohBkdm9iRd96UZnIm+4IJfxGpr3KBa78QpdRP1b39pMooEBhHmW8SkFfqlm76dnJ8PpQqD8y/Ee8pCGZw/Km6u3xMQRnY2BbEfLss7QX872cGKoPV9/q8WD/JQIHwXnSxW1g2olXx5QKBgFwuzkLNRaz/tOSG6bzMP28gQyYgYiPEQ5ugWnfScCMPOzlhKNSMsc0tK+muRYw6LGqntu5OZ0bQr0yMrpZp52RIm7b1zzrrjacEz2mC2mZImbJtZZW6vwyxSaK7XlEADJqfsrQxgxDa9Q/wF6K7EWLzvokaM4bdflADbAflf4qlAoGAFCyz22oyIoT0ti/8AwgXhei5l+NAAiWy+uiwthl+ZLjDqoQx0mzSL7FLOLBx2u9g1m/5nLILcSIhIrhMYcMO6zFoEqT3ovMOK9Kz708QegF2tPbHVXJjzl6Sr6AurL5hli6uwq9CwTk/NW2wz2zxsISryQaij3HWw8chSEl5IbECgYBkqEzRxIZf1dCVuptCG72OshoRGbudI+uJQU49/VE1ulGzYPcgEwQZBCRVFSMbIQgEyoHSbz3v1GXNtBQ4W2gWz/0Pcd8W2uz7/DmLWY8q1vFG4sIILXnfKIAUVzVQJCDov3UEq9YdGzanjpFg98b3URFZQXPDTuZRzSkb+B2f+Q==";
    public static final String RSA_PRIVATE = "";

    private static final int ALIPAY_TYPE = 1;
    private static final int WEIXINPAY_TYPE = 2;
    private int PAY_TYPE;

    @OnClick({R.id.tv_alipay, R.id.tv_wxpay})
    public void doPayDetailClick(View v) {
        switch (v.getId()) {
            case R.id.tv_alipay:
                PAY_TYPE = ALIPAY_TYPE;
                if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_READ_PHONE_STATE_AND_WRITE_EXTERNAL_STORAGE)) {
                    pay(ALIPAY_TYPE);
                }
                break;
            case R.id.tv_wxpay:
                PAY_TYPE = WEIXINPAY_TYPE;
                if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_READ_PHONE_STATE_AND_WRITE_EXTERNAL_STORAGE)) {
                    pay(WEIXINPAY_TYPE);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.REQUEST_READ_PHONE_STATE_AND_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && PermissionHelper.checkAllPermissionResult(grantResults)) {
                    pay(PAY_TYPE);
                } else {
                    PermissionHelper.addPayPermissionDialog(Utils.getContext());
                }
                break;
        }

    }


    public void pay(int type) {
        switch (type) {
            case ALIPAY_TYPE:
                getAlipay();
                break;
            case WEIXINPAY_TYPE:
                getWXPay();
                break;
        }
    }

    /**
     * 微信支付过程，，，，因为是个人测试，所以没法通过，大致流程基本是这样，业务逻辑根据自己的需求来添加
     * 此维信支付是调不起来（签名和包名与实际账号不一致啊，大致流程是如下）
     */
    private void getWXPay() {
        String json = StreamUtils.get(Utils.getContext(), R.raw.wxpay);
        Log.e("json", json);
        WXPayResult wxPayResult = JsonUtils.parseObject(json, WXPayResult.class);
//        WXPayResult wxPayResult = new Gson().fromJson(json, WXPayResult.class);
        mIWXApi = WXAPIFactory.createWXAPI(this, null);
        //将该app注册到微信
        mIWXApi.registerApp(wxPayResult.getAppid());
        if (!(mIWXApi.isWXAppInstalled() && mIWXApi.isWXAppSupportAPI())) {
            ToastUtils.showToastShort("请安装微信客户端后使用微信支付，谢谢合作~");
            return;
        }

        Runnable payRunnable = () -> {
            PayReq request = new PayReq();
            request.appId = wxPayResult.getAppid();
            request.partnerId = wxPayResult.getPartnerid();
            request.prepayId = wxPayResult.getPrepayid();
            request.packageValue = wxPayResult.getPackageX();
            request.nonceStr = wxPayResult.getNoncestr();
            request.timeStamp = wxPayResult.getTimestamp() + "";
            request.sign = wxPayResult.getSign();
            mIWXApi.sendReq(request);
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付相关，省了一些基本的申请步骤
     */
    private void getAlipay() {
        //支付宝支付业务  此处是通过网络请求加载得到的数据来进行处理
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", (dialoginterface, i) -> finish()).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Log.e("=======", "3");
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);

        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;

        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);

        String orderInfo = orderParam + "&" + sign;

//                final String orderInfo = "app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D\n";   // 订单信息
        Runnable payRunnable = () -> {
            PayTask payTask = new PayTask(PayAliAndWXActivity.this);
            Map<String, String> result = payTask.payV2(orderInfo, true);
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        // 必须异步调用
        Thread thread = new Thread(payRunnable);
        thread.start();
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_pay;
    }
}
