package com.personal.noncommercial.significantproject.moudle.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.PhotoReleaseAdapter;
import com.personal.noncommercial.significantproject.dialog.CustomDialogPhotoImp;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.inter.OnCameraAndAlbum;
import com.personal.noncommercial.significantproject.moudle.inter.OnPhotoJiuGongPic;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;
import com.personal.noncommercial.significantproject.pop.PopupWindowPhotoImp;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.dialog.CustomDialog;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.view.weight.SpacesItemDecoration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.utils.FileUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author :lizhengcao
 * @date :2018/4/10
 * E-mail:lizc@bsoft.com.cn
 * @类说明 九宫格图片
 */

public class PhotoActivity2 extends BaseActivity implements OnCameraAndAlbum, OnPhotoJiuGongPic {

    private static final String TAG = PhotoActivity2.class.getSimpleName();
    public static final int MAX_COUNT = 9;
    private static final int SPAN_COUNT = 3;
    //相机返回码
    private static final int REQUEST_CODE_CAMERA = 0x10;
    //相册返回码
    private static final int REQUEST_CODE_ALBUM = 0x01;

    @BindString(R.string.jiuGongGePicture)
    String jiuGongGePictures;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private PopupWindowPhotoImp pw;
    //存放图片的路径
    private ArrayList<String> mSelectPath;

    private PhotoReleaseAdapter adapter;
    private CustomDialogPhotoImp dialog;
    //压缩后的图片转化为文件的集合
    private List<File> mFileList;
    //相机临时文件夹
    private File mTmpFile;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle(jiuGongGePictures);
        mSelectPath = new ArrayList<>();
        mFileList = new ArrayList<>();

        mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(1)));
        adapter = new PhotoReleaseAdapter(mContext, mSelectPath, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, SPAN_COUNT));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_photo2;
    }


    @Override
    public void onCamera() {
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION)) {
            camera();
        }

    }

    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            try {
                mTmpFile = FileUtils.createTmpFile(mContext);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Uri tempUri;

            if (mTmpFile != null && mTmpFile.exists()) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //Fix the Android N+ file can't be send
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    tempUri = FileProvider.getUriForFile(mContext,
                            getApplicationContext().getPackageName() + ".provider", mTmpFile);
                } else {
                    tempUri = Uri.fromFile(mTmpFile);
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            } else {
                ToastUtils.showToastShort("图片错误");
            }
        } else {
            ToastUtils.showToastShort("系统相机未找到");
        }
    }

    @Override
    public void onAlbum() {
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_WRITE_ES_PERMISSION)) {
            //权限已同意过
            album();
        }
    }

    private void album() {
        MultiImageSelector selector = MultiImageSelector.create();
        selector.showCamera(false);
        selector.count(MAX_COUNT);
        selector.multi();
        selector.origin(mSelectPath);
        selector.start(this, REQUEST_CODE_ALBUM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.REQUEST_WRITE_ES_PERMISSION:
                //相册权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    //第一次同意授权
                    album();
                else
                    PermissionHelper.addAlbumPermissionDialog(mContext);
                break;
            case PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION:
                if (grantResults.length > 0 && PermissionHelper.checkAllPermissionResult(grantResults))
                    camera();
                else
                    PermissionHelper.addCameraPermissionDialog(mContext);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ALBUM:
                    //相册返回
                    mSelectPath.clear();
                    mSelectPath.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                    adapter.notifyDataSetChanged();
                    break;
                case REQUEST_CODE_CAMERA:
                    //相机返回
                    if (mTmpFile != null) {
                        Uri tempUri;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            tempUri = FileProvider.getUriForFile(mContext,
                                    getApplicationContext().getPackageName() + ".provider", mTmpFile);
                        } else {
                            tempUri = Uri.fromFile(mTmpFile);
                        }

                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, tempUri));
                        mSelectPath.add(mTmpFile.getAbsolutePath());
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pw != null)
            deleteFile();
    }

    //删除文件
    private void deleteFile() {
        if (mTmpFile != null && mTmpFile.exists()) {
            boolean success = mTmpFile.delete();
            if (success) {
                mTmpFile = null;
            }
        }
    }

    @Override
    public void showPopWindow() {
        pw = new PopupWindowPhotoImp(mContext, this);
        pw.setPopWindow();
    }

    @Override
    public void showCustomDialog(int position) {
        if (dialog == null)
            dialog = new CustomDialogPhotoImp(mContext, this);
        dialog.setCustomDialog(position);
    }

    @Override
    public void deletePhotoItem(int pos) {
        mSelectPath.remove(pos);
        adapter.notifyDataSetChanged();
    }

    //压缩图片
    @OnClick(R.id.btn_compress)
    public void doClick() {

        mFileList.clear();

        if (mSelectPath == null) {
            return;
        }

        //异步调用
        Luban.with(mContext)
                .load(mSelectPath)  // 传人要压缩的图片列表
                .ignoreBy(100)       // 忽略不压缩图片的大小
                .setTargetDir(getPath())    // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI

                    }

                    @Override
                    public void onSuccess(File file) {
                        //压缩成功后调用，返回压缩后的图片文件
                        mFileList.add(file);
                        //文件全部压缩成功后上传服务器
                        if (mFileList.size() == mSelectPath.size()) {
                            //此时图片已全部压缩
                            for (File f : mFileList) {
                                //f.getName();
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // 当压缩过程出现问题时调用
                    }
                }).launch(); //启动压缩
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
