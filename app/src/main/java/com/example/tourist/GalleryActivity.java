package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tourist.model.Moment;
import com.example.tourist.viewmodel.MomentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private GalleryAdapter mAdapter;
    private List<ImageElement> images = new ArrayList<>();
    private Activity activity;
    private MomentViewModel mMomentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        activity = this;
        mMomentViewModel = ViewModelProviders.of(this).get(MomentViewModel.class);

        // Initialise Recycler View and assign it a layout manager
        mRecyclerView = findViewById(R.id.images);
        mRecyclerView.setHasFixedSize(true);
        int numberOfColumnsInTheGrid = 3;
        mRecyclerView.setLayoutManager(
                new GridLayoutManager(this, numberOfColumnsInTheGrid));

        // Add photos to images array
        for (int i = 0; i < 15; ++i) {
            images.add(new ImageElement(R.drawable.joe1));
        }

        // Assign Adapter to Recycler View
        mAdapter = new GalleryAdapter(images);
        mRecyclerView.setAdapter(mAdapter);

        // Add observer for the Moment live data
        mMomentViewModel.getAllMoments().observe(this, new Observer<List<Moment>>() {
            @Override
            public void onChanged(List<Moment> moments) {
                for (Moment moment : moments) {
                    ImageElement element = new ImageElement(moment.getImageFilePathInt());
                    mAdapter.addImage(element);
                }
            }
        });

        initEasyImage();

        // get the images from the Gallery
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });

        // TODO: 23/12/2020 Fix camera dependencies
        // Get a photo from camera
        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
            }
        });
    }

    /**
     * Initialise Easy Image
     */
    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("EasyImage sample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    /**
     * add the selected images to the grid
     * @param returnedPhotos
     */
    private void onPhotosReturned(List<File> returnedPhotos) {
        images.addAll(getImageElements(returnedPhotos));
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    /**
     * given a list of photos, it creates a list of myElements
     * @param returnedPhotos
     * @return
             */
    private List<ImageElement> getImageElements(List<File> returnedPhotos) {
        List<ImageElement> imageElementList = new ArrayList<>();
        for (File file: returnedPhotos){
            ImageElement element = new ImageElement(file);
            imageElementList.add(element);
        }
        return imageElementList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                // Error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    public Activity getActivity() {
        return activity;
    }
}