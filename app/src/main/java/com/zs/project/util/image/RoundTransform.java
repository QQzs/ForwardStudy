package com.zs.project.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by zs
 * Date：2018年 01月 19日
 * Time：17:15
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class RoundTransform extends BitmapTransformation {
    private int cornerRadius;
    private RectF rectF = new RectF();

    /**
     * @param round dp
     */
    public RoundTransform(Context context, int round) {
        super(context);
        float density = context.getResources().getDisplayMetrics().density;
        this.cornerRadius = (int) (round/density+0.5f);
    }

    private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int width = source.getWidth();
        int height = source.getHeight();
        rectF.set(0, 0, width, height);
        Bitmap squared = Bitmap.createBitmap(source, 0, 0, width, height);

        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG | Paint
                .ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader
                .TileMode.CLAMP));
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        return result;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }
}
