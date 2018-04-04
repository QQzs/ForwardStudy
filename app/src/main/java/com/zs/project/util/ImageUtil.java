package com.zs.project.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

/**
 * Created by zs
 * Date：2018年 04月 04日
 * Time：17:33
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ImageUtil {

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩 放大
     *                 社区的标准是720P   不够的放大  超过的 缩小
     * @return Bitmap
     */
    public static byte[] compressImage(Bitmap bitmap, int width, int height, boolean isAdjust) {
        float sx = 1;
        float sy = 1;
        // 如果想要的宽度和高度都比源图片小，就放大
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            sx = width / bitmap.getWidth();
            sy = height / bitmap.getHeight();
        } else {
            // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int roundingMode);
            // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
            sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal.ROUND_DOWN).floatValue();
            sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4, BigDecimal.ROUND_DOWN).floatValue();

        }
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Log.d("upload_pic", " sx = " + sx + " sy = " + sy);
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了

        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            } else {
                // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
                int options = 100;
                // 压缩图片 512k以下
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                while (baos.toByteArray().length / 1024 > 1024) {
                    // 循环判断如果压缩后图片是否大于512kb,大于继续压缩
                    baos.reset();// 重置baos即清空baos
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                    // 这里压缩options%，把压缩后的数据存放到baos中
                    options -= 10;// 每次都减少10
                    if (options <= 0) {
                        break;
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (Exception e) {

            }
        }
        if (baos != null) {
            return baos.toByteArray();
        } else {
            return new byte[]{};
        }
    }

    /**
     * 压缩大图方法
     * @param bigBitmap
     * @param size  压缩后大小
     * @return
     */
    public static Bitmap compressBitmap(Bitmap bigBitmap, int size){

        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            bigBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            while (baos.toByteArray().length / 1024 > size) {
                // 循环判断如果压缩后图片是否大于指定,大于继续压缩
                baos.reset();// 重置baos即清空baos
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                // 这里压缩options%，把压缩后的数据存放到baos中
                options -= 20;// 每次都减少20
                if (options <= 0) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("My_Log","error"+e.toString());
        }
        return BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);

    }

}
