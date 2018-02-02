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
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by gyzhong on 15/3/22.
 */
public class PullZoomListView extends ListView {

    /*头部View 的容器*/
    private FrameLayout mHeaderContainer;
    /*头部View 的图片*/
    private ImageView mHeaderImg;
    /*屏幕的高度*/
    private int mScreenHeight;
    /*屏幕的宽度*/
    private int mScreenWidth;

    private int mHeaderHeight;

    /*无效的点*/
    private static final int INVALID_POINTER = -1;
    /*滑动动画执行的时间*/
    private static final int MIN_SETTLE_DURATION = 200; // ms
    /*定义了一个时间插值器，根据ViewPage控件来定义的*/
    private static final Interpolator sInterpolator = new Interpolator() {
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    /*记录上一次手指触摸的点*/
    private float mLastMotionX;
    private float mLastMotionY;

    /*当前活动的点Id,有效的点的Id*/
    protected int mActivePointerId = INVALID_POINTER;
    /*开始滑动的标志距离*/
    private int mTouchSlop;

    private float mScale;
    private float mLastScale;

    private final float mMaxScale = 2.0f;

    private boolean isNeedCancelParent;

    private OnScrollListener mScrollListener ;

    private final float REFRESH_SCALE = 1.20F;

    private OnRefreshListener mRefreshListener ;


    public PullZoomListView(Context context) {
        super(context);
        init(context);
    }

    public PullZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullZoomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
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
        super.setOnScrollListener(new InternalScrollerListener() );

    }
    /*处理事件用*/

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction() & MotionEventCompat.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:

                /*计算 x，y 的距离*/
                int index = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, index);
                if (mActivePointerId == INVALID_POINTER)
                    break;
                mLastMotionX = MotionEventCompat.getX(ev, index);
                mLastMotionY = MotionEventCompat.getY(ev, index);
                // 结束动画
                abortAnimation();
                mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
                isNeedCancelParent = true ;
                break;
            case MotionEvent.ACTION_MOVE:
                int indexMove = MotionEventCompat.getActionIndex(ev);
                mActivePointerId = MotionEventCompat.getPointerId(ev, indexMove);

                if (mActivePointerId == INVALID_POINTER) {
                    /*这里相当于松手*/
                    finishPull();
                    isNeedCancelParent = true ;
                } else {
                    if (mHeaderContainer.getBottom() >= mHeaderHeight) {
                        ViewGroup.LayoutParams params = this.mHeaderContainer
                                .getLayoutParams();
                        final float y = MotionEventCompat.getY(ev, indexMove);
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
                        dy = dy * 0.5f * (mHeaderHeight * 1.0f / params.height);
                        mLastScale = (dy + params.height) * 1.0f / mHeaderHeight;
                        mScale = clamp(mLastScale, 1.0f, mMaxScale);
                        Log.v("zgy", "=======mScale=====" + mLastScale+",f = "+f);

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
                    mLastMotionY = MotionEventCompat.getY(ev, indexMove);

                }

                break;
            case MotionEvent.ACTION_UP:

                finishPull();

                break;
            case MotionEvent.ACTION_POINTER_UP:

                int pointUpIndex = MotionEventCompat.getActionIndex(ev);
                int pointId = MotionEventCompat.getPointerId(ev, pointUpIndex);
                if (pointId == mActivePointerId) {
                    /*松手执行结束拖拽操作*/
                    finishPull();
                }

                break;

        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mScrollListener = l ;
    }

    private void abortAnimation() {

    }

    private void finishPull() {
        mActivePointerId = INVALID_POINTER;
        if (mHeaderContainer.getBottom() > mHeaderHeight){
            Log.v("zgy", "===super====onTouchEvent========");
            if (mScale > REFRESH_SCALE){
                if (mRefreshListener != null){
                    mRefreshListener.onRefresh();
                }
            }
            pullBackAnimation();
        }
    }

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
     * 通过事件和点的 id 来获取点的索引
     *
     * @param ev
     * @param id
     * @return
     */
    private int getPointerIndex(MotionEvent ev, int id) {
        int activePointerIndex = MotionEventCompat.findPointerIndex(ev, id);
        if (activePointerIndex == -1)
            mActivePointerId = INVALID_POINTER;
        return activePointerIndex;
    }


    public void setOnRefreshListener(OnRefreshListener l){
        mRefreshListener = l ;
    }

    public ImageView getHeaderImageView() {
        return this.mHeaderImg;
    }


    private float clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }

    private class InternalScrollerListener implements OnScrollListener{
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if (mScrollListener != null){
                mScrollListener.onScrollStateChanged(view,scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            float diff = mHeaderHeight - mHeaderContainer.getBottom();
            if ((diff > 0.0F) && (diff < mHeaderHeight)) {
                int i = (int) (0.3D * diff);
                mHeaderImg.scrollTo(0, -i);
            } else if (mHeaderImg.getScrollY() != 0) {
                mHeaderImg.scrollTo(0, 0);
            }
            if (mScrollListener != null){
                mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
        }
    }

    public interface OnRefreshListener {
        void onRefresh() ;
    }

    public void computeRefresh(){
        if (mActivePointerId != INVALID_POINTER){

        }
    }
}

