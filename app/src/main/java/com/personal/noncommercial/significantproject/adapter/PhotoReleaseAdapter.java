package com.personal.noncommercial.significantproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.personal.noncommercial.significantproject.R;
import com.personal.noncommercial.significantproject.moudle.inter.OnPhotoJiuGongPic;
import com.personal.noncommercial.significantproject.moudle.view.PhotoActivity2;
import com.personal.noncommercial.significantproject.utils.DensityUtils;
import com.personal.noncommercial.significantproject.view.SquaredImageView;

import java.util.ArrayList;

/**
 * @author :lizhengcao
 * @date :2018/4/10
 * E-mail:lizc@bsoft.com.cn
 * @类说明 多图片的选择器的适配器
 */

public class PhotoReleaseAdapter extends RecyclerView.Adapter<PhotoReleaseAdapter.PhotoViewHolder> {


    private Context mContext;

    private ArrayList<String> mSelectPath;
    private OnPhotoJiuGongPic mPhotoJiuGongPicListener;

    public PhotoReleaseAdapter(Context context, ArrayList<String> selectPath, OnPhotoJiuGongPic photoJiuGongPicListener) {
        this.mContext = context;
        this.mSelectPath = selectPath;
        this.mPhotoJiuGongPicListener = photoJiuGongPicListener;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_release_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(position == mSelectPath.size() ? "" : mSelectPath.get(position))
                .asBitmap()
                .placeholder(R.mipmap.release_add_img)
                .into(holder.mSquaredImageView);

        if (position == mSelectPath.size()) {
            holder.mIvDelete.setVisibility(View.GONE);
        } else {
            holder.mIvDelete.setVisibility(View.VISIBLE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示popWindow
                if (mSelectPath.size() == position) {
                    mPhotoJiuGongPicListener.showPopWindow();
                }
            }
        });

        //删除图片
        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoJiuGongPicListener.showCustomDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mSelectPath.size() == PhotoActivity2.MAX_COUNT ?
                mSelectPath.size() : mSelectPath.size() + 1;
    }


    class PhotoViewHolder extends RecyclerView.ViewHolder {

        SquaredImageView mSquaredImageView;
        ImageView mIvDelete;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mSquaredImageView = (SquaredImageView) itemView.findViewById(R.id.squared_image_view);
            mIvDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }

}
