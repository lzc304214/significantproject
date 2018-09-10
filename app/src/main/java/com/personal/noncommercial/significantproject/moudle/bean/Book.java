package com.personal.noncommercial.significantproject.moudle.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :lizhengcao
 * @date :2018/5/30
 * E-mail:lizc@bsoft.com.cn
 * @类说明
 */

public class Book implements Parcelable {
    //普通类型
    private int age;
    private String name;
    private boolean isMale;

    //对象
    private BookItem bookItem;

    private List<String> bookName;

    private List<BookItem> mBookItems = new ArrayList<>();

    public Book() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public void setBookItem(BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public List<String> getBookName() {
        return bookName;
    }

    public void setBookName(List<String> bookName) {
        this.bookName = bookName;
    }

    public List<BookItem> getBookItems() {
        return mBookItems;
    }

    public void setBookItems(List<BookItem> bookItems) {
        mBookItems = bookItems;
    }

    protected Book(Parcel in) {
        age = in.readInt();
        name = in.readString();
        isMale = in.readByte() != 0;
        bookItem = in.readParcelable(BookItem.class.getClassLoader());
        bookName = in.createStringArrayList();
        mBookItems = in.createTypedArrayList(BookItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
        dest.writeString(name);
        dest.writeByte((byte) (isMale ? 1 : 0));
        dest.writeParcelable(bookItem, flags);
        dest.writeStringList(bookName);
        dest.writeTypedList(mBookItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
