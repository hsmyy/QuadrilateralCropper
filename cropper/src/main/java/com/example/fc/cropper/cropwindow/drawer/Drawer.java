package com.example.fc.cropper.cropwindow.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by fc on 15-3-10.
 */
public interface Drawer {
    public void drawBackground(Canvas canvas, Rect bitmapRect, Paint backgroundPaint);
    public void drawRuleOfThirdsGuidelines(Canvas canvas, Paint guidePaint);
    public void drawRect(Canvas canvas, Paint borderPaint);
    public void drawCorner(Canvas canvas, float cornerRadius, Paint cornerPaint);

}
