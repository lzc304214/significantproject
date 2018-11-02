package com.personal.noncommercial.significantproject.moudle.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * @author :lizhengcao
 * @date :2018/10/15
 * E-mail:lizc@bsoft.com.cn
 * @类说明 Builder模式的测试实体类
 */

public class Person implements Parcelable {

    //姓名 年龄 性别 爱好
    private String name;
    private int age;
    private String gender;
    private String hobby;

    private Person() {

    }

    protected Person(Parcel in) {
        name = in.readString();
        age = in.readInt();
        gender = in.readString();
        hobby = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getHobby() {
        return hobby;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(gender);
        dest.writeString(hobby);
    }

    public static class Builder {

        private Person mPerson;

        public Builder() {
            mPerson = new Person();
        }

        public Builder setName(String name) {
            mPerson.name = name;
            return this;
        }

        public Builder setAge(int age) {
            mPerson.age = age;
            return this;
        }

        public Builder setGender(String gender) {
            mPerson.gender = gender;
            return this;
        }

        public Builder setHobby(String hobby) {
            mPerson.hobby = hobby;
            return this;
        }

        public Person build() {
            return mPerson;
        }

    }
}
