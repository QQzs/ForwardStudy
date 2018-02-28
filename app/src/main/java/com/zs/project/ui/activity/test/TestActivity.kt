package com.zs.project.ui.activity.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zs.project.R
import com.zs.project.bean.CardBannerEntry
import com.zs.project.bean.ImageBannerEntry
import com.zs.project.util.transform.DepthPageTransformer
import com.zs.project.util.transform.ZoomOutPageTransformer
import com.zs.project.view.banner.page.CenterBigTransformer
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()

    }

    fun initView(){

        //设置自定义翻页动画改变器，也可以不设置。如果没有设置，则使用ViewPager默认的翻页动画。
        banner_view1?.setPageTransformer(true, DepthPageTransformer())
        //getData()方法是从网络上获取数据。这里只是伪代码。
        val bannerEntries = getData1()
        //设置数据源并开始轮播。如果不希望启动轮播则调用两个参数的方法。
        banner_view1?.entries = bannerEntries


        banner_view2?.setPageTransformer(true, ZoomOutPageTransformer())
        val bannerEntries2 = getData2()
        banner_view2?.entries = bannerEntries2

        banner_view3?.setPageTransformer(true, CenterBigTransformer(0.9f))
        val bannerEntries3 = getData3()
        banner_view3?.setEntries(bannerEntries3 , false)
        banner_view3?.setShowLeftAndRightPage(20)
    }


    fun getData1() : MutableList<CardBannerEntry>{
        var items = ArrayList<CardBannerEntry>()
        items.add(CardBannerEntry("大话西游：“炸毛韬”引诱老妖", "更新至50集", "http://m.qiyipic.com/common/lego/20171026/dd116655c96d4a249253167727ed37c8.jpg"))
        items.add(CardBannerEntry("天使之路：藏风大片遇高反危机", "10-29期", "http://m.qiyipic.com/common/lego/20171029/c9c3800f35f84f1398b89740f80d8aa6.jpg"))
        items.add(CardBannerEntry("星空海2：陆漓设局害惨吴居蓝", "更新至30集", "http://m.qiyipic.com/common/lego/20171023/bd84e15d8dd44d7c9674218de30ac75c.jpg"))
        items.add(CardBannerEntry("中国职业脱口秀大赛：狂笑首播", "10-28期", "http://m.qiyipic.com/common/lego/20171028/f1b872de43e649ddbf624b1451ebf95e.jpg"))
        items.add(CardBannerEntry("奇秀好音乐，你身边的音乐真人秀", null, "http://pic2.qiyipic.com/common/20171027/cdc6210c26e24f08940d36a5eb918c34.jpg"))
        return items
    }

    fun getData2() : MutableList<ImageBannerEntry>{
        var items = ArrayList<ImageBannerEntry>()
        items.add(ImageBannerEntry("大话西游：“炸毛韬”引诱老妖", "更新至50集", "http://m.qiyipic.com/common/lego/20171026/dd116655c96d4a249253167727ed37c8.jpg"))
        items.add(ImageBannerEntry("天使之路：藏风大片遇高反危机", "10-29期", "http://m.qiyipic.com/common/lego/20171029/c9c3800f35f84f1398b89740f80d8aa6.jpg"))
        items.add(ImageBannerEntry("星空海2：陆漓设局害惨吴居蓝", "更新至30集", "http://m.qiyipic.com/common/lego/20171023/bd84e15d8dd44d7c9674218de30ac75c.jpg"))
        items.add(ImageBannerEntry("中国职业脱口秀大赛：狂笑首播", "10-28期", "http://m.qiyipic.com/common/lego/20171028/f1b872de43e649ddbf624b1451ebf95e.jpg"))
        items.add(ImageBannerEntry("奇秀好音乐，你身边的音乐真人秀", null, "http://pic2.qiyipic.com/common/20171027/cdc6210c26e24f08940d36a5eb918c34.jpg"))
        return items
    }

    fun getData3() : MutableList<ImageBannerEntry>{
        var items = ArrayList<ImageBannerEntry>()
        items.add(ImageBannerEntry("大话西游：“炸毛韬”引诱老妖", "更新至50集", "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"))
        items.add(ImageBannerEntry("天使之路：藏风大片遇高反危机", "10-29期", "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"))
        items.add(ImageBannerEntry("星空海2：陆漓设局害惨吴居蓝", "更新至30集", "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"))
        items.add(ImageBannerEntry("中国职业脱口秀大赛：狂笑首播", "10-28期", "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"))
        items.add(ImageBannerEntry("奇秀好音乐，你身边的音乐真人秀", null, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2508618114.jpg"))
        return items
    }


}
