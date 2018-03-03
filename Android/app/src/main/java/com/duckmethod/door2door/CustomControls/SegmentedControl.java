package com.duckmethod.door2door.CustomControls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.R;
import com.boyscoutpopcorn.nbcs.boyscoutpopcorn.Utilities.DrawUtility;

/**
 * Created by Ryan on 2/11/2018.
 */

public class SegmentedControl extends View implements View.OnTouchListener {

    private final static String TAG = "SegmentedControl";

    //Defaults
    private final static int DEFAULT_NUMBER_OF_SEGMENTS = 2;
    private final static int DEFAULT_COLOR = 0; //Black
    private final static int DEFAULT_TEXT_SIZE = 18;

    //Attrs
    private int mNumberOfSegments;
    private int mActiveBackgroundColor;
    private int mInactiveBackgroundColor;
    private int mActiveTextColor;
    private int mInactiveTextColor;
    private int mTextSize;
    private String[] mSegmentText;

    //Paints
    private Paint mActiveBackgroundPaint;
    private Paint mInactiveBackgroundPaint;
    private Paint mActiveTextPaint;
    private Paint mInactiveTextPaint;

    //Active segment
    private int mActiveSegment = 0;  //0 index

    //Touch listener
    public interface OnSegmentClicked{
        void segmentClicked(int segment);
    }
    private OnSegmentClicked mOnSegmentClicked;


    public SegmentedControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SegmentedControl,
                0, 0);

        try {
            mNumberOfSegments = a.getInteger(R.styleable.SegmentedControl_numSegments, DEFAULT_NUMBER_OF_SEGMENTS);
            mSegmentText = new String[mNumberOfSegments];
            mSegmentText[0] = a.getString(R.styleable.SegmentedControl_segmentText1);
            mSegmentText[1] = a.getString(R.styleable.SegmentedControl_segmentText2);
            if (mNumberOfSegments > 2){
                mSegmentText[2] = a.getString(R.styleable.SegmentedControl_segmentText3);
            }
            if (mNumberOfSegments > 3){
                mSegmentText[3] = a.getString(R.styleable.SegmentedControl_segmentText4);
            }
            mActiveBackgroundColor = a.getColor(R.styleable.SegmentedControl_activeBackgroundColor, DEFAULT_COLOR);
            mInactiveBackgroundColor = a.getColor(R.styleable.SegmentedControl_inactiveBackgroundColor, DEFAULT_COLOR);
            mActiveTextColor = a.getColor(R.styleable.SegmentedControl_activeTextColor, DEFAULT_COLOR);
            mInactiveTextColor = a.getColor(R.styleable.SegmentedControl_inactiveTextColor, DEFAULT_COLOR);
            mTextSize = a.getDimensionPixelSize(R.styleable.SegmentedControl_textSize, DEFAULT_TEXT_SIZE);
        } finally {
            a.recycle();
        }

        mActiveBackgroundPaint = DrawUtility.createFillPaint(mActiveBackgroundColor);
        mInactiveBackgroundPaint = DrawUtility.createFillPaint(mInactiveBackgroundColor);
        mActiveTextPaint = DrawUtility.createTextPaint(mActiveTextColor, mTextSize);
        mInactiveTextPaint = DrawUtility.createTextPaint(mInactiveTextColor, mTextSize);
        mActiveTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mInactiveTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        //Set the onTouchListener
        this.setOnTouchListener(this);

    }

    public SegmentedControl(Context context){
        super(context);
    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //Set the paints for each segment
        Paint[] bgPaints = {mInactiveBackgroundPaint, mInactiveBackgroundPaint,mInactiveBackgroundPaint};
        Paint[] textPaints = {mInactiveTextPaint, mInactiveTextPaint, mInactiveTextPaint};
        bgPaints[mActiveSegment] = mActiveBackgroundPaint;
        textPaints[mActiveSegment] = mActiveTextPaint;

        //Get the rectangle width/height
        float rectWidth = (getWidth()-getPaddingLeft()-getPaddingRight())/mNumberOfSegments;
        float rectHeight = getHeight()-getPaddingTop()-getPaddingBottom();


        //create the rectangles
        Path[] rectPaths = new Path[mNumberOfSegments];
        for (int i = 0; i<mNumberOfSegments; i++){

            boolean[] roundedCorners = {false, false, false, false};

            //Draw the rectangles
            if (i==0){  //Far left, so round the left corners.
                roundedCorners[0] = true;  //tl
                roundedCorners[3] = true;  //bl
            } else if (i == mNumberOfSegments - 1 ){ //Far right
                roundedCorners[1] = true;  //tr
                roundedCorners[2] = true;  //br
            }

            rectPaths[i] = DrawUtility.RoundedRect(getPaddingLeft() + rectWidth * i,
                    getPaddingTop(),
                    rectWidth * (i+ 1) + getPaddingLeft(),
                    getPaddingTop() + rectHeight,
                    10, 10, //Radius
                    roundedCorners[0], roundedCorners[1], roundedCorners[2], roundedCorners[3]);  //where we are rounding
            canvas.drawPath(rectPaths[i], bgPaints[i]);

            //Draw the texts
            Rect bounds = new Rect();
            textPaints[i].getTextBounds(mSegmentText[i], 0, mSegmentText[i].length(), bounds);
            canvas.drawText(mSegmentText[i],
                    getPaddingLeft() + rectWidth * i + rectWidth/2 - bounds.centerX(),
                    getPaddingTop() + rectHeight/2 - bounds.centerY(),
                    textPaints[i]);
        }

    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){

            Log.d(TAG, "MotionEvent: " + String.valueOf(motionEvent.getX()));

            //Find which segment was pressed and return that
            float rectWidth = (getWidth()-getPaddingLeft()-getPaddingRight())/mNumberOfSegments;
            int segmentIndex = (int)((motionEvent.getX()-getPaddingLeft())/rectWidth);
            segmentIndex = Math.max(Math.min(mNumberOfSegments, segmentIndex),0);

            //Return the interface
            if (mOnSegmentClicked != null) {
                mOnSegmentClicked.segmentClicked(segmentIndex);
            }

            //Set the active index
            setActiveSegment(segmentIndex);

        }
        return false;
    }

    public void setActiveSegment(int index){
        mActiveSegment = index;
        invalidate();
    }

    public int getActiveSegment(){
        return mActiveSegment;
    }

    public void setOnSegmentClicked(OnSegmentClicked onSegmentClicked){
        mOnSegmentClicked = onSegmentClicked;
    }

}
