package com.example.tourist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourist.R;
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
    private Activity activity;
    private MomentViewModel mMomentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        activity = this;

        mMomentViewModel = createMomentViewModel();

        mRecyclerView = initialiseRecyclerViewWithGridLayoutManager(
                R.id.images, true, 3);

        mAdapter = new GalleryAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mMomentViewModel.getAllMoments().observe(this, createMomentObserver());

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
     * Creates a Moment View Model
     * @return
     */
    private MomentViewModel createMomentViewModel() {
        return ViewModelProviders.of(this).get(MomentViewModel.class);
    }


    /**
     * Create an observer for the Moment View Model
     * @return
     */
    private Observer<List<Moment>> createMomentObserver() {
        return new Observer<List<Moment>>() {
            @Override
            public void onChanged(@Nullable final List<Moment> moments) {
                mAdapter.setMoments(moments);
            }
        };
    }


    /**
     * Creates a recycler view and assigns it the passed view id. Recycler view will use the
     * Grid Layout manager.
     * @param viewId - id of the view to be assigned to the recycler view
     * @param hasFixedSize - determines whether the recyler view's will be affected by
     *                     the adapter contents
     * @param numberOfColumnsInTheGrid - number of columns in grid
     * @return
     */
    private RecyclerView initialiseRecyclerViewWithGridLayoutManager(int viewId,
                                                                     boolean hasFixedSize,
                                                                     int numberOfColumnsInTheGrid) {
        RecyclerView recyclerView = findViewById(viewId);
        recyclerView.setHasFixedSize(hasFixedSize);
        recyclerView.setLayoutManager(
                new GridLayoutManager(this, numberOfColumnsInTheGrid));
        return recyclerView;
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
        List<Moment> moments = getMomentsFromReturnedPhotos(returnedPhotos);
        for (Moment moment : moments) {
            mMomentViewModel.insert(moment);
        }
    }

    /**
     * given a list of photos, it creates a list of Moments
     * @param returnedPhotos
     * @return
     */
    private List<Moment> getMomentsFromReturnedPhotos(List<File> returnedPhotos) {
        List<Moment> moments = new ArrayList<>();
        Moment tmpMoment;
        for (File file: returnedPhotos){
            tmpMoment = new Moment();
            tmpMoment.setImageFilePath(file.getPath());
            moments.add(tmpMoment);
        }
        return moments;
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