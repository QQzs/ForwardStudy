package com.zs.project.bean;

/**
 * Created by zs
 * Date：2018年 02月 08日
 * Time：17:04
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ColorBean {

    private int index;

    private boolean choose;

    private int iconId;

    private String name;

    public ColorBean(int index) {
        this.index = index;
    }

    public ColorBean(int index, int id , String name) {
        this.index = index;
        this.iconId = id;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
