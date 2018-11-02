package com.personal.noncommercial.significantproject.retrofitutil.net;


import android.content.Context;

import com.google.gson.JsonParseException;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.retrofitutil.ui.LoadingDialog;
import com.personal.noncommercial.significantproject.retrofitutil.util.NetUtil;
import com.personal.noncommercial.significantproject.retrofitutil.util.StringUtil;
import com.personal.noncommercial.significantproject.retrofitutil.util.ToastUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class DefaultSubscriber<T> extends Subscriber<T> {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    //出错提示
    //网络异常
    private final String networkMsg = "Network anomaly";
    //解析异常
    private final String parseMsg = "Analytical anomaly";
    //未知错误
    private final String unknownMsg = "Unknown error";

    protected LoadingDialog dialog;


    protected Context context;

    @Override
    public void onCompleted() {

    }

    public DefaultSubscriber(Context context) {
        if (context != null) {
            this.context = context;
            dialog = new LoadingDialog(context);
        }
    }

    public DefaultSubscriber() {
    }


    @Override
    public void onStart() {
        if (dialog != null && needLoadingDialog()) {
            dialog.show();
        }
    }

    protected boolean needLoadingDialog() {
        return true;
    }

    protected boolean showSuccessMessage() {
        return false;
    }

    protected boolean showFailedMessage() {
        return true;
    }

    @Override
    public void onError(Throwable e) {
        if (dialog != null)
            dialog.dismiss();
        try {
            if (NetUtil.isAvailable(context)) {
                ToastUtils.showToastShort(context, context.getString(R.string.network_close));
            }
//            if (!ApplicationUtils.isOpenNetwork()) {
//                ToastUtils.showToastShort(context, context.getString(R.string.network_close));
//            }
            else {
                //            Throwable throwable = e;
                //            //获取最根源的异常
                //            while (throwable.getCause() != null) {
                //                e = throwable;
                //                throwable = throwable.getCause();
                //            }
                ApiException ex;
                //SocketException
                if (e.getCause() != null && e.getCause() instanceof SocketException) {
                    showError(R.string.network_connect_error);
                    //SocketTimeoutException
                } else if (e.getCause() != null && e.getCause() instanceof SocketTimeoutException) {
                    showError(R.string.network_connect_error);
                    //Server error
                } else if (e instanceof HttpException) {             //HTTP错误
                    HttpException httpException = (HttpException) e;
                    if (httpException == null) {
                        return;
                    }
                    ex = new ApiException(e, httpException.code());
                    switch (httpException.code()) {
                        case UNAUTHORIZED:
                        case FORBIDDEN:
                            showError(R.string.network_permissions_error);
                            break;
                        case NOT_FOUND:
                        case REQUEST_TIMEOUT:
                        case GATEWAY_TIMEOUT:
                        case INTERNAL_SERVER_ERROR:
                        case BAD_GATEWAY:
                        case SERVICE_UNAVAILABLE:
                        default:
                            ex.setDisplayMessage(networkMsg);  //均视为网络错误
//                            ToastUtils.showToastShort(BaseApplication.getInstance(),  BaseApplication.getInstance().getString(R.string.network_connect_fail));
                            //  onError(ex);
                            break;
                    }
                } else if (e instanceof ResultException) {    //服务器返回的错误
                    ResultException resultException = (ResultException) e;
                    if (resultException == null) {
                        return;
                    }
                    ex = new ApiException(resultException, resultException.getErrCode());
                    showError(R.string.network_fail);
                } else if (e instanceof JsonParseException
                        || e instanceof JSONException
                        || e instanceof ParseException) {
                    ex = new ApiException(e, ApiException.PARSE_ERROR);
                    ex.setDisplayMessage(parseMsg);            //均视为解析错误
                    showError(R.string.network_parse_error);
                    //   onError(ex);
                } else if (e instanceof ServerException) {
                    if (!StringUtil.isEmpty(e.getMessage())) {
                        ToastUtils.showToastShort(context, e.getMessage());
                    }
                } else if (e instanceof ConnectException) {
//                    ex = new ApiException(e, ERROR.NETWORD_ERROR);
//                    ex.setDisplayMessage(networkMsg);  //均视为网络错误
                    showError(R.string.network_connect_fail);
                } else {
//                    ToastUtils.showToastShort(context, context.getString(R.string.network_unknow_error));
                    ex = new ApiException(e, ApiException.UNKNOWN);
                    ex.setDisplayMessage(unknownMsg);          //未知错误
                    //   onError(ex);
                }
            }
        } catch (Exception e1) {
            ToastUtils.showToastShort(context, context.getString(R.string.network_connect_fail));
            e1.printStackTrace();
        }
    }

    protected void showError(int string) {
        if (showFailedMessage())
            ToastUtils.showToastShort(context, context.getString(string));
    }


    @Override
    public void onNext(T t) {
        if (dialog != null)
            dialog.dismiss();
    }

}
