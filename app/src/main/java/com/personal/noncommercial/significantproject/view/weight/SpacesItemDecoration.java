package com.personal.noncommercial.significantproject.view.weight;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.personal.noncommercial.significantproject.moudle.view.PhotoActivity2;

/**
 * 设置item之间的分割空间
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.top = space;
        outRect.right = space;
        outRect.bottom = space;
    }
}
