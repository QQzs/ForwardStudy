package com.zs.project.bean;

import java.io.Serializable;


public class Channel implements Serializable {
    
    public static final int TYPE_MY = 1;            // 已关注的标签title
    public static final int TYPE_OTHER = 2;         // 推荐的标签title
    public static final int TYPE_MY_CHANNEL = 3;    // 已关注的标签
    public static final int TYPE_OTHER_CHANNEL = 4; // 已关注的标签
    public static final int TYPE_NORMAL = 5;        // 默认不可删除的标签
    public String titleName;
    public String titleCode;
    private int itemType;

    public Channel(String titleName, int itemType) {
        this.titleName = titleName;
        this.itemType = itemType;
    }

    public Channel(String name, String pk) {
        this(TYPE_MY_CHANNEL, name, pk);
    }

    public Channel(int type, String name, String pk) {
        titleName = name;
        titleCode = pk;
        itemType = type;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "titleName='" + titleName + '\'' +
                ", titleCode='" + titleCode + '\'' +
                ", itemType=" + itemType +
                '}';
    }
}
