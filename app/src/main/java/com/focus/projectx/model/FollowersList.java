package com.focus.projectx.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Focus on 19.03.2017.
 */

public class FollowersList implements Parcelable {
    @SerializedName("Person_ID")
    @Expose
    private Integer personID;
    @SerializedName("Person_LastName")
    @Expose
    private String personLastName;
    @SerializedName("Person_FirstName")
    @Expose
    private String personFirstName;
    @SerializedName("Person_Login")
    @Expose
    private String personLogin;
    @SerializedName("Person_Description")
    @Expose
    private String personDescription;
    @SerializedName("Person_Avatar")
    @Expose
    private String personAvatar;

    protected FollowersList(Parcel in) {
        personID = in.readInt();
        personLastName = in.readString();
        personFirstName = in.readString();
        personLogin = in.readString();
        personDescription = in.readString();
        personAvatar = in.readString();
    }

    public static final Creator<FollowersList> CREATOR = new Creator<FollowersList>() {
        @Override
        public FollowersList createFromParcel(Parcel in) {
            return new FollowersList(in);
        }

        @Override
        public FollowersList[] newArray(int size) {
            return new FollowersList[size];
        }
    };

    public Integer getPersonID() {
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLogin() {
        return personLogin;
    }

    public void setPersonLogin(String personLogin) {
        this.personLogin = personLogin;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public String getPersonAvatar() {
        return personAvatar;
    }

    public void setPersonAvatar(String personAvatar) {
        this.personAvatar = personAvatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(personID);
        dest.writeString(personLastName);
        dest.writeString(personFirstName);
        dest.writeString(personLogin);
        dest.writeString(personDescription);
        dest.writeString(personAvatar);
    }
}
