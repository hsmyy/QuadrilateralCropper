package com.example.fc.cropper.cropwindow.handlenew;

import android.graphics.Rect;

import com.example.fc.cropper.cropwindow.edge.Edge;
import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;

/**
 * Created by fc on 15-3-10.
 */
public class CornerHandleHelperNew extends HandleHelperNew {

    public CornerHandleHelperNew(EdgeNew singleEdge) {
        super(singleEdge, null);
    }

    @Override
    public void updateCropWindow(float x, float y, Rect imageRect) {
        this.primaryEdge.setxCoordinate(x);
        this.primaryEdge.setyCoordinate(y);
    }


}
