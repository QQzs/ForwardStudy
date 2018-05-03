package com.zs.project.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.zs.project.R;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：13:56
 * —————————————————————————————————————
 * About:可以切换颜色的 ImageView
 * —————————————————————————————————————
 */

@SuppressLint("AppCompatCustomView")
public class SwitchColorView extends ImageView {

    private Paint mPaint;
    /**
     * 图像bitmap
     */
    private Bitmap mBitmap;
    /**
     * 图像副本bitmap
     */
    private Bitmap mCopyBitmap;

    /**
     * 图片
     */
    private int mSwitchImage;
    /**
     * 副本颜色
     */
    private int mSwitchColor;

    public SwitchColorView(Context context) {
        this(context, null);
    }

    public SwitchColorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchColorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchColorView);
        mSwitchImage = array.getResourceId(R.styleable.SwitchColorView_switchImage, R.mipmap.ic_launcher);
        mSwitchColor = array.getColor(R.styleable.SwitchColorView_switchColor, -1);
        if (mSwitchImage == -1) {
            return;
        }
        //源图像
        mBitmap = BitmapFactory.decodeResource(getResources(), mSwitchImage);
        if (mSwitchColor != -1) {
            copyBitmap();
            this.setImageBitmap(mCopyBitmap);
        } else {
            this.setImageBitmap(mBitmap);
        }
    }

    /**
     * 生成图像副本
     */
    private void copyBitmap() {

        //创建一个目标图像，由于不能对getResources方法提取到的Bitmap对象进行修改操作，必须复制出一个
        mCopyBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        mCopyBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888,true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Canvas基于目标图像进行操作
        Canvas canvas = new Canvas(mCopyBitmap);
        //在canvas上的mOutPutBitmap绘制
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.drawColor(mSwitchColor, PorterDuff.Mode.SRC_IN);
    }

    /**
     * 设置原图像
     * @param image
     */
    public void swichImage(int image){
        mSwitchImage = image;
        //原图像
        mBitmap = BitmapFactory.decodeResource(getResources(), mSwitchImage);
    }


    /**
     * 切换颜色
     * @param color
     */
    public void switchColor(int color){
        mSwitchColor = color;
        copyBitmap();
        this.setImageBitmap(mCopyBitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;

        if (mBitmap == null) {
            width = widthSize;
            height = heightSize;
        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            } else {
                width = mBitmap.getWidth();
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            } else {
                height = mBitmap.getHeight();
            }
        }
        setMeasuredDimension(width, height);
    }

}
