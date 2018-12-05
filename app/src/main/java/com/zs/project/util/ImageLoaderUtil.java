package com.zs.project.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zs.project.R;
import com.zs.project.app.MyApp;
import com.zs.project.util.image.BlurTransform;
import com.zs.project.util.image.BorderCircleTransform;
import com.zs.project.util.image.CircleTransform;
import com.zs.project.util.image.GlideApp;
import com.zs.project.util.image.RoundTransform;

import java.io.File;
import java.math.BigDecimal;


public class ImageLoaderUtil {

    public static RequestOptions mOptions = new RequestOptions()
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .fallback(R.mipmap.default_img)
            .dontAnimate()//去掉动画
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    public static RequestOptions mOptionsNoCache = new RequestOptions()
            .placeholder(R.mipmap.default_img)
            .error(R.mipmap.default_img)
            .fallback(R.mipmap.default_img)
            .dontAnimate()//去掉动画
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public static RequestOptions mOptionsNoPlace = new RequestOptions()
            .placeholder(R.mipmap.img_empty)
            .error(R.mipmap.img_empty)
            .fallback(R.mipmap.img_empty)
            .dontAnimate()//去掉动画
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

    public static void displayImage(String url, ImageView img) {
        if (NetworkUtil.isAvailable(MyApp.getAppContext())) {
            loadNormal(url, img);
        } else {
            loadCache(url, img);
        }

    }

    private static void loadNormal(String url, ImageView img) {  //placeholder占位符。错误占位符：.error()
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .into(img);
    }

    private static void loadCache(String url, ImageView img) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .into(img);
    }

    /**
     * 圆形图片
     * @param url
     * @param img
     */
    private static void loadCircleImage(String url, ImageView img) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .transform(new CircleTransform(MyApp.getAppContext()))
                .into(img);
    }

    /**
     * @param resourceId
     * @param img
     */
    public static void displayLocalImage(int resourceId, ImageView img) {
        GlideApp.with(img.getContext())
                .load(resourceId)
                .apply(mOptions)
                .into(img);
    }

    /**
     * 等比例缩放图片至屏幕宽度
     * @param resourceId
     * @param imageView
     */
    public static void loadLocalImageMatch(int resourceId, final ImageView imageView) {
        GlideApp.with(MyApp.getAppContext())
                .load(resourceId)
                .apply(mOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.width = ScreenUtil.getScreenWidth();
                        para.height = ScreenUtil.dp2px(200f);
                        imageView.setImageDrawable(resource);

                    }
                });
    }


    /**
     * 圆形图片 资源文件
     * @param resourceId
     * @param img
     */
    public static void loadCircleImage(int resourceId, ImageView img) {
        GlideApp.with(img.getContext())
                .load(resourceId)
                .apply(mOptions)
                .transform(new CircleTransform(img.getContext()))
                .into(img);
    }

    /**
     * 圆形图片 资源文件
     * @param resourceId
     * @param img
     */
    public static void loadAvatarImage(int resourceId, ImageView img) {
        GlideApp.with(MyApp.getAppContext())
                .load(resourceId)
                .apply(mOptions)
                .transform(new BorderCircleTransform(MyApp.getAppContext(),4, ContextCompat.getColor(MyApp.getAppContext(),R.color.white)))
                .into(img);
    }

    /**
     * 圆形图片 资源文件
     * @param imagePath
     * @param img
     */
    public static void loadAvatarImage(String imagePath, ImageView img) {
        GlideApp.with(MyApp.getAppContext())
                .load(imagePath)
                .apply(mOptionsNoCache)
                .transform(new BorderCircleTransform(MyApp.getAppContext(),4, ContextCompat.getColor(MyApp.getAppContext(),R.color.white)))
                .into(img);
    }

    /**
     * 圆角图片
     * @param url
     * @param img
     * @param round
     */
    private static void loadRoundImage(String url, ImageView img , int round) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .transform(new RoundTransform(MyApp.getAppContext(),round))
                .into(img);
    }

    public static void loadImage(String url, ImageView imageView) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .fitCenter()
                .apply(mOptionsNoPlace)
                .into(imageView);
    }

    /**
     * 等比例缩放图片至屏幕宽度
     * @param url
     * @param imageView
     */
    public static void loadImageHW(String url, final ImageView imageView) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        int imageWidth = resource.getIntrinsicWidth();
                        int imageHeight = resource.getIntrinsicHeight();
                        int height = ScreenUtil.getScreenWidth() * imageHeight / imageWidth;
                        ViewGroup.LayoutParams para = imageView.getLayoutParams();
                        para.height = height;
                        para.width = ScreenUtil.getScreenWidth();
                        imageView.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 需要在子线程执行
     *
     * @param url
     * @return
     */
    public static Bitmap load(String url) {
        try {
            return GlideApp.with(MyApp.getAppContext())
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加载网络图片并设置大小
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void displayImage(String url, ImageView imageView, int width, int height) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .override(width, height)
                .apply(mOptions)
                .into(imageView);
    }

    /**
     * 高斯模糊图片
     * @param url
     * @param imageView
     */
    public static void displayBlurImage(@DrawableRes int url, ImageView imageView) {
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .transform(new BlurTransform(MyApp.getAppContext()))
                .into(imageView);
    }

    /**
     * 优先显示缩略图再加载原图
     * @param thumb 缩略图
     * @param url   原图
     * @param img
     */
    public static void displaythumbImage(String thumb,String url, ImageView img) {  //placeholder占位符。错误占位符：.error()
        GlideApp.with(MyApp.getAppContext())
                .load(url)
                .apply(mOptions)
                .thumbnail(GlideApp.with(MyApp.getAppContext()).load(thumb))
                .into(img);
    }

    /**
     * 清除图片磁盘缓存，调用Glide自带方法
     * @return
     */
    public static boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.get(MyApp.getAppContext()).clearDiskCache();
                    }
                }).start();
            } else {
                GlideApp.get(MyApp.getAppContext()).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 清除Glide内存缓存
    public static boolean clearCacheMemory() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                GlideApp.get(MyApp.getAppContext()).clearMemory();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache(Context context) {
        clearCacheDiskSelf();
        clearCacheMemory();
        String ImageExternalCatchDir=context.getExternalCacheDir()+ ExternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
       // deleteFolderFile(ImageExternalCatchDir, true);
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize(Context context) {
        try {
            return getFormatSize(getFolderSize(new File(context.getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath filePath
     * @param deleteThisPath deleteThisPath
     */
    private void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
        private static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte == 0){
            return "0K";
        }

        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
