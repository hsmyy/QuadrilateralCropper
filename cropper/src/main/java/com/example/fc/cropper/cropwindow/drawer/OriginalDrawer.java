package com.example.fc.cropper.cropwindow.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.fc.cropper.cropwindow.edge.Edge;

/**
 * Created by fc on 15-3-10.
 */
public class OriginalDrawer implements Drawer {
    @Override
    public void drawBackground(Canvas canvas, Rect bitmapRect, Paint backgroundPaint) {
        final float left = Edge.LEFT.getCoordinate();
        final float top = Edge.TOP.getCoordinate();
        final float right = Edge.RIGHT.getCoordinate();
        final float bottom = Edge.BOTTOM.getCoordinate();

        /*-
          -------------------------------------
          |                top                |
          -------------------------------------
          |      |                    |       |
          |      |                    |       |
          | left |                    | right |
          |      |                    |       |
          |      |                    |       |
          -------------------------------------
          |              bottom               |
          -------------------------------------
         */

        // Draw "top", "bottom", "left", then "right" quadrants.
        canvas.drawRect(bitmapRect.left, bitmapRect.top, bitmapRect.right, top, backgroundPaint);
        canvas.drawRect(bitmapRect.left, bottom, bitmapRect.right, bitmapRect.bottom, backgroundPaint);
        canvas.drawRect(bitmapRect.left, top, left, bottom, backgroundPaint);
        canvas.drawRect(right, top, bitmapRect.right, bottom, backgroundPaint);
    }

    @Override
    public void drawRuleOfThirdsGuidelines(Canvas canvas, Paint guidePaint) {
        final float left = Edge.LEFT.getCoordinate();
        final float top = Edge.TOP.getCoordinate();
        final float right = Edge.RIGHT.getCoordinate();
        final float bottom = Edge.BOTTOM.getCoordinate();

        // Draw vertical guidelines.
        final float oneThirdCropWidth = Edge.getWidth() / 3;

        final float x1 = left + oneThirdCropWidth;
        canvas.drawLine(x1, top, x1, bottom, guidePaint);
        final float x2 = right - oneThirdCropWidth;
        canvas.drawLine(x2, top, x2, bottom, guidePaint);

        // Draw horizontal guidelines.
        final float oneThirdCropHeight = Edge.getHeight() / 3;

        final float y1 = top + oneThirdCropHeight;
        canvas.drawLine(left, y1, right, y1, guidePaint);
        final float y2 = bottom - oneThirdCropHeight;
        canvas.drawLine(left, y2, right, y2, guidePaint);
    }

    @Override
    public void drawRect(Canvas canvas, Paint borderPaint) {
        canvas.drawRect(Edge.LEFT.getCoordinate(),
                Edge.TOP.getCoordinate(),
                Edge.RIGHT.getCoordinate(),
                Edge.BOTTOM.getCoordinate(),
                borderPaint);
    }

    @Override
    public void drawCorner(Canvas canvas, float cornerRadius, Paint cornerPaint) {
        final float left = Edge.LEFT.getCoordinate();
        final float top = Edge.TOP.getCoordinate();
        final float right = Edge.RIGHT.getCoordinate();
        final float bottom = Edge.BOTTOM.getCoordinate();


        canvas.drawCircle(left, top, cornerRadius, cornerPaint);
        canvas.drawCircle(left, bottom, cornerRadius, cornerPaint);
        canvas.drawCircle(right, top, cornerRadius, cornerPaint);
        canvas.drawCircle(right, bottom, cornerRadius, cornerPaint);

        canvas.drawCircle((left + right) / 2, top, cornerRadius, cornerPaint);
        canvas.drawCircle((left + right) / 2, bottom, cornerRadius, cornerPaint);
        canvas.drawCircle(left, (top + bottom) / 2, cornerRadius, cornerPaint);
        canvas.drawCircle(right, (top + bottom) / 2, cornerRadius, cornerPaint);
    }
}
