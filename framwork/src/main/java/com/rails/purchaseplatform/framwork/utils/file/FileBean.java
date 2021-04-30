package com.rails.purchaseplatform.framwork.utils.file;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sk on 2017/4/21.
 */

public class FileBean implements Parcelable {

    public String filename;
    public ArrayList<String> filecontent = new ArrayList<String>();

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ArrayList<String> getFilecontent() {
        return filecontent;
    }

    public void setFilecontent(ArrayList<String> filecontent) {
        this.filecontent = filecontent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(filename);
        parcel.writeList(filecontent);
    }


    public static final Creator<FileBean> CREATOR = new Creator<FileBean>() {
        @Override
        public FileBean createFromParcel(Parcel in) {
            FileBean fileBean = new FileBean();
            fileBean.setFilename(in.readString());
            fileBean.setFilecontent(in.readArrayList(FileBean.class.getClassLoader()));
            return fileBean;
        }

        @Override
        public FileBean[] newArray(int size) {
            return null;
        }
    };
}
