package com.personal.noncommercial.significantproject.moudle.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.adapter.MatissePhotoReleaseAdapter;
import com.personal.noncommercial.significantproject.dialog.CustomDialogPhotoImp;
import com.personal.noncommercial.significantproject.moudle.base.BaseActivity;
import com.personal.noncommercial.significantproject.moudle.inter.OnCameraAndAlbum;
import com.personal.noncommercial.significantproject.moudle.inter.OnPhotoJiuGongPic;
import com.personal.noncommercial.significantproject.permission.PermissionHelper;
import com.personal.noncommercial.significantproject.pop.PopupWindowPhotoImp;
import com.personal.noncommercial.significantproject.retrofitutil.ui.LoadingDialog;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.utils.FileUtil;
import com.personal.noncommercial.significantproject.utils.ToastUtils;
import com.personal.noncommercial.significantproject.view.weight.SpacesItemDecoration;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    //相册和相机返回码
    private static final int REQUEST_CODE_ALBUM = 0x01;

    @BindString(R.string.jiuGongGePicture)
    String jiuGongGePictures;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private PopupWindowPhotoImp pw;
    //知乎存放的图片路径
    private List<String> mMatisseSelectPath;
    private CustomDialogPhotoImp dialog;
    //压缩后的图片转化为文件的集合
    private List<File> mFileList;
    private MatissePhotoReleaseAdapter matisseAdapter;

    @Override
    protected void initOnCreate(@Nullable Bundle savedInstanceState) {
        setCurrentTitle(jiuGongGePictures);
        mFileList = new ArrayList<>();
        mMatisseSelectPath = new ArrayList<>();

        mRecyclerView.addItemDecoration(new SpacesItemDecoration(DensityUtils.dp2px(1)));
        matisseAdapter = new MatissePhotoReleaseAdapter(mContext, mMatisseSelectPath, this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, SPAN_COUNT));
        mRecyclerView.setAdapter(matisseAdapter);
    }

    @Override
    protected int getRootLayout() {
        return R.layout.activity_photo2;
    }


    @Override
    public void onCamera() {
        ToastUtils.showToastShort("相机功能已合并到相册功能");
    }

    @Override
    public void onAlbum() {//相册和相机合并后的权限
        if (PermissionHelper.checkPermission(this, PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION)) {
            //权限已同意过
            album();
        }
    }

    private void album() {
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .capture(true)
                .captureStrategy(new CaptureStrategy(
                        true, getApplicationContext().getPackageName() + ".provider"))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .maxSelectable(MAX_COUNT)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(new GlideEngine())
                .forResult(REQUEST_CODE_ALBUM);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.REQUEST_CAMERA_AND_WRITE_ES_PERMISSION:
                //相册权限 grantResults[0] == PackageManager.PERMISSION_GRANTED 单独只有一个权限时的处理方式
                if (grantResults.length > 0 && PermissionHelper.checkAllPermissionResult(grantResults))
                    //第一次同意授权
                    album();
                else
                    PermissionHelper.addAlbumPermissionDialog(mContext);
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
                    mMatisseSelectPath.clear();
                    mMatisseSelectPath.addAll(Matisse.obtainPathResult(data));
                    matisseAdapter.notifyDataSetChanged();
                    break;
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
        mMatisseSelectPath.remove(pos);
        matisseAdapter.notifyDataSetChanged();
    }

    private LoadingDialog mLoadingDialog;

    //压缩图片
    @OnClick(R.id.btn_compress)
    public void doClick() {

        mFileList.clear();

        if (mMatisseSelectPath == null || mMatisseSelectPath.size() == 0) {
            return;
        }

        mLoadingDialog = new LoadingDialog(mContext);
        //异步调用
        Luban.with(mContext)
                .load(mMatisseSelectPath)  // 传人要压缩的图片列表
                .ignoreBy(100) // 忽略不压缩图片的大小
                .setTargetDir(getPath())    // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // 压缩开始前调用，可以在方法内启动 loading UI
                        if (mLoadingDialog != null && !mLoadingDialog.isShowing())
                            mLoadingDialog.show();
                    }

                    @Override
                    public void onSuccess(File file) {
                        //压缩成功后调用，返回压缩后的图片文件
                        mFileList.add(file);
                        Log.e("文件路径：", file.toString());
                        //文件全部压缩成功后上传服务器
                        if (mFileList.size() == mMatisseSelectPath.size()) {
                            //此时图片已全部压缩
                            List<MultipartBody.Part> parts = new ArrayList<>();
                            for (int i = 0; i < mFileList.size(); i++) {
                                File f = mFileList.get(i);
                                RequestBody body = RequestBody.create(MediaType.parse("image/*"), f);
                                MultipartBody.Part part =
                                        MultipartBody.Part.createFormData("filename" + i, f.getName(), body);
                                parts.add(part);
                            }

                            //清空上传图片的列表
                            mMatisseSelectPath.clear();
                            matisseAdapter.notifyDataSetChanged();
                            //删除文件夹里面的文件
                            FileUtil.deleteFilesInDir(getPath());

                            //隐藏加载对话框，实际开发中应该在网络请求结束时操作
                            if (mLoadingDialog != null && mLoadingDialog.isShowing())
                                mLoadingDialog.dismiss();

//                            Call<ResultModel<Object>> call = ApiService.newInstance().getApiInterface().uploadMorePicture("第一个字段", parts);
//                            call.enqueue(new OnResponseListener<ResultModel<Object>>(mContext) {
//                                @Override
//                                public void onSuccess(ResultModel<Object> result) {
//                                    ToastUtils.showToastShort("图片上传成功");
//                                    if (mLoadingDialog != null && mLoadingDialog.isShowing())
//                                        mLoadingDialog.dismiss();
//                                }
//
//                                @Override
//                                protected boolean needLoadingDialog() {
//                                    return false;
//                                }
//                            });
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        // 当压缩过程出现问题时调用
                        ToastUtils.showToastShort(throwable.getMessage());
                    }
                }).launch(); //启动压缩
    }

    /**
     * 鲁班压缩图片后存储的路径文件夹
     *
     * @return
     */
    private String getPath() {
        String path = FileUtil.getRootPath() + "/luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            //true 文件夹不存在需创建   false 文件夹已存在
            return path;
        }
        return path;
    }
}
