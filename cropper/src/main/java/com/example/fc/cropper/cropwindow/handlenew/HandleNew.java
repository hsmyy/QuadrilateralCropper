package com.example.fc.cropper.cropwindow.handlenew;

import android.graphics.Rect;

import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;

/**
 * Created by fc on 15-3-10.
 */
public enum HandleNew {
    TOP_LEFT(new CornerHandleHelperNew(EdgeNew.TOP_LEFT), EdgeNew.TOP_LEFT),
    TOP_RIGHT(new CornerHandleHelperNew(EdgeNew.TOP_RIGHT), EdgeNew.TOP_RIGHT),
    BOTTOM_LEFT(new CornerHandleHelperNew(EdgeNew.BOTTOM_LEFT), EdgeNew.BOTTOM_LEFT),
    BOTTOM_RIGHT(new CornerHandleHelperNew(EdgeNew.BOTTOM_RIGHT), EdgeNew.BOTTOM_RIGHT);

    private HandleHelperNew helper;

    private EdgeNew edge;

    HandleNew(HandleHelperNew helper, EdgeNew edge){
        this.helper = helper;
        this.edge = edge;
    }

    public void updateCropWindow(float x, float y, Rect imageRect){
        helper.updateCropWindow(x, y, imageRect);
    }

    public EdgeNew getEdge(){
        return edge;
    }

    public boolean isMoveLegal(float x, float y){
        switch(this){
            case TOP_LEFT:
                if(!isRightSide(x, y, EdgeNew.BOTTOM_LEFT, EdgeNew.BOTTOM_RIGHT, true) ||
                        !isRightSide(x, y,  EdgeNew.BOTTOM_RIGHT, EdgeNew.TOP_RIGHT, true)){
                    return false;
                }
                break;
            case TOP_RIGHT:
                if(!isRightSide(x, y, EdgeNew.TOP_LEFT, EdgeNew.BOTTOM_LEFT, true) ||
                        !isRightSide(x, y, EdgeNew.BOTTOM_LEFT, EdgeNew.BOTTOM_RIGHT, true)){
                    return false;
                }
                break;
            case BOTTOM_LEFT:
                if(!isRightSide(x, y, EdgeNew.TOP_RIGHT, EdgeNew.TOP_LEFT, true) ||
                        !isRightSide(x, y, EdgeNew.BOTTOM_RIGHT, EdgeNew.TOP_RIGHT, true)){
                    return false;
                }
                break;
            case BOTTOM_RIGHT:
                if(!isRightSide(x, y, EdgeNew.TOP_RIGHT, EdgeNew.TOP_LEFT, true) ||
                        !isRightSide(x, y, EdgeNew.TOP_LEFT, EdgeNew.BOTTOM_LEFT, true)){
                    return false;
                }
                break;
        }
        return true;
    }

    private boolean isRightSide(float x, float y, EdgeNew p1, EdgeNew p2, boolean isLeft){
        float y1 = y - p1.getyCoordinate();
        float x1 = x - p1.getxCoordinate();
        float y2 = p2.getyCoordinate() - p1.getyCoordinate();
        float x2 = p2.getxCoordinate() - p1.getxCoordinate();

        float res = y1 * x2 - x1 * y2;
        if(!isLeft && res > 0){
            return true;
        }
        if(isLeft && res < 0){
            return true;
        }
        return false;
    }
}
