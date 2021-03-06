package com.zs.project.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.donkingliang.imageselector.ClipImageActivity.SELECT_IMAGE
import com.donkingliang.imageselector.ClipImageActivity.TAKE_PHOTO
import com.jaeger.library.StatusBarUtil
import com.zs.project.R
import com.zs.project.app.AppStatusManager
import com.zs.project.base.BaseActivity
import com.zs.project.event.RefreshEvent
import com.zs.project.ui.fragment.*
import com.zs.project.util.ScreenUtil
import com.zs.project.util.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.toast


/**
 * @author Administrator
 */
class MainActivity : BaseActivity() {

    private var mTransaction: FragmentTransaction? = null
    private var mCurrentFragment: Fragment? = null
    private var mFragments : ArrayList<Fragment> = arrayListOf()

    var mIsShow = true  // true：语音播放框滑动到显示状态  false：语音播放框移除屏幕状态
    var mExitTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
    }

    override fun init() {

        mTransaction = supportFragmentManager.beginTransaction()
        mFragments?.add(ArticleFragment())
        mFragments?.add(NewsFragment())
        mFragments?.add(VideoFragment())
        mFragments?.add(DouBanFragment())
        mFragments?.add(MeFragment())

        fragment_article?.setOnClickListener(this)
        fragment_news?.setOnClickListener(this)
        fragment_video?.setOnClickListener(this)
        fragment_douban?.setOnClickListener(this)
        fragment_me?.setOnClickListener(this)

        colorfull_bg_view?.changeImg(SpUtil.getInt("color_view",0))
//        colorfull_bg_view?.switchAnim(false)
        changePage(1)
        StatusBarUtil.setTranslucentForImageViewInFragment(this , 0 , null)
    }

    override fun initData() {

    }

    /**
     * 切换page
     */
    private fun changePage(index : Int){
        changeTab(index)
        changeFragment(index)
    }

    /**
     * fragment切换
     * @param nextFragment
     */
    private fun changeFragment(index: Int) {
        var nextFragment = mFragments[index]
        if (nextFragment != null) {
            if (mCurrentFragment != null) {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.hide(mCurrentFragment).commitAllowingStateLoss()
            }
            mCurrentFragment = nextFragment
            var transaction = supportFragmentManager.beginTransaction()
            if (nextFragment.isAdded) {
                transaction.show(nextFragment).commitAllowingStateLoss()
            } else {
                transaction.add(R.id.fl_homepage, nextFragment).commitAllowingStateLoss()
            }
        }
    }


    private fun changeTab(index : Int){
        dynamicAddView(tv_article, "textColor", R.color.main_color_gray)
        dynamicAddView(tv_news, "textColor", R.color.main_color_gray)
        dynamicAddView(tv_video, "textColor", R.color.main_color_gray)
        dynamicAddView(tv_product, "textColor", R.color.main_color_gray)
        dynamicAddView(tv_me, "textColor", R.color.main_color_gray)

        dynamicAddView(iv_home_article, "switchColor", R.color.main_color_gray)
        dynamicAddView(iv_home_news, "switchColor", R.color.main_color_gray)
        dynamicAddView(iv_home_video, "switchColor", R.color.main_color_gray)
        dynamicAddView(iv_home_product, "switchColor", R.color.main_color_gray)
        dynamicAddView(iv_home_me, "switchColor", R.color.main_color_gray)

        when(index){
            0 ->{
                dynamicAddView(tv_article, "textColor", R.color.app_main_color)
                dynamicAddView(iv_home_article, "switchColor", R.color.app_main_color)
            }
            1 ->{
                dynamicAddView(tv_news, "textColor", R.color.app_main_color)
                dynamicAddView(iv_home_news, "switchColor", R.color.app_main_color)
            }
            2 ->{
                dynamicAddView(tv_video, "textColor", R.color.app_main_color)
                dynamicAddView(iv_home_video, "switchColor", R.color.app_main_color)
            }
            3 ->{
                dynamicAddView(tv_product, "textColor", R.color.app_main_color)
                dynamicAddView(iv_home_product, "switchColor", R.color.app_main_color)
            }
            4 ->{
                dynamicAddView(tv_me, "textColor",R.color.app_main_color)
                dynamicAddView(iv_home_me, "switchColor", R.color.app_main_color)
            }
        }
    }

    private fun startAnimation(view: ImageView) {
        val scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", 0.6f, 1f, 0.75f, 1f)
        scaleXAnimator.repeatCount = 0
        //沿y轴放大
        val scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", 0.6f, 1f, 0.75f, 1f)
        scaleYAnimator.repeatCount = 0
        val set = AnimatorSet()
        //同时沿X,Y轴放大
        set.play(scaleXAnimator).with(scaleYAnimator)
        //都设置1s，也可以为每个单独设置
        set.duration = 300
        set.startDelay = 100
        set.start()
    }

    /**
     *
     * @param isShow
     */
    private fun setVoicePlayerAnim(isShow: Boolean) {
        val objectAnimator: ObjectAnimator
        if (home_bottom_tab?.visibility == View.GONE) {
            return
        }
        if (mIsShow == isShow) {
            return
        }
        if (isShow) {
            objectAnimator = ObjectAnimator.ofFloat<View>(home_bottom_tab, View.TRANSLATION_Y, ScreenUtil.dp2px(50f).toFloat(), 0f)
            mIsShow = true
        } else {
            objectAnimator = ObjectAnimator.ofFloat<View>(home_bottom_tab, View.TRANSLATION_Y, 0f, ScreenUtil.dp2px(50f).toFloat())
            mIsShow = false
        }
        objectAnimator.duration = 400
        objectAnimator.start()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun handleScroll(event: RefreshEvent?) {
        if (event == null){
            return
        }
        if ("scroll" == event.flag){
            setVoicePlayerAnim(event.isRefresh)
        } else if ("colorview" == event.flag){
            colorfull_bg_view?.changeImg(event.refresh_int)
        }
    }

    override fun onClick(view: View) {

        when(view.id){
            R.id.fragment_article ->{
                changePage(0)
                startAnimation(iv_home_article)
            }
            R.id.fragment_news ->{
                changePage(1)
                startAnimation(iv_home_news)
            }
            R.id.fragment_video ->{
                changePage(2)
                startAnimation(iv_home_video)
            }
            
            R.id.fragment_douban ->{
                changePage(3)
                startAnimation(iv_home_product)
            }

            R.id.fragment_me ->{
                changePage(4)
                startAnimation(iv_home_me)
            }
        }

    }

    override fun restartApp() {
        startActivity(Intent(this,GuideActivity::class.java))
        finish()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        var action = intent?.getIntExtra(AppStatusManager.KEY_HOME_ACTION,AppStatusManager.ACTION_BACK_TO_HOME)
        if (action == AppStatusManager.ACTION_RESTART_APP){
            restartApp()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && event?.repeatCount == 0){

            exitApp()

            // 退出App 不杀死
//            val intent = Intent(Intent.ACTION_MAIN)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            intent.addCategory(Intent.CATEGORY_HOME)
//            startActivity(intent)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 退出app
     */
    fun exitApp(){
        if(System.currentTimeMillis() - mExitTime > 2000){
            toast("再按一次退出")
            mExitTime = System.currentTimeMillis()
        }else{
           finish()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && resultCode == RESULT_OK){
            when(requestCode){
                TAKE_PHOTO , SELECT_IMAGE->{
                    mFragments[4].onActivityResult(requestCode, resultCode, data)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
