package com.zs.project.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zs.project.app.Constant;
import com.zs.project.util.BezierUtil;
import com.zs.project.util.LogUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zs
 * Date：2017年 05月 11日
 * Time：14:17
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */
public class ColorfulView extends FrameLayout {

    private Paint mPaint;
    /**
     * 原始图
     */
    private Bitmap mBitmap;
    /**
     * 复制图
     */
    private Bitmap mCopyBitmap;
    /**
     * icon标志
     */
    private int mFlag;

    private int mScreenWidth;
    private int mScreenHeight;

    /**
     * 收集在已经落下的view ，重新添加到布局中进行复用
     */
    private ArrayList<ImageView> mRemoveViews = new ArrayList<>();
    /**
     * 动画
     */
    private ArrayList<ValueAnimator> mValueAnimators = new ArrayList<>();
    private ArrayList<AnimatorSet> mAnimatorSets = new ArrayList<>();

    /**
     * 起始位置  结束位置 中间点1 中间点2
     */
    private Point mStartPoint;
    private Point mEndPoint;
    private Point mConOnePoint;
    private Point mConTwoPoint;

    /**
     * 产生随机数
     */
    private Random mRandom;
    /**
     * 所有颜色
     */
    private int[] mColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW};

    /**
     * handler
     **/
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == mWhat){
                addMyView();
                mHandler.sendEmptyMessageDelayed(mWhat,mDuration);
            }
            super.handleMessage(msg);
        }
    };

//    private LeakHandler mHandler;
//    public static class LeakHandler extends Handler{
//
//        private WeakReference<View> weakReference;
//
//        private LeakHandler(View view) {
//            this.weakReference = new WeakReference<>(view);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            ColorfulView view = (ColorfulView) weakReference.get();
//            if (view != null && view.getContext() != null){
//                if (msg.what == mWhat){
//                    view.addMyView();
//                    view.mHandler.sendEmptyMessageDelayed(mWhat,mDuration);
//                }
//            }
//        }
//    }

    /**
     * 添加view间隔时间
     */
    private static int mDuration = 600;
    private static int mWhat = 1000;

    public ColorfulView(Context context) {
        this(context,null);
    }

    public ColorfulView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ColorfulView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mHandler = new LeakHandler(this);
        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitmap = BitmapFactory.decodeResource(getResources(), Constant.iconId[0]);
        mRandom = new Random();

        switchAnim(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;

    }

    /**
     *
     * @param flag
     */
    public void changeImg(int flag){
        if (flag > Constant.iconId.length -1){
            mFlag = 0;
        }else{
            mFlag = flag;
        }
        mBitmap = BitmapFactory.decodeResource(getResources(), Constant.iconId[mFlag]);
    }

    /**
     * 开启关闭动画
     * @param flag
     */
    public void switchAnim(boolean flag){

        if (flag){
            if (!mHandler.hasMessages(mWhat)){
                mHandler.sendEmptyMessageDelayed(mWhat,mDuration);
            }
        }else{
            if (mHandler.hasMessages(mWhat)){
                mHandler.removeMessages(mWhat);
            }
        }

    }

    /**
     * 初始化动画
     * @param imageView
     */
    private void initAnimation(final ImageView imageView){

        Point startPoint = this.mStartPoint;
        Point endPoint = this.mEndPoint;
        Point conOnePoint = this.mConOnePoint;
        Point conTwoPoint = this.mConTwoPoint;

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(conOnePoint,conTwoPoint),startPoint,endPoint);
        mValueAnimators.add(valueAnimator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                imageView.setX(point.x);
                imageView.setY(point.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRemoveViews.add(imageView);
                removeView(imageView);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView,"alpha",1.0f,0);
        mValueAnimators.add(valueAnimator);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(6000);
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.start();
        mAnimatorSets.add(animatorSet);

    }

    class MyTypeEvaluator implements TypeEvaluator<Point> {

        private Point onePoint;
        private Point twoPoint;

        public MyTypeEvaluator(Point onePoint, Point twoPoint) {
            this.onePoint = onePoint;
            this.twoPoint = twoPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {
            //利用三阶贝塞尔曲线公式算出中间点坐标
            return BezierUtil.CalculateBezierPointForCubicPoint(t,startValue,onePoint,twoPoint,endValue);
        }
    }

    /**
     * 添加view
     */
    private void addMyView(){

        if (mBitmap == null){
            return;
        }
        mStartPoint = new Point((int)(mRandom.nextFloat() * (Math.random()>0.5?1:-1) * mScreenWidth),-200);
        mEndPoint = new Point((int)(mRandom.nextFloat() *(Math.random()>0.5?1:-1) * mScreenWidth),mScreenHeight + 50);
        mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));
        mConTwoPoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * mRandom.nextFloat() ));

        final ImageView imageView;
        if (mRemoveViews.size() > 0){
            imageView = mRemoveViews.get(0);
            mRemoveViews.remove(0);
        }else{
            imageView = new ImageView(getContext());
        }
        if (mFlag == 3 || mFlag == 4){
            imageView.setImageBitmap(mBitmap);
        }else{
            mCopyBitmap = Bitmap.createBitmap(mBitmap.getWidth(),mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(mCopyBitmap);
            canvas.drawBitmap(mBitmap,0,0,mPaint);
            canvas.drawColor(mColors[mRandom.nextInt(mColors.length)], PorterDuff.Mode.SRC_IN);
            imageView.setImageBitmap(mCopyBitmap);
        }
        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(imageView,params);
        initAnimation(imageView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addMyView();
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        if (mBitmap != null){
            mBitmap.recycle();
            mBitmap = null;
        }
        if (mCopyBitmap != null){
            mCopyBitmap.recycle();
            mCopyBitmap = null;
        }
        for (int i = 0 ; i < mValueAnimators.size() ; i++){
            if (mValueAnimators.get(i) != null){
                mValueAnimators.get(i).removeAllUpdateListeners();
                mValueAnimators.get(i).cancel();
            }
        }
        mValueAnimators.clear();
        mValueAnimators = null;

        for (int i = 0 ; i < mAnimatorSets.size() ; i++){
            if (mAnimatorSets.get(i) != null){
                mAnimatorSets.get(i).removeAllListeners();
                mAnimatorSets.get(i).cancel();
            }
        }
        mAnimatorSets.clear();
        mAnimatorSets = null;

        removeAllViews();
        mRemoveViews.clear();
        LogUtil.Companion.logShowError("colorfull ========");
        super.onDetachedFromWindow();
    }

}
