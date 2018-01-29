package com.zs.project.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.DrawableRes;
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
import com.zs.project.util.image.CircleTransform;
import com.zs.project.util.image.GlideApp;
import com.zs.project.util.image.RoundTransform;

import java.io.File;
import java.math.BigDecimal;


public class GlideImageLoaderTest {
    private static Context mContext;

    public static RequestOptions options = new RequestOptions().centerCrop();

    public static void displayImage(String url, ImageView img) {
        mContext = img.getContext();
        if (NetworkUtil.isAvailable(mContext)) {
            loadNormal(url, img);
        } else {
            loadCache(url, img);
        }

    }

    private static void loadNormal(String url, ImageView img) {  //placeholder占位符。错误占位符：.error()
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .dontAnimate()//去掉动画
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(img);
    }

    /**
     *
     * @param url
     * @param img
     */
    private static void loadCircleImage(String url, ImageView img) {  //placeholder占位符。错误占位符：.error()
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .dontAnimate()//去掉动画
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new CircleTransform(mContext))
                .into(img);
    }

    /**
     *
     * @param url
     * @param img
     * @param round
     */
    private static void loadRoundImage(String url, ImageView img , int round) {  //placeholder占位符。错误占位符：.error()
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .dontAnimate()//去掉动画
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transform(new RoundTransform(mContext,round))
                .into(img);
    }

    private static void loadCache(String url, ImageView img) {
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(img);
    }

    //Glide 动态设置图片宽高
    public static void loadIntoUseFitWidth(String imageUrl, ImageView imageView, float f) {
        GlideApp.with(mContext)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.mipmap.default_img)
                    .error(R.mipmap.default_img)
                    .fallback(R.mipmap.default_img)
                    .into(imageView);
    }

    //等比例缩放图片至屏幕宽度
    public static void loadImage(String url, final ImageView imageView) {
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
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
            return GlideApp.with(mContext)
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

    //加载网络图片并设置大小
    public static void displayImage(String url, ImageView imageView, int width, int height) {
        GlideApp
                .with(mContext)
                .load(url)
                .override(width, height)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .into(imageView);
    }

    //加载本地图片
    public static void Image(@DrawableRes int url, ImageView imageView) {
        GlideApp.with(mContext)
                .load(url)
                .placeholder(R.mipmap.default_img)
                .error(R.mipmap.default_img)
                .fallback(R.mipmap.default_img)
                .into(imageView);
    }


    // 清除图片磁盘缓存，调用Glide自带方法
    public static boolean clearCacheDiskSelf() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GlideApp.get(mContext).clearDiskCache();
                    }
                }).start();
            } else {
                GlideApp.get(mContext).clearDiskCache();
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
                GlideApp.get(mContext).clearMemory();
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
