package com.donkingliang.imageselector.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

public class ImageUtil {

    // TODO 图片存放路径
    public static final String IMAGE_FILE = Environment.getExternalStorageDirectory()+"/AndroidStudy/Images";
    public static final String AVATAR_FILE = "avatar_img";
    public static final String AVATAR_CROP_FILE = "avatar_crop";

    private static final int ICON_SIZE = 450;
    public static final int CAMERA_WITH_DATA = 1000;
    public static final int PHOTO_CROP_RESOULT = 1001;

    public static String saveImage(Bitmap bitmap, String path) {

        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".png";
        FileOutputStream b = null;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();// 创建文件夹
        }

        String fileName = path + File.separator + name;

        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, b);// 把数据写入文件
            return fileName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static Bitmap zoomBitmap(Bitmap bm, int reqWidth, int reqHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) reqWidth) / width;
        float scaleHeight = ((float) reqHeight) / height;
        float scale = Math.min(scaleWidth, scaleHeight);
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        return newbm;
    }

    /**
     * 根据计算的inSampleSize，得到压缩后图片
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {

        int degree = getExifOrientation(pathName);
        try {
            // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, options);
            // 调用上面定义的方法计算inSampleSize值
            options.inSampleSize = calculateInSampleSize(options, reqWidth,
                    reqHeight);

            // 使用获取到的inSampleSize值再次解析图片
            options.inJustDecodeBounds = false;
//            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);

            if (degree != 0) {
                Bitmap newBitmap = rotateImageView(bitmap, degree);
                bitmap.recycle();
                bitmap = null;
                return newBitmap;
            }

            return bitmap;
        } catch (OutOfMemoryError error) {
            Log.e("eee", "内存泄露！");
            return null;
        }
    }

    /**
     * 旋转图片
     *
     * @param bitmap
     * @param angle
     * @return Bitmap
     */
    public static Bitmap rotateImageView(Bitmap bitmap, int angle) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * 计算inSampleSize，用于压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // 源图片的宽度
        int width = options.outWidth;
        int height = options.outHeight;
        int inSampleSize = 1;

        if (width > reqWidth && height > reqHeight) {
//         计算出实际宽度和目标宽度的比率
            int widthRatio = Math.round((float) width / (float) reqWidth);
            int heightRatio = Math.round((float) height / (float) reqHeight);
            inSampleSize = Math.max(widthRatio, heightRatio);
        }

        return inSampleSize;
    }

    /**
     * 调用系统相机拍照
     */
    public static Uri doTakePhoto(Activity activity) {
        String state = Environment.getExternalStorageState(); // 判断是否存在sd卡
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(activity, "请检查手机是否有sd卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            File file = new File(IMAGE_FILE);
            if(!file.exists()){
                file.mkdirs();
            }
            File avatarFile = new File(IMAGE_FILE, AVATAR_FILE);
            if (!avatarFile.exists()){
                avatarFile.createNewFile();
            }
            Uri uri = null;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                uri = FileProvider.getUriForFile(activity, "com.zs.project.FileProvider", avatarFile);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }else{
                uri = Uri.fromFile(avatarFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, CAMERA_WITH_DATA);
            return uri;
        } catch (Exception e) {
            Toast.makeText(activity, "打开相机失败", Toast.LENGTH_SHORT).show();
        }
        return null;

    }

    /**
     * 裁剪图片
     * @param uri
     */
    public static Uri startPhotoZoom(Activity activity,Uri uri) {

        File file = new File(IMAGE_FILE);
        if(!file.exists()){
            file.mkdirs();
        }
        Uri imageUri = Uri.fromFile(new File(IMAGE_FILE ,AVATAR_CROP_FILE));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("circleCrop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//图像输出
        intent.putExtra("outputFormat",
                Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);//回调方法data.getExtras().getParcelable("data")返回数据为空
        activity.startActivityForResult(intent, PHOTO_CROP_RESOULT);
        return imageUri;

    }

    /**
     * 获取uri
     * @param context
     * @param uri
     * @return
     */
    public static Uri getImageUri(Activity context, Uri uri, String filename){
        Bitmap photoBitmap = readBitmapFromPath(context,IMAGE_FILE + "/" + filename);
        // 判断是否有旋转度
        int degree = getExifOrientation(IMAGE_FILE + "/" + filename);
        if(degree != 0){
            photoBitmap = rotaingImageView(photoBitmap,degree);
            File file = saveBitmaptoSdCard(photoBitmap,context,filename);
            return Uri.fromFile(file);
        }else{
            return uri;
        }

    }

    /**
     * 读取本地图片
     * @param filePath
     * @return
     */
    public static Bitmap readBitmapFromPath(Activity context, String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inJustDecodeBounds = false;
        int be = calculateInSampleSize(context,outWidth,outHeight);
        options.inSampleSize = be;
        options.inPurgeable =true;
        options.inInputShareable = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        try {
            return BitmapFactory.decodeFile(filePath, options);
        }catch (OutOfMemoryError e){
            System.gc();
            try {
                options.inSampleSize = be+1;
                return BitmapFactory.decodeFile(filePath, options);

            }catch (OutOfMemoryError e2){
                return null;
            }
        }
    }

    /**
     * 计算图片缩放比例
     * @param outWidth
     * @param outHeight
     * @return
     */
    public static int calculateInSampleSize(Activity context,int outWidth,int outHeight){
        int screenWidth = context.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = context.getWindowManager().getDefaultDisplay().getHeight();
        int be;
        if(outWidth>screenWidth || outHeight>1.5*screenHeight){
            int heightRatio = Math.round(((float) outHeight) / ((float) 1.5 * screenHeight));
            int widthRatio = Math.round(((float) outWidth) / ((float) screenWidth));
            int sample = heightRatio > widthRatio ? heightRatio : widthRatio;
            if (sample < 3)
                be = sample;
            else if (sample < 6.5)
                be = 4;
            else if (sample < 8)
                be = 8;
            else
                be = sample;
        }else{
            be = 1;
        }
        if(be <= 0){
            be = 1;
        }
        return be;
    }

    /**
     * 获取jpeg的旋转信息
     * @param filepath
     * @return
     */
    public static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filepath);
        } catch (IOException ex) {
            //LogUtil.i("test", "cannot read exif");
        }
        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Log.i("ORIENTATION", orientation + "");
            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param bitmap 原图
     * @param angle2 旋转角度
     * @return
     */
    public static Bitmap rotaingImageView(Bitmap bitmap, int angle2) {
        Matrix matrix = new Matrix();
        // 旋转图片 动作
        matrix.postRotate(angle2);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        bitmap = BitmapFactory.decodeStream(bitmap2IS(bitmap), null, options);
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * Bitmap转换成InputStream方法
     *
     * @param bm
     * @return
     */
    public static InputStream bitmap2IS(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream sbs = new ByteArrayInputStream(baos.toByteArray());
        return sbs;
    }

    /**
     * 保存图片到sd卡 名字固定
     * @param bm
     * @return
     */
    public static File saveBitmaptoSdCard(Bitmap bm,Activity mContext,String filename){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File file = new File(IMAGE_FILE);
            if(!file.exists()){
                file.mkdirs();
            }
            FileOutputStream fos;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] buffer = baos.toByteArray();
            File f = new File(file,filename);
            try {
                f.createNewFile();
                fos = new FileOutputStream(f);
                fos.write(buffer,0,buffer.length);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return f;
        }else{
            Toast.makeText(mContext,"sd卡不存在",Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}