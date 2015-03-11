package com.example.fc.cropper.cropwindow.handlenew;

import android.graphics.Rect;

import com.example.fc.cropper.cropwindow.edge.Edge;
import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;

/**
 * Created by fc on 15-3-10.
 */
public abstract class HandleHelperNew {

    protected EdgeNew primaryEdge;
    protected EdgeNew secondaryEdge;

    public HandleHelperNew(EdgeNew primaryEdge, EdgeNew secondaryEdge){
        this.primaryEdge = primaryEdge;
        this.secondaryEdge = secondaryEdge;
    }

    public abstract void updateCropWindow(float x, float y, Rect imageRect);
}
