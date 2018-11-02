package com.personal.retrofit.net;


import com.personal.retrofit.model.ResultModel;
import com.personal.retrofit.model.UpdateModel;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


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

//
//        @Multipart
//        @POST("index.php?app=friends&act=delFriend&")
//        Call<ResultModel<Object>> deletefriends(
//                @Part("username") String username,
//                @Part("password") String password,
//                @Part("friend_id") int friend_id);
//        @Multipart
//        @POST("query")
//        Call<ResultModel<Enty>> deletefriends(
//                @Part("key") String key,
//                @Part("word") String word
//        );
//        //发布帖子
//        @Multipart
//        @POST("phone.php?app=forum&act=add")
//        Call<ResultModel<Object>> addForum(
//                @Part("username") String username,
//                @Part("password") String password,
//                @Part() List<MultipartBody.Part> parts
//
//        ); //发布帖子
    }

}
