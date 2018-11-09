package com.personal.noncommercial.significantproject.retrofitutil.net;

import com.personal.noncommercial.significantproject.retrofitutil.model.ResultModel;
import com.personal.noncommercial.significantproject.retrofitutil.model.UpdateModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;


public class ApiService {

    private static final String LOG_TAG = ApiService.class.getName();

    private ApiInterface apiInterface;

    private static ApiService instance;

    private ApiService() {

        apiInterface = ServiceBuilder.newInstance().build(ApiInterface.class);
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public static ApiService newInstance() {

        if (null == instance) {
            instance = new ApiService();
        }
        return instance;
    }

//
//    public Observable<LoginBody> getLogin(String username, String password, String reg_id) {
//
//        return apiInterface.getLogin(username, password, reg_id);
//    }


    public interface ApiInterface {


        @Multipart
        @POST("api/version")
        Call<ResultModel<UpdateModel>> getNewAppVersion(
                @Part("appcode") String code,
                @Part("appversion") String version);


        @Multipart
        @POST("api/uploadPic")
        Call<ResultModel<Object>> uploadPicture(
                @Part("username") String userName,
                @Part MultipartBody.Part pic);

        @Multipart
        @POST("api/uploadMorePic")
        Call<ResultModel<Object>> uploadMorePicture(
                @Part String userName,
                @Part List<MultipartBody.Part> pics);


    }

}
