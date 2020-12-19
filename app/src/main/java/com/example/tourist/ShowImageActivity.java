package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

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
                ImageElement imageElement = GalleryAdapter.getItems().get(position);
                if (imageElement.image != -1) {
                    imageView.setImageResource(imageElement.image);
                } else if (imageElement.file != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imageElement.file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}