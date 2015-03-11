package com.example.fc.cropper.cropwindow.handlenew;

import android.util.Pair;

import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;

/**
 * Created by fc on 15-3-10.
 */
public class HandleUtilNew {
    public static HandleNew getPressedHandle(float x, float y,
                                             float targetRadius){
        HandleNew pressedHandle = null;

        for(HandleNew handle : HandleNew.values()){
            EdgeNew edge = handle.getEdge();
            if(nearTarget(edge, x, y, targetRadius)){
                pressedHandle = handle;
            }
        }
        return pressedHandle;
    }

    private static boolean nearTarget(EdgeNew edge, float x, float y, float targetRadius){
        return Math.sqrt((edge.getxCoordinate() - x) * (edge.getxCoordinate() - x) +
                (edge.getyCoordinate() - y) * (edge.getyCoordinate() - y)) < targetRadius;
    }

    public static Pair<Float, Float> getOffset(HandleNew handle, float x, float y){
        if(handle == null){
            return null;
        }

        float touchOffsetX = 0;
        float touchOffsetY = 0;

        switch(handle){
            case TOP_LEFT:
                touchOffsetX = EdgeNew.TOP_LEFT.getxCoordinate() - x;
                touchOffsetY = EdgeNew.TOP_LEFT.getyCoordinate() - y;
                break;
            case TOP_RIGHT:
                touchOffsetX = EdgeNew.TOP_RIGHT.getxCoordinate() - x;
                touchOffsetY = EdgeNew.TOP_RIGHT.getyCoordinate() - y;
                break;
            case BOTTOM_LEFT:
                touchOffsetX = EdgeNew.BOTTOM_LEFT.getxCoordinate() - x;
                touchOffsetY = EdgeNew.BOTTOM_RIGHT.getyCoordinate() - y;
                break;
            case BOTTOM_RIGHT:
                touchOffsetX = EdgeNew.BOTTOM_RIGHT.getxCoordinate() - x;
                touchOffsetY = EdgeNew.BOTTOM_RIGHT.getyCoordinate() - y;
                break;
        }

        return new Pair<Float, Float>(touchOffsetX, touchOffsetY);
    }
}
