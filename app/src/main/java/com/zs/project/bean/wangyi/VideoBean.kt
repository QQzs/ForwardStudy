package com.zs.project.bean.wangyi

import java.io.Serializable

/**
 *
Created by zs
Date：2018年 12月 05日
Time：16:29
—————————————————————————————————————
About: 网易视频
—————————————————————————————————————
 */
data class VideoBean(
    var sizeSHD: Int?,
    var replyCount: Int?,
    var videosource: String?,
    var mp4Hd_url: Any?,
    var cover: String?,
    var title: String?,
    var description: String?,
    var replyid: String?,
    var length: Long?,
    var m3u8_url: String?,
    var vid: String?,
    var topicName: String?,
    var votecount: Int?,
    var topicImg: String?,
    var topicDesc: String?,
    var topicSid: String?,
    var replyBoard: String?,
    var playCount: String?,
    var sectiontitle: String?,
    var mp4_url: String?,
    var playersize: Int?,
    var sizeHD: Int?,
    var sizeSD: Int?,
    var m3u8Hd_url: Any?,
    var ptime: String?
): Serializable