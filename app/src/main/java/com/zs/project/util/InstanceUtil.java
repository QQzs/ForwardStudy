package com.zs.project.util;

/**
 * Created by zs
 * Date：2018年 10月 08日
 * Time：15:41
 * —————————————————————————————————————
 * About: 单例模式
 * —————————————————————————————————————
 */
public class InstanceUtil {

    public static InstanceUtil mUtil = null;

    private InstanceUtil(){

    }

    /**
     * 两次判空实现单例
     * @return
     */
    public static InstanceUtil getInstance1(){
        if (mUtil == null){
            synchronized (InstanceUtil.class){
                if (mUtil == null){
                    mUtil = new InstanceUtil();
                }
            }
        }
        return mUtil;
    }

    /**
     * 静态内部类实现单例
     * @return
     */
    public static InstanceUtil getInstance2(){
        return TestHolder.instance;
    }

    public static class TestHolder{
        private static final InstanceUtil instance = new InstanceUtil();
    }




}
