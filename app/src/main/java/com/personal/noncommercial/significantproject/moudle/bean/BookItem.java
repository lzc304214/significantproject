package com.personal.noncommercial.significantproject.moudle.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author :lizhengcao
 * @date :2018/5/30
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class BookItem implements Parcelable {

    private String des;
    private String price;

    protected BookItem(Parcel in) {
        des = in.readString();
        price = in.readString();
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(des);
        dest.writeString(price);
    }
}
