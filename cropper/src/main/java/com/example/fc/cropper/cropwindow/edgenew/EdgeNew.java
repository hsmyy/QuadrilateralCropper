package com.example.fc.cropper.cropwindow.edgenew;

/**
 * Created by fc on 15-3-10.
 */
public enum EdgeNew {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT;

    public static final int MIN_CROP_LENGTH_PX = 40;

    private float xCoordinate;
    private float yCoordinate;

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }
}
