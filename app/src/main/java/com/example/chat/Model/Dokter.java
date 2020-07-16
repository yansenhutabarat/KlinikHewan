package com.example.chat.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Dokter extends ArrayList<Parcelable> implements Parcelable {
    private String nama, username, password, dokterId;
    private long rating;

    public Dokter() {
    }

    protected Dokter(Parcel in) {
        nama = in.readString();
        username = in.readString();
        password = in.readString();
        dokterId = in.readString();
        rating = in.readLong();
    }

    public static final Creator<Dokter> CREATOR = new Creator<Dokter>() {
        @Override
        public Dokter createFromParcel(Parcel in) {
            return new Dokter(in);
        }

        @Override
        public Dokter[] newArray(int size) {
            return new Dokter[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getDokterId() {
        return dokterId;
    }

    public void setDokterId(String dokterId) {
        this.dokterId = dokterId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(dokterId);
        dest.writeLong(rating);
    }
}
