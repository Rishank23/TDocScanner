package com.rishank_reddy.tdocscanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DocOverlay extends View {

    private Paint mPaint;
    private int mColor;
    private float[] coordinates;

    private CustomCameraActivity customCameraActivity;

    public DocOverlay(Context context) {
        super(context);
        init(context, null);
    }

    public DocOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DocOverlay(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    public void init(Context context, AttributeSet attrs){

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DocOverlay, 0, 0);

        if (typedArray != null && typedArray.length()>0){
            try {
                mColor = typedArray.getColor(R.styleable.DocOverlay_paintColour, Color.BLUE);
            } finally {
                typedArray.recycle();
            }
        }else{
            Log.d("unknownError","BaseVisualizer in init()");
        }

        customCameraActivity = new CustomCameraActivity();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(6.0f);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        coordinates = new float[8];
        coordinates[0] = 100; coordinates[1] = 100; // x0, y0
        coordinates[2] = 100; coordinates[3] = 900; // x1, y1
        coordinates[4] = 900; coordinates[5] = 900; // x1, y1
        coordinates[6] = 900; coordinates[7] = 100; // x3, y3
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int j=0; j<10; j++){
            // TODO: change the loop based on time, like looping every 0.2 seconds
            customCameraActivity.getCoordinates(coordinates);
            for (int i = 0; i < 4; ++i) {
                canvas.drawLine(coordinates[2 * i], coordinates[2 * i + 1], coordinates[(2 * (i + 1)) % 8], coordinates[(2 * (i + 1)) % 8 + 1], mPaint);
            }
//            super.onDraw(canvas);
        }
        super.onDraw(canvas);
    }
}
