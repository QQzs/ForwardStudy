package com.zs.project.event;

import java.util.ArrayList;

/**
 * Created by zs
 * Date：2018年 03月 30日
 * Time：17:02
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class SelectImageEvent {

    private String type;
    private ArrayList<String> mImages;

    public SelectImageEvent(String type, ArrayList<String> mImages) {
        this.type = type;
        this.mImages = mImages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public void setmImages(ArrayList<String> mImages) {
        this.mImages = mImages;
    }

    @Override
    public String toString() {
        return "SelectImageEvent{" +
                "type='" + type + '\'' +
                ", mImages=" + mImages +
                '}';
    }
}

