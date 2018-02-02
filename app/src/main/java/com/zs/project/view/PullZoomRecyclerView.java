package com.zs.project.view;

/**
 * Created by zs
 * Date：2018年 02月 01日
 * Time：16:53
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * Created by gyzhong on 15/3/22.
 */
public class PullZoomRecyclerView extends XRecyclerView{

    /**
     * 头部View 的容器
     */
    private FrameLayout mHeaderContainer;
    /**
     * 头部View 的图片
     */
    private ImageView mHeaderImg;
    /*屏幕的高度*/
    private int mScreenHeight;
    /*屏幕的宽度*/
    private int mScreenWidth;
    private int mHeaderHeight;

    /**
     * 无效的点
     */
    private static final int INVALID_POINTER = -1;
    /**
     * 滑动动画执行的时间
     */
    private static final int MIN_SETTLE_DURATION = 200; // ms
    /**
     * 定义了一个时间插值器，根据ViewPage控件来定义的
     */
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    /**
     * 记录上一次手指触摸的点
     */
    private float mLastMotionY;
    /**
     * 当前活动的点Id,有效的点的Id
     */
    protected int mActivePointerId = INVALID_POINTER;

    private boolean isNeedCancelParent;

    private float mScale;
    private float mLastScale;
    /**
     * 放大的最大程度（默认两倍）
     */
    private float mMaxScale = 2.0f;
    /**
     * 滑动放大系数，系数越大，滑动时放大灵敏度越大
     */
    private float mScaleRatio = 0.6f;

    /**
     * 刷新的临界值，超过才刷新
     */
    private final float REFRESH_SCALE = 1.20f;

    public PullZoomRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public PullZoomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullZoomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mHeaderContainer = new FrameLayout(context);
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenWidth = metrics.widthPixels;
        mHeaderHeight = (int) ((9 * 1.0f / 16) * mScreenWidth);
        LayoutParams absLayoutParams = new LayoutParams(mScreenWidth, mHeaderHeight);
        mHeaderContainer.setLayoutParams(absLayoutParams);
        mHeaderImg = new ImageView(context);
        FrameLayout.LayoutParams imgLayoutParams = new FrameLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHeaderImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mHeaderImg.setLayoutParams(imgLayoutParams);
        mHeaderContainer.addView(mHeaderImg);
        addHeaderView(mHeaderContainer);
    }

    public ImageView getHeaderImageView() {
        return this.mHeaderImg;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // ACTION_DOWN 事件被RecyclerView拦截，转到 ACTION_MOVE 记录按下位置
                break;
            case MotionEvent.ACTION_MOVE:
                if (mLastMotionY == 0){
                    mLastMotionY = ev.getY();
                }
                mActivePointerId = ev.getPointerId(ev.getActionIndex());
                if (mActivePointerId == INVALID_POINTER) {
                    /*这里相当于松手*/
                    finishPull();
                    isNeedCancelParent = true ;
                } else {
                    if (mHeaderContainer.getBottom() >= mHeaderHeight) {
                        ViewGroup.LayoutParams params = this.mHeaderContainer
                                .getLayoutParams();
                        final float y = ev.getY();
                        float dy = y - mLastMotionY;
                        float f = ((y - this.mLastMotionY + this.mHeaderContainer
                                .getBottom()) / this.mHeaderHeight - this.mLastScale)
                                / 2.0F + this.mLastScale;
                        if ((this.mLastScale <= 1.0D) && (f <= this.mLastScale)) {
                            params.height = this.mHeaderHeight;
                            this.mHeaderContainer
                                    .setLayoutParams(params);
                            return super.onTouchEvent(ev);
                        }
                        /*这里设置紧凑度*/
                        dy = dy * mScaleRatio * (mHeaderHeight * 1.0f / params.height);
                        mLastScale = (dy + params.height) * 1.0f / mHeaderHeight;
                        mScale = clamp(mLastScale, 1.0f, mMaxScale);
                        params.height = (int) (mHeaderHeight * mScale);
                        mHeaderContainer.setLayoutParams(params);
                        mLastMotionY = y;
                        if(isNeedCancelParent ){
                            isNeedCancelParent = false;
                            MotionEvent motionEvent = MotionEvent.obtain(ev);
                            motionEvent.setAction(MotionEvent.ACTION_CANCEL);
                            super.onTouchEvent(motionEvent);
                        }
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                finishPull();
                mLastMotionY = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mLastMotionY = 0;
                int pointUpIndex = ev.getActionIndex();
                int pointId = ev.getPointerId(pointUpIndex);
                if (pointId == mActivePointerId) {
                    /*松手执行结束拖拽操作*/
                    finishPull();
                }
                break;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 恢复原态
     */
    private void finishPull() {
        mActivePointerId = INVALID_POINTER;
        if (mHeaderContainer.getBottom() > mHeaderHeight){
            if (mScale > REFRESH_SCALE){
                // 调用父类XRecyclerView 的刷新方法，也可以自己写一个刷新的接口
                refresh();
                Log.v("My_Log", "===super====onTouchEvent========");
            }
            pullBackAnimation();
        }
    }

    /**
     * 复原动画
     */
    private void pullBackAnimation(){
        ValueAnimator pullBack = ValueAnimator.ofFloat(mScale , 1.0f);
        pullBack.setInterpolator(sInterpolator);
        pullBack.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                LayoutParams params = (LayoutParams) mHeaderContainer.getLayoutParams();
                params.height = (int) (mHeaderHeight * value);
                mHeaderContainer.setLayoutParams(params);
            }
        });
        pullBack.setDuration((long) (MIN_SETTLE_DURATION*mScale));
        pullBack.start();

    }

    /**
     * 检查数据
     * @param value
     * @param min
     * @param max
     * @return
     */
    private float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

}

