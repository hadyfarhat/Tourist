package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourist.model.Moment;

public class ShowImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        Bundle b = getIntent().getExtras();
        int position = -1;
        if (b != null) {
            position = b.getInt("position");
            if (position != -1) {
                ImageView imageView = findViewById(R.id.image_detail);
                Moment moment = GalleryAdapter.getMoments().get(position);
                if (moment.imageFilePathIsInt()) {
                    imageView.setImageResource(moment.getImageFilePathInt());
                } else if (moment.imageFilePathIsString()) {
                    String imagePath = moment.getImageFilePath();
                    Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}