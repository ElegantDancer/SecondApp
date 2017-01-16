package com.zhenzhen.secondapp.Circle;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.zhenzhen.secondapp.R;


/**
 * Created by zhenzhen on 2016/12/17.
 */

public class CircleRun extends View {

    private final Interpolator ANGLE_INTERPOLATER = new LinearInterpolator();
    private final Interpolator SWEEP_INTERPOLATER = new AccelerateDecelerateInterpolator();
    private final int ANGLE_ANIMATOR_DURATION = 1500;
    private final int SWEEP_ANIMATOR_DURATION = 1000;
    private final int MIN_SWEEP_ANGLE = 30;
    private final int DEFAULT_BORDER_WIDTH = 3;

    private RectF fBounds = new RectF();

    private ObjectAnimator mObjectAnimatorAngle;
    private ObjectAnimator mObjectAnimatorSweep;
    private ValueAnimator fractionAnimator;
    private boolean mModelAppearing = true;

    private Paint mPaint;
    private Paint mSweepPaint;
    private Paint mHookPaint;
    private Paint mArrowPaint;

    private float mCurGlobalAngleOffset;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;

    private float mBorderWidth;
    private boolean mRunning;
    private int[] mColors;
    private int mCurrentColorIndex;
    private int mNextColorIndex;

    private final int STATE_LOADING = 1;
    private final int STATE_FINISH = 2;
    private final int STATE_ERROR = 3;
    private int mCurrentState;

    private Path mHookPath;
    private Path mArrowPath;
    private Path mErrorPath;

    private int ARROW_WIDTH = 10 * 2;
    private int ARROW_HEIGHT = 5 * 2;


    private boolean isShowArrow;

    private float mRingCenterRadius;

    public CircleRun(Context context) {
        this(context, null);
    }

    public CircleRun(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRun(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        float density = context.getResources().getDisplayMetrics().density;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);
        //圆圈的宽度
        mBorderWidth = array.getDimension(R.styleable.CircleProgress_progressBorderWidth, DEFAULT_BORDER_WIDTH * density);
        isShowArrow = array.getBoolean(R.styleable.CircleProgress_showArrow, false);
        array.recycle();

        ARROW_WIDTH = (int) (mBorderWidth * 2);
        ARROW_HEIGHT = (int) mBorderWidth;
        mColors = new int[]{
                Color.RED, Color.BLUE, Color.GREEN, Color.GRAY
        };
        mCurrentColorIndex = 0;
        mNextColorIndex = 1;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mColors[mCurrentColorIndex]);

        mHookPaint = new Paint(mPaint);
        mArrowPaint = new Paint(mPaint);

        mHookPath = new Path();
        mErrorPath = new Path();

        setupAnimations();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mCurrentState){
            case STATE_LOADING:
                drawArc(canvas);
                break;
            case STATE_FINISH:
                break;
            case STATE_ERROR:
                break;
        }
    }

    private void drawArc(Canvas canvas) {

        float startAngle = mCurrentGlobalAngle - mCurGlobalAngleOffset;
        float sweepAngle = mCurrentSweepAngle;
        if(mModelAppearing){
            mPaint.setColor(mColors[0]);
            sweepAngle += MIN_SWEEP_ANGLE;
        }else{
            startAngle += sweepAngle;
            sweepAngle = 360f - sweepAngle - MIN_SWEEP_ANGLE;
        }

        canvas.drawArc(fBounds, startAngle, sweepAngle, false, mPaint);

        if(isShowArrow){

        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int min = Math.min(w, h);
        fBounds.left = mBorderWidth * 2f + 0.5f;
        fBounds.right = min - mBorderWidth * 2f - .5f;
        fBounds.top = mBorderWidth * 2f + .5f;
        fBounds.bottom = min - mBorderWidth * 2f - .5f;

        mRingCenterRadius = Math.min(fBounds.centerX() - fBounds.left, fBounds.centerY() - fBounds.top) - mBorderWidth;
    }

    private void setupAnimations() {

        mObjectAnimatorAngle = ObjectAnimator.ofFloat(this, mAngleProperty, 360f);
        mObjectAnimatorAngle.setInterpolator(ANGLE_INTERPOLATER);
        mObjectAnimatorAngle.setDuration(ANGLE_ANIMATOR_DURATION);
        mObjectAnimatorAngle.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorAngle.setRepeatCount(ValueAnimator.INFINITE);

        mObjectAnimatorSweep = ObjectAnimator.ofFloat(this, mSweepProperty, 360f - MIN_SWEEP_ANGLE * 2);
        mObjectAnimatorSweep.setInterpolator(SWEEP_INTERPOLATER);
        mObjectAnimatorSweep.setDuration(SWEEP_ANIMATOR_DURATION);
        mObjectAnimatorSweep.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimatorSweep.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimatorSweep.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        fractionAnimator = ValueAnimator.ofInt(0, 255);
        fractionAnimator.setInterpolator(ANGLE_INTERPOLATER);
        fractionAnimator.setDuration(100);
        fractionAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mCurrentState = STATE_LOADING;
        mObjectAnimatorAngle.start();
        mObjectAnimatorSweep.start();
    }

    /**
     * 用于动画Animation
     */
    private Property<CircleRun, Float> mAngleProperty = new Property<CircleRun, Float>(Float.class, "angle") {
        @Override
        public Float get(CircleRun object) {
            return object.getCurrentGlobalAngle();
        }

        @Override
        public void set(CircleRun object, Float value) {
            object.setCurrentGlobalAngel(value);
        }
    };

    private Property<CircleRun, Float> mSweepProperty = new Property<CircleRun, Float>(Float.class, "arc") {
        @Override
        public Float get(CircleRun object) {
            return object.getCurrentSweepAngle();
        }

        @Override
        public void set(CircleRun object, Float value) {
            object.setCurrentSweepAngel(value);
            invalidate();
        }
    };


    private float getCurrentGlobalAngle(){
        return mCurrentGlobalAngle;
    }

    private void setCurrentGlobalAngel(float angle){
        this.mCurrentGlobalAngle = angle;
    }

    private float getCurrentSweepAngle(){
        return mCurrentSweepAngle;
    }

    private void setCurrentSweepAngel(float angle){
        this.mCurrentSweepAngle = angle;
        invalidate();
    }
}
