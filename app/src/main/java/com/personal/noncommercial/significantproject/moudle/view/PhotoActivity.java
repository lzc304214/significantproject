package com.personal.noncommercial.significantproject.moudle.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.app.Constant;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.inter.OnCameraAndAlbum;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;
import com.personal.noncommercial.significantproject.pop.PopupWindowPhotoImp;
import com.personal.noncommercial.significantproject.utils.AnimationUtil;
import com.personal.noncommercial.significantproject.utils.BarUtil;
import com.personal.noncommercial.significantproject.utils.CountDownUtil;
import com.personal.noncommercial.significantproject.utils.DelayToDoUtil;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.utils.MD5Util;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.utils.listener.OnDelayListener;
import com.personal.noncommercial.significantproject.utils.listener.OnUpdateListener;
import com.yalantis.ucrop.UCrop;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.android.segmented.SegmentedGroup;
import me.nereo.multi_image_selector.utils.FileUtils;
import top.zibin.luban.Luban;

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


    @Override
    @SuppressLint("SetTextI18n")
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle(title);
        //切记不要放布局文件，用动态代码表示，否则会出问题
        rbFirst.setChecked(true);
        //MD5算法演示
        tvMd5.setText(MD5Util.Md5("13339053971") +
                "\n状态栏高度：" + BarUtil.getStatusBarHeight(mContext) +
                "\nActionBar的高度：" + BarUtil.getActionBarHeight(this));
        //透明状态栏和导航栏的设置
        BarUtil.setTransparentStatusBar(this);

        //卡片翻转效果
        AnimationUtil.cardFilpAnimation(tvBeforeView, tvAfterView);
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
        File file = new File(Constant.APP_PATH, childSuffix + System.currentTimeMillis());
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
            tempUri = FileProvider.getUriForFile(mContext,
                    getApplicationContext().getPackageName() + ".provider", tempFile);

        } else {
            tempUri = Uri.fromFile(tempFile);
        }

        // 指定照片保存路径（SD卡）,每次拍照后这个图片都会被替换
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
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
        //真机开发使用，Intent.ACTION_GET_CONTENT  模拟机器中使用：Intent.ACTION_PICK
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
                    startPhotoZoom(tempUri);
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
                    if (data != null) {
                        Uri resultUri = UCrop.getOutput(data);
                        Bitmap bitmap = BitmapFactory.decodeFile(resultUri.getPath());
                        mCircleIvHeader.setImageBitmap(bitmap);

                        File postPicFile = createTempFile(childSuffixSave);
                        //采用android自带的压缩方法
                        saveBitmapToFile(bitmap, postPicFile);
                        Glide.with(this).load(postPicFile).into(mIvPic);
                        Log.e(TAG, "onActivityResult: " + postPicFile.getAbsolutePath() + "\n" + postPicFile.getName());
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
    private void startPhotoZoom(Uri uri) {
        Log.e(TAG, "startPhotoZoom: " + uri);
        int photoSize = DensityUtils.dp2px(70);
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(colorPrimary);
        options.setStatusBarColor(colorPrimary);

        UCrop.of(uri,
                Uri.fromFile(createTempFile(childSuffixCrop)))
                .withMaxResultSize(photoSize, photoSize)
                .withAspectRatio(1, 1)
                .withOptions(options)
                .start(this);
    }

    //把bitmap转化为File
    private void saveBitmapToFile(Bitmap bitmap, File tempFile) {
        // compress(CompressFormat format, int quality, OutputStream stream)
        // quality 压缩的质量   100
        try {
            OutputStream os = new FileOutputStream(tempFile);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
