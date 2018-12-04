package com.zs.project.bean.wangyi

/**
 *
Created by zs
Date：2018年 12月 04日
Time：14:07
—————————————————————————————————————
About: 网易新闻
—————————————————————————————————————
 */

data class Article(
    var modelmode: String?,
    var hasImg: Int?,
    var digest: String?,
    var skipType: String?,
    var commentCount: Int?,
    var url: String?,
    var docid: String?,
    var title: String?,
    var imgextra: MutableList<Imgextra?>?,
    var source: String?,
    var priority: Int?,
    var liveInfo: Any?,
    var skipURL: String?,
    var imgsrc: String?,
    var stitle: String?,
    var photosetID: String?,
    var ptime: String?,
    var imgsrc3gtype: String?
)

data class Imgextra(
    var imgsrc: String?
)