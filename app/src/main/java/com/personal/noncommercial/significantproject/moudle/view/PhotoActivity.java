package com.personal.noncommercial.significantproject.moudle.view;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.Constant;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.bean.Person;
import com.personal.noncommercial.significantproject.moudle.inter.OnCameraAndAlbum;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;
import com.personal.noncommercial.significantproject.pop.PopupWindowPhotoImp;
import com.personal.noncommercial.significantproject.retrofitutil.model.ResultModel;
import com.personal.noncommercial.significantproject.retrofitutil.net.ApiService;
import com.personal.noncommercial.significantproject.retrofitutil.net.OnResponseListener;
import com.personal.noncommercial.significantproject.utils.AnimationUtil;
import com.personal.noncommercial.significantproject.utils.BarUtil;
import com.personal.noncommercial.significantproject.utils.CountDownUtil;
import com.personal.noncommercial.significantproject.utils.DataUtil;
import com.personal.noncommercial.significantproject.utils.DelayToDoUtil;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.utils.EncodeUtil;
import com.personal.noncommercial.significantproject.utils.EncryptUtil;
import com.personal.noncommercial.significantproject.utils.FileUtil;
import com.personal.noncommercial.significantproject.utils.MD5Util;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.utils.listener.OnDelayListener;
import com.personal.noncommercial.significantproject.utils.listener.OnUpdateListener;
import com.personal.noncommercial.significantproject.utils.util.JsonUtils;
import com.yalantis.ucrop.UCrop;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.android.segmented.SegmentedGroup;
import me.nereo.multi_image_selector.MultiImageSelector;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author :lizhengcao
 * @date :2018/4/2
 * E-mail:lizc@bsoft.com.cn
 * @类说明 图片类型处理界面
 */

public class PhotoActivity extends BaseActivity implements OnCameraAndAlbum {

    private static final String TAG = PhotoActivity.class.getSimpleName();
    @BindString(R.string.picture)
    String title;
    @BindString(R.string.image_source)
    String picResource;
    @BindColor(R.color.colorPrimary)
    int colorPrimary;
    @BindView(R.id.circle_iv_header)
    CircleImageView mCircleIvHeader;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.rb_first)
    RadioButton rbFirst;
    @BindView(R.id.rb_two)
    RadioButton rbTwo;
    @BindView(R.id.rb_third)
    RadioButton rbThird;
    @BindView(R.id.segment_group)
    SegmentedGroup mSegmentedGroup;
    @BindView(R.id.tv_verification)
    TextView mTvVerification;
    @BindString(R.string.verification)
    String hint;
    @BindView(R.id.tv_md5)
    TextView tvMd5;

    //总的时间
    private static final long WAIT_TIME = 60 * 1000;
    //时间间隔
    private static final long INTERVAL = 1000;


    private PopupWindowPhotoImp popWindow;
    //相机返回请求码
    private static final int REQUEST_CAMERA_CODE = 1;
    private File tempFile;
    private static final int REQUEST_ALBUM_CODE = 2;
    //相机临时uri
    private Uri tempUri;


    private static final String childSuffixCamera = "/temp/camera/";
    private static final String childSuffixCrop = "/temp/crop/";
    private static final String childSuffixSave = "/temp/save/";

    private String encodingAndDecoding = "大家好，我是编码与解码的试验品";


    private String[] money = {"0.03", "0.06", "0.24"};

    private String getTotalCost() {
        Double totalMoney = 0.00;
        if (money != null && money.length > 0) {
            for (int i = 0; i < money.length; i++) {
                String hjje = money[i];
                if (DataUtil.isDouble(hjje)) {

                    totalMoney += DataUtil.stringToDouble(hjje);
                }

            }

        }
        return DataUtil.getAmountValue(String.valueOf(totalMoney));
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle(title);
        //切记不要放布局文件，用动态代码表示，否则会出问题
        rbFirst.setChecked(true);
        //MD5算法演示
        //编码
        String encode = EncodeUtil.urlEncode(encodingAndDecoding);
        //解码
        String decode = EncodeUtil.urlDecode(encode);

        String json = "{\n" +
                "  \"msg\": \"测试信息\",\n" +
                "  \"code\": 1,\n" +
                "  \"data\": {\n" +
                "    \"des\": \"原始数据解析测试\",\n" +
                "    \"fuc\": \"想法检验\"\n" +
                "  },\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"info\": \"信息\",\n" +
                "      \"info2\": \"信息2\",\n" +
                "      \"index\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        String msg = jsonObject.getString("msg");
        Integer code = jsonObject.getInteger("code");
        JSONObject data = jsonObject.getJSONObject("data");
        String des = data.getString("des");
        JSONArray array = jsonObject.getJSONArray("list");
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            String info = o.getString("info");
            String info2 = o.getString("info2");
            Integer index = o.getInteger("index");
            Log.e("普通解析对象的方法===", "info=" + info + "&info2=" + info2 + "&index=" + index);
        }
        tvMd5.setText("MD5Util：" + MD5Util.Md5("13339053971") +
                "\nEncryptUtil:" + EncryptUtil.encryptMD5ToString("13339053971") +
                "\n状态栏高度：" + BarUtil.getStatusBarHeight(mContext) +
                "\nActionBar的高度：" + BarUtil.getActionBarHeight(this) +
                "\n原文：" + encodingAndDecoding +
                "\n编码：" + encode +
                "\n解码：" + decode + "\n" + getTotalCost() +
                "\nSD卡根目录path：" + FileUtil.getRootPath().getPath() +
                "=====\nAbsolutePath：" + FileUtil.getRootPath().getAbsolutePath() +
                "\n缓存目录：" + FileUtil.getCacheFolder(mContext).getPath() +
                "====\nAbsolutePath：" + FileUtil.getCacheFolder(mContext).getAbsolutePath() +
                "\nperson：姓名-" + getPerson().getName() + "\n年龄-" + getPerson().getAge() +
                "\n把对象转化为json字符串：" + JsonUtils.toJsonString(getPerson()) + "\n" +
                "msg=" + msg + "&code=" + code + "&des=" + des);
        //透明状态栏和导航栏的设置
        BarUtil.setTransparentStatusBar(this);
        //卡片翻转效果
        AnimationUtil.cardFilpAnimation(tvBeforeView, tvAfterView);

    }

    private Person getPerson() {
        //构建者Builder模式
        return new Person.Builder()
                .setAge(18)
                .setGender("男")
                .setName("小明")
                .setHobby("篮球")
                .build();
    }

    private void delayDo() {
        //延时5s去做
        DelayToDoUtil.delayToDo(5000, new OnDelayListener() {
            @Override
            public void doSomething() {
                tvMd5.setText("延时5s，做了某件事");
            }
        });
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_photo;
    }


    @BindView(R.id.tv_beforeView)
    TextView tvBeforeView;
    @BindView(R.id.tv_afterView)
    TextView tvAfterView;

    @OnClick({
            R.id.circle_iv_header,
            R.id.tv_verification})
    public void doClick(View v) {
        switch (v.getId()) {
            case R.id.circle_iv_header:
                popWindow = new PopupWindowPhotoImp(mContext, this);
                popWindow.setPopWindow();
                break;
            case R.id.tv_verification:
                CountDownUtil.countDown(mTvVerification, WAIT_TIME, INTERVAL, hint);
                delayDo();
                //设置状态栏颜色
//                BarUtil.setStatusBarColor(this, R.color.red_round);
                //颜色渐变
                int colorPrimary = getResources().getColor(R.color.colorPrimary);
                int colorAccent = getResources().getColor(R.color.colorAccent);
                AnimationUtil.animationColorGradient(colorPrimary, colorAccent, new OnUpdateListener() {
                    @Override
                    public void onUpdate(int intValue) {
                        tvMd5.setTextColor(intValue);
                    }
                });

                break;
            default:
                break;
        }


    }

    /**
     * 创建临时文件夹
     */
    private File createTempFile(String childSuffix) {
        File file = new File(Constant.APP_PATH, childSuffix + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    @Override
    public void onCamera() {
        //相机
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION)) {
            camera();
        }
    }

    /**
     * 对相机的操作
     */
    private Uri outputFileUri;

    private void camera() {
        //权限全部核查通过，或者是api<23
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Environment.getExternalStorageDirectory() 获取手机的根目录
        tempFile = createTempFile(childSuffixCamera);
        //相机uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //Fix the Android N+ file can't be send
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            outputFileUri = FileProvider.getUriForFile(mContext,
                    getApplicationContext().getPackageName() + ".provider", tempFile);

        } else {
            outputFileUri = Uri.fromFile(tempFile);
        }

        // 指定照片保存路径（SD卡）,每次拍照后这个图片都会被替换
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    @Override
    public void onAlbum() {
        //相册
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_WRITE_ES_PERMISSION)) {
            album();
        }
    }

    @Override
    protected void onDestroy() {
        //防止任务执行未完成引起bug
        DelayToDoUtil.cancelHandler();
        super.onDestroy();
    }

    /**
     * 对相册的操作
     */
    public void album() {
//        //真机开发使用，Intent.ACTION_GET_CONTENT  模拟机器中使用：Intent.ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_ALBUM_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION:
                //相机
                if (grantResults.length > 0 && PermissionHelper.checkAllPermissionResult(grantResults)) {
                    camera();
                } else {
                    PermissionHelper.addCameraPermissionDialog(mContext);
                }
                break;
            case PermissionHelper.REQUEST_WRITE_ES_PERMISSION:
                //相册
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    album();
                } else {
                    PermissionHelper.addAlbumPermissionDialog(mContext);
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA_CODE:
                    //相机
                    startPhotoZoom(outputFileUri);
                    break;
                case REQUEST_ALBUM_CODE:
                    //相册
                    if (data != null && data.getData() != null) {
                        startPhotoZoom(data.getData());
                    } else {
                        ToastUtils.showToastShort(picResource);
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    //相机或者相册得到图片的裁剪
                    if (data != null) {
                        Uri resultUri = UCrop.getOutput(data);
                        Glide.with(this)
                                .load(resultUri)
                                .asBitmap()
                                .placeholder(R.mipmap.avatar_none)
                                .into(mCircleIvHeader);
                        Log.e("裁剪过会的图片：", cropFile.getName());
//                        cropFile.getName();
                        //上传头像的功能 此时只有一张图片
//                        RequestBody body = RequestBody.create(MediaType.parse("image/*"), cropFile);
//                        MultipartBody.Part part = MultipartBody.Part.createFormData("filename", cropFile.getName(), body);
//                        Call<ResultModel<Object>> call = ApiService.newInstance().getApiInterface().uploadPicture("第一个字段", part);
//                        call.enqueue(new OnResponseListener<ResultModel<Object>>(mContext) {
//                            @Override
//                            public void onSuccess(ResultModel<Object> result) {
//
//                            }
//
//                            @Override
//                            protected boolean needLoadingDialog() {
//                                return false;
//                            }
//                        });

                    } else {
                        ToastUtils.showToastShort("裁剪图片失败");
                    }
                    break;
            }
        }
    }


    /**
     * 裁剪
     *
     * @param uri
     */
    private File cropFile;

    private void startPhotoZoom(Uri uri) {
        Log.e(TAG, "startPhotoZoom: " + uri);
        int photoSize = DensityUtils.dp2px(70);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(colorPrimary);
        options.setStatusBarColor(colorPrimary);
        cropFile = createTempFile(childSuffixCrop);
        UCrop.of(uri,
                Uri.fromFile(cropFile))
                .withMaxResultSize(photoSize, photoSize)
                .withAspectRatio(1, 1)
                .withOptions(options)
                .start(this);
    }

}
