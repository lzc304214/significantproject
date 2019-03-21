package com.personal.noncommercial.significantproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.inter.OnPhotoJiuGongPic;
import com.personal.noncommercial.significantproject.moudle.view.PhotoActivity2;
import com.personal.noncommercial.significantproject.view.SquaredImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author :lizhengcao
 * @date :2018/4/10
 * E-mail:lizc@bsoft.com.cn
 * @类说明 知乎多图片的选择器的适配器
 */

public class MatissePhotoReleaseAdapter extends RecyclerView.Adapter<MatissePhotoReleaseAdapter.PhotoViewHolder> {


    private Context mContext;

    private List<String> mMatisseSelectPath;
    private OnPhotoJiuGongPic mPhotoJiuGongPicListener;

    public MatissePhotoReleaseAdapter(Context context, List<String> matisseSelectPath, OnPhotoJiuGongPic photoJiuGongPicListener) {
        this.mContext = context;
        this.mMatisseSelectPath = matisseSelectPath;
        this.mPhotoJiuGongPicListener = photoJiuGongPicListener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_release_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(mContext)
                .load(position == mMatisseSelectPath.size() ? "" : mMatisseSelectPath.get(position))
                .asBitmap()
                .placeholder(R.mipmap.release_add_img)
                .into(holder.mSquaredImageView);

        if (position == mMatisseSelectPath.size()) {
            holder.mIvDelete.setVisibility(View.GONE);
        } else {
            holder.mIvDelete.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(v -> {
            //显示popWindow
            if (mMatisseSelectPath.size() == position) {
                mPhotoJiuGongPicListener.showPopWindow();
            }
        });

        //删除图片
        holder.mIvDelete.setOnClickListener(v ->
                mPhotoJiuGongPicListener.showCustomDialog(position));
    }

    @Override
    public int getItemCount() {

        return mMatisseSelectPath.size() == PhotoActivity2.MAX_COUNT ?
                mMatisseSelectPath.size() : mMatisseSelectPath.size() + 1;
    }


    static class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.squared_image_view)
        SquaredImageView mSquaredImageView;
        @BindView(R.id.iv_delete)
        ImageView mIvDelete;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
