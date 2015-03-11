package com.example.fc.cropper.cropwindow.drawer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;

/**
 * Created by fc on 15-3-10.
 */
public class NewDrawer implements Drawer {
    @Override
    public void drawBackground(Canvas canvas, Rect bitmapRect, Paint backgroundPaint) {
        Path path = new Path();
        path.moveTo(0,0);
        path.lineTo(bitmapRect.width(), 0);
        path.lineTo(bitmapRect.width(), bitmapRect.height());
        path.lineTo(0, bitmapRect.height());
        path.lineTo(EdgeNew.BOTTOM_LEFT.getxCoordinate(), EdgeNew.BOTTOM_LEFT.getyCoordinate());
        path.lineTo(EdgeNew.BOTTOM_RIGHT.getxCoordinate(), EdgeNew.BOTTOM_RIGHT.getyCoordinate());
        path.lineTo(EdgeNew.TOP_RIGHT.getxCoordinate(), EdgeNew.TOP_RIGHT.getyCoordinate());
        path.lineTo(EdgeNew.TOP_LEFT.getxCoordinate(), EdgeNew.TOP_LEFT.getyCoordinate());
        path.lineTo(EdgeNew.BOTTOM_LEFT.getxCoordinate(), EdgeNew.BOTTOM_LEFT.getyCoordinate());
        path.lineTo(0, bitmapRect.height());
        path.lineTo(0,0);

        canvas.drawPath(path, backgroundPaint);
    }

    @Override
    public void drawRuleOfThirdsGuidelines(Canvas canvas, Paint guidePaint) {
        //empty
    }

    @Override
    public void drawRect(Canvas canvas, Paint borderPaint) {
        Path path = new Path();
        path.moveTo(EdgeNew.TOP_LEFT.getxCoordinate(), EdgeNew.TOP_LEFT.getyCoordinate());
        path.lineTo(EdgeNew.TOP_RIGHT.getxCoordinate(), EdgeNew.TOP_RIGHT.getyCoordinate());
        path.lineTo(EdgeNew.BOTTOM_RIGHT.getxCoordinate(), EdgeNew.BOTTOM_RIGHT.getyCoordinate());
        path.lineTo(EdgeNew.BOTTOM_LEFT.getxCoordinate(), EdgeNew.BOTTOM_LEFT.getyCoordinate());
        path.lineTo(EdgeNew.TOP_LEFT.getxCoordinate(), EdgeNew.TOP_LEFT.getyCoordinate());

        canvas.drawPath(path, borderPaint);
    }

    @Override
    public void drawCorner(Canvas canvas, float cornerRadius, Paint cornerPaint) {
        canvas.drawCircle(EdgeNew.TOP_LEFT.getxCoordinate(), EdgeNew.TOP_LEFT.getyCoordinate(), cornerRadius, cornerPaint);
        canvas.drawCircle(EdgeNew.BOTTOM_LEFT.getxCoordinate(), EdgeNew.BOTTOM_LEFT.getyCoordinate(), cornerRadius, cornerPaint);
        canvas.drawCircle(EdgeNew.TOP_RIGHT.getxCoordinate(), EdgeNew.TOP_RIGHT.getyCoordinate(), cornerRadius, cornerPaint);
        canvas.drawCircle(EdgeNew.BOTTOM_RIGHT.getxCoordinate(), EdgeNew.BOTTOM_RIGHT.getyCoordinate(), cornerRadius, cornerPaint);

        canvas.drawCircle(midX(EdgeNew.TOP_LEFT, EdgeNew.TOP_RIGHT), midY(EdgeNew.TOP_LEFT, EdgeNew.TOP_RIGHT), cornerRadius, cornerPaint);
        canvas.drawCircle(midX(EdgeNew.TOP_LEFT, EdgeNew.BOTTOM_LEFT), midY(EdgeNew.TOP_LEFT, EdgeNew.BOTTOM_LEFT), cornerRadius, cornerPaint);
        canvas.drawCircle(midX(EdgeNew.BOTTOM_LEFT, EdgeNew.BOTTOM_RIGHT), midY(EdgeNew.BOTTOM_LEFT, EdgeNew.BOTTOM_RIGHT), cornerRadius, cornerPaint);
        canvas.drawCircle(midX(EdgeNew.TOP_RIGHT, EdgeNew.BOTTOM_RIGHT), midY(EdgeNew.TOP_RIGHT, EdgeNew.BOTTOM_RIGHT), cornerRadius, cornerPaint);
    }

    private static final float midX(EdgeNew edgeA, EdgeNew edgeB){
        return (edgeA.getxCoordinate() + edgeB.getxCoordinate()) / 2;
    }

    private static final float midY(EdgeNew edgeA, EdgeNew edgeB){
        return (edgeA.getyCoordinate() + edgeB.getyCoordinate()) / 2;
    }
}
