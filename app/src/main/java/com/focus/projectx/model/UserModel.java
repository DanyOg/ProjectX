package com.focus.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Focus on 19.03.2017.
 */

public class UserModel implements Parcelable {
    @SerializedName("user")
    @Expose
    private UserInfoModel user;
    @SerializedName("token")
    @Expose
    private String token;

    public UserModel(Parcel in) {
        token  = in.readString();
        user   = (UserInfoModel) in.readParcelable(UserInfoModel.class.getClassLoader());
    }

    public UserModel(){}

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public UserInfoModel getUser() {
        return user;
    }

    public void setUser(UserInfoModel user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeParcelable(user, flags);
    }
}
