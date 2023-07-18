package com.abeldandi.alitasecurity;

import android.os.Parcel;
import android.os.Parcelable;

public class ListElement implements Parcelable {
    public String color;
    public String securityname;
    public String idnumber;

    public ListElement(String color, String securityname, String idnumber) {
        this.color = color;
        this.securityname = securityname;
        this.idnumber = idnumber;
    }

    protected ListElement(Parcel in) {
        color = in.readString();
        securityname = in.readString();
        idnumber = in.readString();
    }

    public static final Creator<ListElement> CREATOR = new Creator<ListElement>() {
        @Override
        public ListElement createFromParcel(Parcel in) {
            return new ListElement(in);
        }

        @Override
        public ListElement[] newArray(int size) {
            return new ListElement[size];
        }
    };

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSecurityname() {
        return securityname;
    }

    public void setSecurityname(String securityname) {
        this.securityname = securityname;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(color);
        dest.writeString(securityname);
        dest.writeString(idnumber);
    }
}

