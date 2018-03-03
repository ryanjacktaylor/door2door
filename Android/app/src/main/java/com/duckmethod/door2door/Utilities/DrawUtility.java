package com.duckmethod.door2door.Utilities;

import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Ryan on 2/17/2018.
 */

public class DrawUtility {

    private final static String TAG = "DrawUtility";

    public static Path RoundedRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean br, boolean bl
    ){
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else{
            path.rLineTo(0, -ry);
            path.rLineTo(-rx,0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else{
            path.rLineTo(-rx, 0);
            path.rLineTo(0,ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else{
            path.rLineTo(0, ry);
            path.rLineTo(rx,0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else{
            path.rLineTo(rx,0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }

    public static Paint createFillPaint(int color){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public static Paint createStrokePaint(int color, float stroke){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public static Paint createTextPaint(int color, float size){
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setTextSize(size);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}
