package com.zs.project.bean.news;

import java.io.Serializable;


public class QaTagInfo implements Serializable {
    public static final int TYPE_MY = 1;            // 已关注的标签title
    public static final int TYPE_OTHER = 2;         // 推荐的标签title
    public static final int TYPE_MY_CHANNEL = 3;    // 已关注的标签
    public static final int TYPE_OTHER_CHANNEL = 4; // 已关注的标签
    public static final int TYPE_NORMAL = 5;        // 默认不可删除的标签
    public String tagname;
    public String tagpk;
    private int itemType;

    public QaTagInfo(){

    }

    public QaTagInfo(String tagname, int itemType) {
        this.tagname = tagname;
        this.itemType = itemType;
    }

    public QaTagInfo(String name, String pk) {
        this(TYPE_MY_CHANNEL, name, pk);
    }

    public QaTagInfo(int type, String name, String pk) {
        tagname = name;
        tagpk = pk;
        itemType = type;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public String getTagpk() {
        return tagpk;
    }

    public void setTagpk(String tagpk) {
        this.tagpk = tagpk;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }
}
