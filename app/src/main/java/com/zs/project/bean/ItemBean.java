package com.zs.project.bean;

/**
 * Created by zs
 * Date：2018年 02月 05日
 * Time：13:41
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ItemBean {

    private int iconId;
    private String itemTitle;
    private int type;
    private int flag;

    public ItemBean(int iconId, String itemTitle, int type) {
        this.iconId = iconId;
        this.itemTitle = itemTitle;
        this.type = type;
    }

    public ItemBean(int iconId, String itemTitle, int type, int flag) {
        this.iconId = iconId;
        this.itemTitle = itemTitle;
        this.type = type;
        this.flag = flag;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
