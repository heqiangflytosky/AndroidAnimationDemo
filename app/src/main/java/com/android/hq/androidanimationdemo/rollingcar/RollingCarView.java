package com.android.hq.androidanimationdemo.rollingcar;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import com.android.hq.androidanimationdemo.R;

/**
 * Created by heqiang on 16-12-15.
 */
public class RollingCarView extends View {

    private int mLastX;
    private int mWidth;
    private int mDownX;
    private int mMiddle;
    private int mMoveDistance;
    private int mMinVelocity;
    private float mDamping = 0.5f;
    private int mMaxMoveDistance;
    private Scroller mScroller;
    private boolean mIsFling;
    private boolean mClick;

    private final static float WHEEL_RADIUS = 30f;        //轮胎半径
    private final static float Y_WHEEL_AXIS = 600f;        //轮胎轴Y点
    private final static float WHEEL_AXIS_DISTANCE = 150f; //两轮胎轴之间的距离

    private final static float WHEEL_OUTER_RADIUS = 50f;        //车身轮胎部位半径

    private final static float CAR_BODY_LENGTH = 300f;     //车身长度
    private final static float CAR_BODY_HEIGHT_EDGE = 60f;      //车身引擎盖高度

    private Paint mTyrePainter;     //轮胎画笔
    private Paint mHubPainter;      //轮毂画笔
    private Paint mCarBodyPainter;      //车身画笔
    private Path mCarBodyPath;

    private VelocityTracker mVelocityTracker;

    public RollingCarView(Context context) {
        this(context, null);
    }

    public RollingCarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollingCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RollingCarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView(){
        mScroller = new Scroller(getContext());
        mMinVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();

        mTyrePainter = new Paint();
        mTyrePainter.setAntiAlias(true);
        mTyrePainter.setStyle(Paint.Style.STROKE);
        mTyrePainter.setStrokeWidth(10);
        mTyrePainter.setColor(0xff303030);

        mHubPainter = new Paint();
        mHubPainter.setAntiAlias(true);
        mHubPainter.setStyle(Paint.Style.STROKE);
        mHubPainter.setStrokeWidth(5);
        mHubPainter.setColor(0xff606060);

        mCarBodyPainter = new Paint();
        mCarBodyPainter.setAntiAlias(true);
        mCarBodyPainter.setStyle(Paint.Style.STROKE);
        mCarBodyPainter.setStrokeWidth(3);
        mCarBodyPainter.setColor(0xff505050);

    }

    private void drawCar(Canvas canvas){
        Log.e("Test", "mMoveDistance = " + mMoveDistance);
        mMoveDistance = Math.min(mMoveDistance, mMaxMoveDistance);
        mMoveDistance = Math.max(mMoveDistance, -mMaxMoveDistance);
        //draw wheel
        int count_w = canvas.save();
        canvas.translate(mMoveDistance - WHEEL_AXIS_DISTANCE / 2, 0);
        drawWheel(canvas);
        canvas.translate(WHEEL_AXIS_DISTANCE, 0);
        drawWheel(canvas);
        canvas.restoreToCount(count_w);

        //draw car body
        drawCarBody(canvas);
    }

    private void drawWheel(Canvas canvas){
        int count = canvas.save();

        double  circumference = 2 * Math.PI * WHEEL_RADIUS;
        float cx = mMiddle;
        float cy = Y_WHEEL_AXIS;
        canvas.rotate((float) (360 * ((mMoveDistance % circumference) / circumference)), cx, cy);
        canvas.drawCircle(cx, cy, WHEEL_RADIUS, mTyrePainter);

        for (int i = 0; i<5; i++){
            canvas.drawLine(cx, cy,cx + WHEEL_RADIUS * (float)Math.cos((Math.PI * 2 * (i+1)) / 5.0f ), cy + WHEEL_RADIUS * (float)Math.sin((Math.PI * 2 * (i + 1) / 5.0f)), mHubPainter);
        }

        canvas.restoreToCount(count);
    }

    private void drawCarBody(Canvas canvas){
        int count = canvas.save();
        canvas.translate(mMoveDistance, 0);
/*
        float car_head_x = mMiddle - CAR_BODY_LENGTH/2;
        float car_head_x_bottom = Y_WHEEL_AXIS;
        float car_head_x_top = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE;

        canvas.drawLine(mMiddle, car_head_x_bottom, mMiddle - (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS), car_head_x_bottom, mCarBodyPainter);

        RectF wheelOuterRectHead = new RectF();
        wheelOuterRectHead.left = mMiddle - (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS);
        wheelOuterRectHead.top = Y_WHEEL_AXIS - WHEEL_OUTER_RADIUS;
        wheelOuterRectHead.right = mMiddle - (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS);
        wheelOuterRectHead.bottom = car_head_x_bottom + WHEEL_OUTER_RADIUS;
        canvas.drawArc(wheelOuterRectHead, 180, 180, false, mCarBodyPainter);

        canvas.drawLine(mMiddle - (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS), car_head_x_bottom, car_head_x, car_head_x_bottom, mCarBodyPainter);

        canvas.drawLine(car_head_x, car_head_x_bottom, car_head_x, car_head_x_top, mCarBodyPainter);

        canvas.drawLine(car_head_x, car_head_x_top, mMiddle - WHEEL_AXIS_DISTANCE/2, car_head_x_top, mCarBodyPainter);

        RectF wheelOuterRectBody = new RectF();
        wheelOuterRectBody.left = mMiddle - WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.top = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE -  WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.right = mMiddle + WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.bottom = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE +  WHEEL_AXIS_DISTANCE/2;
        canvas.drawArc(wheelOuterRectBody, 180, 180, false, mCarBodyPainter);

        float car_back_x = mMiddle + CAR_BODY_LENGTH/2;
        canvas.drawLine(mMiddle + WHEEL_AXIS_DISTANCE / 2, car_head_x_top, car_back_x, car_head_x_top, mCarBodyPainter);

        canvas.drawLine(car_back_x, car_head_x_top, car_back_x, car_head_x_bottom, mCarBodyPainter);

        canvas.drawLine(car_back_x, car_head_x_bottom, mMiddle + WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS, car_head_x_bottom, mCarBodyPainter);

        RectF wheelOuterRectBack = new RectF();
        wheelOuterRectBack.left = mMiddle + (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS);
        wheelOuterRectBack.top = Y_WHEEL_AXIS - WHEEL_OUTER_RADIUS;
        wheelOuterRectBack.right = mMiddle + (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS);
        wheelOuterRectBack.bottom = car_head_x_bottom + WHEEL_OUTER_RADIUS;
        canvas.drawArc(wheelOuterRectBack, 180, 180, false, mCarBodyPainter);

        canvas.drawLine(mMiddle + (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS), car_head_x_bottom, mMiddle, car_head_x_bottom, mCarBodyPainter);
*/
        if(mCarBodyPath == null){
            initCarBodyPath();
        }
        canvas.drawPath(mCarBodyPath, mCarBodyPainter);

        canvas.restoreToCount(count);
    }

    private void initCarBodyPath(){
        mCarBodyPath = new Path();

        float car_head_x = mMiddle - CAR_BODY_LENGTH/2;
        float car_head_x_bottom = Y_WHEEL_AXIS;
        float car_head_x_top = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE;

        mCarBodyPath.moveTo(mMiddle, car_head_x_bottom);
        mCarBodyPath.lineTo(mMiddle - (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS), car_head_x_bottom);

        RectF wheelOuterRectHead = new RectF();
        wheelOuterRectHead.left = mMiddle - (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS);
        wheelOuterRectHead.top = Y_WHEEL_AXIS - WHEEL_OUTER_RADIUS;
        wheelOuterRectHead.right = mMiddle - (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS);
        wheelOuterRectHead.bottom = car_head_x_bottom + WHEEL_OUTER_RADIUS;
        mCarBodyPath.arcTo(wheelOuterRectHead, 180, 180, true);

        mCarBodyPath.moveTo(mMiddle - (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS), car_head_x_bottom);
        mCarBodyPath.lineTo(car_head_x, car_head_x_bottom);
        mCarBodyPath.lineTo(car_head_x, car_head_x_top);
        mCarBodyPath.lineTo(mMiddle - WHEEL_AXIS_DISTANCE / 2, car_head_x_top);

        RectF wheelOuterRectBody = new RectF();
        wheelOuterRectBody.left = mMiddle - WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.top = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE -  WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.right = mMiddle + WHEEL_AXIS_DISTANCE/2;
        wheelOuterRectBody.bottom = Y_WHEEL_AXIS - CAR_BODY_HEIGHT_EDGE +  WHEEL_AXIS_DISTANCE/2;
        mCarBodyPath.arcTo(wheelOuterRectBody, 180, 180, false);

        float car_back_x = mMiddle + CAR_BODY_LENGTH/2;
        mCarBodyPath.lineTo(car_back_x, car_head_x_top);
        mCarBodyPath.lineTo(car_back_x, car_head_x_bottom);
        mCarBodyPath.lineTo(mMiddle + WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS, car_head_x_bottom);

        RectF wheelOuterRectBack = new RectF();
        wheelOuterRectBack.left = mMiddle + (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS);
        wheelOuterRectBack.top = Y_WHEEL_AXIS - WHEEL_OUTER_RADIUS;
        wheelOuterRectBack.right = mMiddle + (WHEEL_AXIS_DISTANCE / 2 + WHEEL_OUTER_RADIUS);
        wheelOuterRectBack.bottom = car_head_x_bottom + WHEEL_OUTER_RADIUS;
        mCarBodyPath.arcTo(wheelOuterRectBack, 180, 180, true);

        mCarBodyPath.moveTo(mMiddle + (WHEEL_AXIS_DISTANCE / 2 - WHEEL_OUTER_RADIUS), car_head_x_bottom);
        mCarBodyPath.lineTo(mMiddle, car_head_x_bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCar(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mMiddle = mWidth/2;
        mMaxMoveDistance = (int)(mMiddle - CAR_BODY_LENGTH / 2) - 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);

        mIsFling = false;
        int xPosition = (int) event.getX();

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);

        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mScroller.forceFinished(true);
                mLastX = xPosition;
                mDownX = xPosition;
                break;
            case MotionEvent.ACTION_MOVE:

                float move = xPosition - mLastX;
                if(isOutOfScope(move)) {
                    if(move > 0){
                        mMoveDistance = mMaxMoveDistance;
                    }else if(move < 0){
                        mMoveDistance = -mMaxMoveDistance;
                    }
                    return true;
                }
                mMoveDistance += move;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mClick = false;
                if (Math.abs(mDownX - mLastX) < 5) {
                    mClick = true;
                }
                if(!mClick){
                    mLastX = 0;
                    invalidate();
                    computeVelocityTracker();
                    return true;
                }
                break;

        }
        mLastX = xPosition;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            int xPosition = mScroller.getCurrX();
            if (mIsFling) {
                float move = xPosition - mLastX;
                mLastX = xPosition;
                if (isOutOfScope(move)) {
                    if(move > 0){
                        mMoveDistance = mMaxMoveDistance;
                    }else{
                        mMoveDistance = -mMaxMoveDistance;
                    }
                    mScroller.forceFinished(true);
                    mIsFling = false;
                    return;
                }
                mMoveDistance += move;
            } else {
                mMoveDistance = xPosition;
            }
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
        }
    }

    private void computeVelocityTracker() {
        mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = mVelocityTracker.getXVelocity();
        if (Math.abs(xVelocity) > mMinVelocity) {
            xVelocity = (1 - mDamping) * xVelocity;
            mIsFling = true;
            mScroller.fling(0, 0, (int) xVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        }
    }

    private boolean isOutOfScope(float dt){
        if ((dt >= 0 && mMoveDistance >= mMaxMoveDistance) || (dt <= 0 && mMoveDistance <= -mMaxMoveDistance)){
            return true;
        }
        return false;
    }
}
