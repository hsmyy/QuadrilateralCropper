package com.example.fc.cropper.cropwindow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.fc.cropper.CropImageView;
import com.example.fc.cropper.cropwindow.drawer.Drawer;
import com.example.fc.cropper.cropwindow.drawer.NewDrawer;
import com.example.fc.cropper.cropwindow.edgenew.EdgeNew;
import com.example.fc.cropper.cropwindow.handlenew.HandleNew;
import com.example.fc.cropper.cropwindow.handlenew.HandleUtilNew;
import com.example.fc.cropper.util.HandleUtil;
import com.example.fc.cropper.util.PaintUtil;

/**
 * Created by fc on 15-3-10.
 */
public class CropOverlayViewNew extends View {
    // Private Constants ///////////////////////////////////////////////////////

    private static final int SNAP_RADIUS_DP = 6;
    private static final float DEFAULT_SHOW_GUIDELINES_LIMIT = 100;

    // Gets default values from PaintUtil, sets a bunch of values such that the
    // corners will draw correctly
    private static final float DEFAULT_CORNER_THICKNESS_DP = PaintUtil.getCornerThickness();
    private static final float DEFAULT_LINE_THICKNESS_DP = PaintUtil.getLineThickness();
    private static final float DEFAULT_CORNER_OFFSET_DP = (DEFAULT_CORNER_THICKNESS_DP / 2) - (DEFAULT_LINE_THICKNESS_DP / 2);
    private static final float DEFAULT_CORNER_EXTENSION_DP = DEFAULT_CORNER_THICKNESS_DP / 2
            + DEFAULT_CORNER_OFFSET_DP;
    private static final float DEFAULT_CORNER_LENGTH_DP = 20;

    private static final float DEFAULT_CORNER_RADIUS = 5;

    // mGuidelines enumerations

    // Member Variables ////////////////////////////////////////////////////////

    // The Paint used to draw the white rectangle around the crop area.
    private Paint mBorderPaint;

    // The Paint used to draw the guidelines within the crop area when pressed.

    // The Paint used to draw the corners of the Border

    // The Paint used to darken the surrounding areas outside the crop area.
    private Paint mBackgroundPaint;

    // The bounding box around the Bitmap that we are cropping.
    private Rect mBitmapRect;

    private Paint mCornerCirclePaint;

    // The radius of the touch zone (in pixels) around a given Handle.
    private float mHandleRadius;

    // An edge of the crop window will snap to the corresponding edge of a
    // specified bounding box when the crop window edge is less than or equal to
    // this distance (in pixels) away from the bounding box edge.
    private float mSnapRadius;

    // Holds the x and y offset between the exact touch location and the exact
    // handle location that is activated. There may be an offset because we
    // allow for some leeway (specified by mHandleRadius) in activating a
    // handle. However, we want to maintain these offset values while the handle
    // is being dragged so that the handle doesn't jump.
    private Pair<Float, Float> mTouchOffset;

    // The Handle that is currently pressed; null if no Handle is pressed.
    private HandleNew mPressedHandle;

    // Flag indicating if the crop area should always be a certain aspect ratio
    // (indicated by mTargetAspectRatio).
    private boolean mFixAspectRatio = CropImageView.DEFAULT_FIXED_ASPECT_RATIO;

    // Floats to save the current aspect ratio of the image
    private int mAspectRatioX = CropImageView.DEFAULT_ASPECT_RATIO_X;
    private int mAspectRatioY = CropImageView.DEFAULT_ASPECT_RATIO_Y;

    // The aspect ratio that the crop area should maintain; this variable is
    // only used when mMaintainAspectRatio is true.
    private float mTargetAspectRatio = ((float) mAspectRatioX) / mAspectRatioY;

    // Instance variables for customizable attributes
    private int mGuidelines;

    // Whether the Crop View has been initialized for the first time
    private boolean initializedCropWindow = false;

    // Instance variables for the corner values
    private float mCornerExtension;
    private float mCornerOffset;
    private float mCornerLength;
    private float mCornerRadius;

    //drawer class
    private Drawer drawer = new NewDrawer();

    public CropOverlayViewNew(Context context){
        super(context);
        init(context);
    }

    public CropOverlayViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        initCropWindow(mBitmapRect);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        drawer.drawBackground(canvas, mBitmapRect, mBackgroundPaint);
        drawer.drawRect(canvas, mBorderPaint);
        drawer.drawCorner(canvas, mCornerRadius, mCornerCirclePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // If this View is not enabled, don't allow for touch interactions.
        if (!isEnabled()) {
            return false;
        }

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                onActionDown(event.getX(), event.getY());
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                onActionUp();
                return true;

            case MotionEvent.ACTION_MOVE:
                onActionMove(event.getX(), event.getY());
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;

            default:
                return false;
        }
    }

    public void setBitmapRect(Rect bitmapRect) {
        mBitmapRect = bitmapRect;
        initCropWindow(mBitmapRect);
    }

    public void setSelectionRect(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight){
        setPointToEdge(EdgeNew.TOP_LEFT, topLeft);
        setPointToEdge(EdgeNew.TOP_RIGHT, topRight);
        setPointToEdge(EdgeNew.BOTTOM_LEFT, bottomLeft);
        setPointToEdge(EdgeNew.BOTTOM_RIGHT, bottomRight);
    }

    private void setPointToEdge(EdgeNew edge, Point point){
        edge.setxCoordinate(point.x);
        edge.setyCoordinate(point.y);
    }

    public void resetCropOverlayView() {
        if (initializedCropWindow) {
            initCropWindow(mBitmapRect);
            invalidate();
        }
    }

    private void initCropWindow(Rect bitmapRect) {
        if (initializedCropWindow == false)
            initializedCropWindow = true;

        // Initialize crop window to have 10% padding w/ respect to image.
        final float horizontalPadding = 0.1f * bitmapRect.width();
        final float verticalPadding = 0.1f * bitmapRect.height();

        float top = bitmapRect.top + verticalPadding;
        float left = bitmapRect.left + horizontalPadding;
        float bottom = bitmapRect.bottom - verticalPadding;
        float right = bitmapRect.right - horizontalPadding;

        EdgeNew.TOP_LEFT.setxCoordinate(left);
        EdgeNew.TOP_LEFT.setyCoordinate(top);
        EdgeNew.BOTTOM_LEFT.setxCoordinate(left);
        EdgeNew.BOTTOM_LEFT.setyCoordinate(bottom);
        EdgeNew.TOP_RIGHT.setxCoordinate(right);
        EdgeNew.TOP_RIGHT.setyCoordinate(top);
        EdgeNew.BOTTOM_RIGHT.setxCoordinate(right);
        EdgeNew.BOTTOM_RIGHT.setyCoordinate(bottom);

    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        mHandleRadius = HandleUtil.getTargetRadius(context);

        mSnapRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                SNAP_RADIUS_DP,
                displayMetrics);

        mBorderPaint = PaintUtil.newBorderPaint(context);
        mBackgroundPaint = PaintUtil.newBackgroundPaint(context);
        mCornerCirclePaint = PaintUtil.newCornerRadiusPaint(context);

        // Sets the values for the corner sizes
        mCornerOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_OFFSET_DP,
                displayMetrics);
        mCornerExtension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_EXTENSION_DP,
                displayMetrics);
        mCornerLength = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_LENGTH_DP,
                displayMetrics);
        mCornerRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                DEFAULT_CORNER_RADIUS, displayMetrics);

        // Sets guidelines to default until specified otherwise
        mGuidelines = CropImageView.DEFAULT_GUIDELINES;
    }

    private void onActionDown(float x, float y){
        mPressedHandle = HandleUtilNew.getPressedHandle(x, y, mHandleRadius);

        if(mPressedHandle == null){
            return;
        }

        mTouchOffset = HandleUtilNew.getOffset(mPressedHandle, x, y);

        invalidate();
    }

    private void onActionUp(){
        if(mPressedHandle == null){
            return;
        }
        mPressedHandle = null;
        invalidate();
    }

    private void onActionMove(float x, float y){
        if(mPressedHandle == null){
            return;
        }
        x += mTouchOffset.first;
        y += mTouchOffset.second;

        // Calculate the new crop window size/position.
        if(mPressedHandle.isMoveLegal(x,y)){
            mPressedHandle.updateCropWindow(x, y, mBitmapRect);
        }

        invalidate();
    }

}
