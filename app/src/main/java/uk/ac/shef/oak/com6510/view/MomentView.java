package uk.ac.shef.oak.com6510.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import uk.ac.shef.oak.com6510.R;

import uk.ac.shef.oak.com6510.model.Moment;
import uk.ac.shef.oak.com6510.view.adapter.MomentAdapter;

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
                Moment moment = MomentAdapter.getMoments().get(position);
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