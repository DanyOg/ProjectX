package com.focus.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Focus on 19.03.2017.
 */

public class AllUserModel implements Parcelable {
    @SerializedName("Persons")
    @Expose
    private List<UserInfoModel> user = new ArrayList<UserInfoModel>();

    public AllUserModel(Parcel in) {
        in.readList(user, null);
    }

    public AllUserModel(){}

    public static final Creator<AllUserModel> CREATOR = new Creator<AllUserModel>() {
        @Override
        public AllUserModel createFromParcel(Parcel in) {
            return new AllUserModel(in);
        }

        @Override
        public AllUserModel[] newArray(int size) {
            return new AllUserModel[size];
        }
    };

    public List<UserInfoModel> getUser() {
        return user;
    }

    public void setUser(List<UserInfoModel> user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(user);
    }
}