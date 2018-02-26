package com.zs.project.view.loadingview.circleloading;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.zs.project.R;

public class LoadingView extends RelativeLayout {

    private CircleImageView mCircleView;
    private MaterialProgressDrawable mProgress;
    private static final int CIRCLE_BG_LIGHT = 0xFFFAFAFA;
    private static final int DEFAULT_CIRCLE_TARGET = 64;
    private static final int CIRCLE_DIAMETER_Big = 45;
    private int mCircleWidth;
    private int mCircleHeight;
    private int mCurrentTargetOffsetTop;
    protected int mOriginalOffsetTop;


    private float mTotalDragDistance = -1;


    private final float REFRESH_SCALE = 1.20F;


    private int mMediumAnimationDuration;
    private Animation mScaleAnimation;

    private Animation mScaleDownAnimation;



    protected int mFrom;

    private static final float DECELERATE_INTERPOLATION_FACTOR = 2f;

    private DecelerateInterpolator mDecelerateInterpolator;

    private boolean mRefresh = false;


    public LoadingView(Context context) {
        super(context);
        initView(getContext());
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(getContext());
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(getContext());
    }

/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(getContext());
    }*/

    private void initView(Context context) {
        createProgressView();
        final DisplayMetrics metrics = getResources().getDisplayMetrics();
        mCircleHeight = mCircleWidth = (int) (CIRCLE_DIAMETER_Big * metrics.density);
        mTotalDragDistance = DEFAULT_CIRCLE_TARGET * metrics.density;
        mMediumAnimationDuration = getResources().getInteger(
                android.R.integer.config_mediumAnimTime);
        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);
        setVisibility(VISIBLE);
    }

    /*��������View*/
    private void createProgressView() {
        /*����Բ�ε�ImageView*/
        mCircleView = new CircleImageView(getContext(), CIRCLE_BG_LIGHT, CIRCLE_DIAMETER_Big/2);
        /*�������ض���Drawable*/
        mProgress = new MaterialProgressDrawable(getContext(), this);
        /*������ɫ*/
        mProgress.setBackgroundColor(CIRCLE_BG_LIGHT);
        mCircleView.setImageDrawable(mProgress);
        mCircleView.setVisibility(View.VISIBLE);
        mProgress.setAlpha(255);
        /*���ü���ʱ����ɫֵ*/
        mProgress.setColorSchemeColors(getResources().getColor(R.color.main_color_red));
        addView(mCircleView);
    }

    /*����Ƕ�̬�ı䶯����Ч��*/
    public void setScale(float scale) {
        mProgress.showArrow(true);
        float targetScale = scale;
        mProgress.setArrowScale(Math.min(1.0f, targetScale));
        mProgress.setProgressRotation((targetScale));
        mProgress.setStartEndTrim(0, Math.min(.8f, 0.8f * targetScale));
    }

    /*������ʱû�õ�*/
    public void onRefresh(float scale) {

        mRefresh = true;
        startScaleUpAnimation(null);
        animateOffsetToCorrectPosition(mCurrentTargetOffsetTop, mRefreshListener);
    }

    /*������ʱû�õ�*/
    public void stopRefresh() {
        mRefresh = false;
        startScaleDownAnimation(mRefreshListener);
    }


    /*��ʼִ�ж���Ч��*/
    public void startAnimation() {

        mProgress.start();
    }

    /*����֪�Ķ���Ч��*/
    public void stopAnimation() {

        mProgress.stop();
    }

    private Animation.AnimationListener mRefreshListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mRefresh) {

                mProgress.start();

            } else {
                mProgress.stop();
                mCircleView.setVisibility(View.GONE);
                ViewCompat.setScaleX(mCircleView, 0);
                ViewCompat.setScaleY(mCircleView, 0);
            }
            mCurrentTargetOffsetTop = mCircleView.getTop();
        }
    };

    private void startScaleUpAnimation(Animation.AnimationListener listener) {
        mCircleView.setVisibility(View.VISIBLE);
        mScaleAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(interpolatedTime);
            }
        };
        if (listener != null) {
            mScaleAnimation.setAnimationListener(listener);
        }
        mScaleAnimation.setDuration(mMediumAnimationDuration);
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mScaleAnimation);
    }

    private void startScaleDownAnimation(Animation.AnimationListener listener) {
        mScaleDownAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setAnimationProgress(1 - interpolatedTime);
            }
        };
        mScaleDownAnimation.setDuration(150);
        mCircleView.setAnimationListener(listener);
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mScaleDownAnimation);
    }

    private void animateOffsetToCorrectPosition(int from, Animation.AnimationListener listener) {
        mFrom = from;
        mAnimateToCorrectPosition.reset();
        mAnimateToCorrectPosition.setDuration(200);
        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
        if (listener != null) {
            mAnimateToCorrectPosition.setAnimationListener(listener);
        }
        mCircleView.clearAnimation();
        mCircleView.startAnimation(mAnimateToCorrectPosition);
    }

    /*��view��Ϊ�ɼ���ʱ�򣬶�����ʼִ�У���֮����ֹͣ*/
    @Override
    public void setVisibility(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {

            mProgress.stop();
        } else {
            mProgress.start();
            mProgress.showArrow(true);
        }
        super.setVisibility(visibility);
    }

    private final Animation mAnimateToCorrectPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, Transformation t) {
            int targetTop = 0;
            int endTarget = 0;
            endTarget = (int) (mOriginalOffsetTop + mTotalDragDistance);
            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
            int offset = targetTop - mCircleView.getTop();
            setTargetOffsetTopAndBottom(offset);
        }
    };

    /**
     * @param progress
     */
    private void setAnimationProgress(float progress) {
        ViewCompat.setScaleX(mCircleView, progress);
        ViewCompat.setScaleY(mCircleView, progress);
    }

    private void setTargetOffsetTopAndBottom(int offset) {
        mCircleView.bringToFront();
        mCircleView.offsetTopAndBottom(offset);
        mCurrentTargetOffsetTop = mCircleView.getTop();
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        final int width = getMeasuredWidth();
        int circleWidth = mCircleView.getMeasuredWidth();
        int circleHeight = mCircleView.getMeasuredHeight();
        mCircleView.layout((width / 2 - circleWidth / 2), 0,
                (width / 2 + circleWidth / 2), circleHeight);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCircleView.measure(MeasureSpec.makeMeasureSpec(mCircleWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(mCircleHeight, MeasureSpec.EXACTLY));

    }
}