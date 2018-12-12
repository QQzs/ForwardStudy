package com.zs.project.greendao;

import com.zs.project.bean.news.Imgextra;
import com.zs.project.greendao.convert.ImgextraConvert;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;

/**
 * Created by zs
 * Date：2018年 12月 06日
 * Time：15:27
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
@Entity
public class ArticleData {
    /**
     * modelmode : u
     * digest :
     * skipType : photoset
     * commentCount : 616
     * url : 00AJ0003|661183
     * docid : 0003set661183
     * title : 曲线美
     * imgextra : [{"imgsrc":"http://cms-bucket.nosdn.127.net/2018/12/06/c0c9f0ee94b5402bb93b61491c8294a6.jpeg"},{"imgsrc":"http://cms-bucket.nosdn.127.net/2018/12/06/5f3d8911dcc6460681bcb87ee002125a.jpeg"}]
     * source : 视觉中国
     * priority : 250
     * liveInfo : null
     * skipURL : http://3g.163.com/touch/photoview.html?channelid=0003&setid=661183
     * imgsrc : http://cms-bucket.nosdn.127.net/2018/12/06/f38e5747c5a9448c9c8a2fea0a057428.jpeg
     * stitle : 00AJ0003|661183
     * photosetID : 0003|661183
     * ptime : 2018-12-06 07:54:42
     * imgsrc3gtype : 2
     */

    @Id(autoincrement = true)
    private Long id;
    private String modelmode;
    private String hasImg;
    private String digest;
    private String skipType;
    private String commentCount;
    private String url;
    private String docid;
    private String title;
    private String source;
    private int priority;
    private String liveInfo;
    private String skipURL;
    private String imgsrc;
    private String stitle;
    private String photosetID;
    private String ptime;
    private String imgsrc3gtype;
    @Convert(converter = ImgextraConvert.class , columnType = String.class)
    private List<Imgextra> imgextra;
    @Generated(hash = 1014904073)
    public ArticleData(Long id, String modelmode, String hasImg, String digest, String skipType, String commentCount, String url, String docid, String title, String source, int priority, String liveInfo,
            String skipURL, String imgsrc, String stitle, String photosetID, String ptime, String imgsrc3gtype, List<Imgextra> imgextra) {
        this.id = id;
        this.modelmode = modelmode;
        this.hasImg = hasImg;
        this.digest = digest;
        this.skipType = skipType;
        this.commentCount = commentCount;
        this.url = url;
        this.docid = docid;
        this.title = title;
        this.source = source;
        this.priority = priority;
        this.liveInfo = liveInfo;
        this.skipURL = skipURL;
        this.imgsrc = imgsrc;
        this.stitle = stitle;
        this.photosetID = photosetID;
        this.ptime = ptime;
        this.imgsrc3gtype = imgsrc3gtype;
        this.imgextra = imgextra;
    }
    @Generated(hash = 1559847312)
    public ArticleData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getModelmode() {
        return this.modelmode;
    }
    public void setModelmode(String modelmode) {
        this.modelmode = modelmode;
    }
    public String getHasImg() {
        return this.hasImg;
    }
    public void setHasImg(String hasImg) {
        this.hasImg = hasImg;
    }
    public String getDigest() {
        return this.digest;
    }
    public void setDigest(String digest) {
        this.digest = digest;
    }
    public String getSkipType() {
        return this.skipType;
    }
    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }
    public String getCommentCount() {
        return this.commentCount;
    }
    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDocid() {
        return this.docid;
    }
    public void setDocid(String docid) {
        this.docid = docid;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSource() {
        return this.source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public int getPriority() {
        return this.priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getLiveInfo() {
        return this.liveInfo;
    }
    public void setLiveInfo(String liveInfo) {
        this.liveInfo = liveInfo;
    }
    public String getSkipURL() {
        return this.skipURL;
    }
    public void setSkipURL(String skipURL) {
        this.skipURL = skipURL;
    }
    public String getImgsrc() {
        return this.imgsrc;
    }
    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }
    public String getStitle() {
        return this.stitle;
    }
    public void setStitle(String stitle) {
        this.stitle = stitle;
    }
    public String getPhotosetID() {
        return this.photosetID;
    }
    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }
    public String getPtime() {
        return this.ptime;
    }
    public void setPtime(String ptime) {
        this.ptime = ptime;
    }
    public String getImgsrc3gtype() {
        return this.imgsrc3gtype;
    }
    public void setImgsrc3gtype(String imgsrc3gtype) {
        this.imgsrc3gtype = imgsrc3gtype;
    }
    public List<Imgextra> getImgextra() {
        return this.imgextra;
    }
    public void setImgextra(List<Imgextra> imgextra) {
        this.imgextra = imgextra;
    }
}
