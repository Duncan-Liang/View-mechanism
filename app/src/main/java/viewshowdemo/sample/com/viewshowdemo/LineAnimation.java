package viewshowdemo.sample.com.viewshowdemo;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by liangfeng on 2016/11/30.
 */

public class LineAnimation extends RelativeLayout{

    private ArrayList<ValueAnimator> mSinValueAnimator;
    private final String TAG = "LineAnimation";
    private Context mContext;
    public LineAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        mSinValueAnimator = new ArrayList<ValueAnimator>();
//        RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.anim_wave);
//        for (int i=0;i<4;i++) {
//            //Log.d(TAG, "LineAnimation ----- relativeLayout.getHeight()  = " + relativeLayout.getHeight());
//            SinCanvas tmpCanvas = new SinCanvas(mContext, i,  160);
//            long time = 0;
//            switch (i) {
//                case 0:
//                    time = 11000;   //11000
//                    break;
//                case 1:
//                    time = 7000;    //7000
//                    break;
//                case 2:
//                    time = 5000;    //5000
//                    break;
//                case 3:
//                    time = 3000;    //3000
//                    break;
//                default:
//                    break;
//            }
//            ValueAnimator tmpValueAnimator = tmpCanvas.getSinValueAnimator(tmpCanvas.getBarY(), time);
//            tmpValueAnimator.start();
//            mSinValueAnimator.add(0, tmpValueAnimator);
//            relativeLayout.addView(tmpCanvas);
//        }


    }

    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        Log.d(TAG, "LineAnimation ----- onDetachedFromWindow");
        super.onDetachedFromWindow();
        if(mSinValueAnimator!=null&&mSinValueAnimator.size()>0){
            for(int i=0;i<mSinValueAnimator.size();i++){
                mSinValueAnimator.get(i).end();
                mSinValueAnimator.get(i).removeAllUpdateListeners();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "LineAnimation ----- onMeasure");
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "LineAnimation ----- onLayout");
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);

        if(changed)
        {
            RelativeLayout relativeLayout= (RelativeLayout) findViewById(R.id.anim_wave);

            for (int i=0;i<4;i++) {
                SinCanvas tmpCanvas = new SinCanvas(mContext,i,160);
                long time = 0;
                switch (i) {
                    case 0:
                        time =11000;   //11000
                        break;
                    case 1:
                        time =7000;    //7000
                        break;
                    case 2:
                        time =5000;    //5000
                        break;
                    case 3:
                        time =3000;    //3000
                        break;
                    default:
                        break;
                }
                ValueAnimator tmpValueAnimator = tmpCanvas.getSinValueAnimator(tmpCanvas.getBarY(), time);
                tmpValueAnimator.start();
                mSinValueAnimator.add(0, tmpValueAnimator);
                relativeLayout.addView(tmpCanvas);
            }
        }
    }

}

class SinCanvas extends View{
    private float mSinCanvasWidth;
    private float mBarX;
    private float mBarY;
    private float mCenterLength;
    private float mSideLength;

    private Paint mSinPaint;
    private Paint mSinPaintDown;
    private Path mSinPath;

    private int id;
    private float bound;
    private Context mContext;
    public SinCanvas(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SinCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SinCanvas(Context context, int id, float bound) {
        super(context);
        this.id = id;
        this.bound = bound;
        mContext=context;
        init();
    }

    public float getDensity(Context context){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    public void init() {
        Log.d("LineAnimation", "LineAnimation ----- SinCanvas init");
        mSinPath = new Path();
        mSinPaint = new Paint();
        mSinPaint.setStyle(Paint.Style.STROKE);
        mSinPaint.setStrokeWidth(1*getDensity(mContext));
        mSinPaint.setAntiAlias(true);
        mSinPaint.setColor(0xff724682);
        if(id==0){
            mSinPaint.setColor(0xffcd72e7);
        }
        mSinPaintDown = new Paint();
        mSinPaintDown.setAntiAlias(true);
        mSinPaintDown.setColor(0xff34243e);
        switch (id) {
            case 0:
                mBarY = (bound)/2-1;
                break;
            case 1:
                mBarY = (bound)/2-5;
                break;
            case 2:
                mBarY = -((bound)/2-1);
                break;
            case 3:
                mBarY = -((bound)/2-5);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("LineAnimation", "LineAnimation ----- SinCanvas onMeasure");
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d("LineAnimation", "LineAnimation ----- SinCanvas onLayout");
        super.onLayout(changed, left, top, right, bottom);
        mSinCanvasWidth = right - left;
        mCenterLength = mSinCanvasWidth * (0.4f);
        mSideLength = (mSinCanvasWidth - mCenterLength) / 2f;
        mBarX = mSideLength / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("LineAnimation", "LineAnimation ----- SinCanvas onDraw");
        super.onDraw(canvas);

        mSinPath.reset();
        mSinPath.moveTo(0, (bound)/2);
        mSinPath.rCubicTo(mBarX*3/4, -mBarY/256, mSideLength - mBarX, -mBarY, mSideLength, 0);
        mSinPath.rCubicTo(mBarX, mBarY, mCenterLength - mBarX, mBarY, mCenterLength, 0);
        mSinPath.rCubicTo(mBarX, -mBarY, mSideLength-(mBarX)*3/4, -mBarY/256, mSideLength, 0);
        canvas.drawPath(mSinPath, mSinPaint);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("LineAnimation", "LineAnimation ----- SinCanvas dispatchDraw");
        super.dispatchDraw(canvas);
    }

    public float getBarY() {
        return mBarY;
    }

    public ValueAnimator getSinValueAnimator(float startPos, long duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(startPos, -startPos);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener(){
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                setBarY(Float.valueOf(animation.getAnimatedValue().toString()));
            }
        });
        return valueAnimator;
    }

    public void setBarY(float barY) {
        mBarY = barY;
        invalidate();
    }

}