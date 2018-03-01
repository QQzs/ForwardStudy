package com.zs.project.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zs.project.util.ScreenUtil;

/**
 * Created by zs
 * Date：2018年 02月 01日
 * Time：16:53
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class PullZoomRecyclerView extends XRecyclerView{

    private LayoutInflater mInflater;
    /**
     * 头部View 的容器
     */
    private View mHeaderContainer;
    /**
     * 头部View 的图片
     */
    private ImageView mHeaderImg;
    /*屏幕的宽度*/
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
    private final float REFRESH_SCALE = 1.30f;

    public PullZoomRecyclerView(Context context) {
        this(context,null);
    }

    public PullZoomRecyclerView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PullZoomRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        // 方法一 默认添加
//        mHeaderContainer = new FrameLayout(getContext());
//        mScreenWidth = ScreenUtil.getScreenWidth();
//        mHeaderHeight = (int) ((9 * 1.0f / 16) * mScreenWidth);
//        LayoutParams absLayoutParams = new LayoutParams(mScreenWidth, mHeaderHeight);
//        mHeaderContainer.setLayoutParams(absLayoutParams);
//        mHeaderImg = new ImageView(getContext());
//        FrameLayout.LayoutParams imgLayoutParams = new FrameLayout.LayoutParams
//                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        mHeaderImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        mHeaderImg.setLayoutParams(imgLayoutParams);
//        ((FrameLayout)mHeaderContainer).addView(mHeaderImg);
//        addHeaderView(mHeaderContainer);

        // 方法二，属性添加
//        mInflater = LayoutInflater.from(getContext());
//        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.PullZoomRecyclerView);
//        int headerResId = array.getResourceId(R.styleable.PullZoomRecyclerView_zoom_headerView,-1);
//        float headerHeight = array.getDimension(R.styleable.PullZoomRecyclerView_zoom_header_height, 600f);
//        mHeaderContainer = mInflater.inflate(headerResId,null);
//        mHeaderHeight = (int)headerHeight;
//        LayoutParams absLayoutParams = new LayoutParams(ScreenUtil.getScreenWidth(),mHeaderHeight);
//        mHeaderContainer.setLayoutParams(absLayoutParams);
//        addHeaderView(mHeaderContainer);
//        array.recycle();

    }

    /**
     * 方法三:
     * 头布局中只有伸缩view
     * 高度必须有 布局内的高度无效
     * @param view
     * @param height
     */
    public void addZoomHeaderView(View view, int height){
        mHeaderContainer = view;
        mHeaderHeight = height;
        // mHeaderContainer 没有父布局  Recyclerview.LayoutParams
        LayoutParams params = new LayoutParams(ScreenUtil.getScreenWidth(),mHeaderHeight);
        mHeaderContainer.setLayoutParams(params);
        addHeaderView(mHeaderContainer);

    }

    /**
     * 方法三:
     * 头布局中有伸缩view 和 其他view
     * @param header  头布局view
     * @param zoomView 可伸缩的view
     * @param height 伸缩默认高度
     */
    public void addZoomContainerView(View header,View zoomView, int height){
        mHeaderContainer = zoomView;
        mHeaderHeight = height;
        // mHeaderContainer 有父布局
        ViewGroup.LayoutParams params = mHeaderContainer.getLayoutParams();
        params.height = height;
        mHeaderContainer.setLayoutParams(params);
        addHeaderView(header);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderContainer != null){
            switch (ev.getAction()) {
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
                ViewGroup.LayoutParams params = mHeaderContainer.getLayoutParams();
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

