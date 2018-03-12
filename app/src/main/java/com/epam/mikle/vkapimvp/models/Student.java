package com.epam.mikle.vkapimvp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;

import static com.epam.mikle.vkapimvp.VKApplication.TAG;

public class Student implements Parcelable{
    public final String fullName;
    public final String vkDomain;
    public int randNumber;
    public String vkId;
    public File photo;
    public boolean isOwner;

    public Student(Parcel in){
        fullName = in.readString();
        vkDomain = in.readString();
        randNumber = in.readInt();
        vkId = in.readString();
    }

    public Student(String fullName, String vkDomain){
        this.fullName = fullName;
        this.vkDomain = vkDomain;
    }

    public boolean isRandNumberNull(){
        return randNumber == 0;
    }

    public boolean isVkIdNull(){
        return (vkId == null || vkId.equals(""));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(vkDomain);
        dest.writeInt(randNumber);
        dest.writeString(vkId);
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        // распаковываем объект из Parcel
        public Student createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel");
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
