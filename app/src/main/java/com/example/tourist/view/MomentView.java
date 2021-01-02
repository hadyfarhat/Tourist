package com.example.tourist.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tourist.R;
import com.example.tourist.view.adapter.GalleryAdapter;

public class MomentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moment_view);

        Bundle b = getIntent().getExtras();
        int position = -1;
        if (b != null) {
            position = b.getInt("position");
            if (position != -1) {
                ImageView imageView = findViewById(R.id.moment_image);
                com.example.tourist.model.Moment moment = GalleryAdapter.getMoments().get(position);
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