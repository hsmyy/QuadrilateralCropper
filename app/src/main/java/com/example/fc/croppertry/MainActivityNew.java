package com.example.fc.croppertry;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.example.fc.cropper.CropImageViewNew;
import com.example.fc.cropperexample.R;

/**
 * Created by fc on 15-3-11.
 */
public class MainActivityNew extends Activity{

    private static final int ROTATE_NINETY_DEGREES = 90;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_new);
        final CropImageViewNew cropImageView = (CropImageViewNew) findViewById(R.id.CropImageView);

        final Button resetButton = (Button) findViewById(R.id.Button_reset);
        resetButton.setOnClickListener(new View.OnClickListener(){

            private volatile int num = 0;

            @Override
            public void onClick(View v) {
                cropImageView.reset();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
