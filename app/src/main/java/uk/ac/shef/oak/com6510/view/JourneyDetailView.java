package uk.ac.shef.oak.com6510.view;

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

import uk.ac.shef.oak.com6510.R;
import uk.ac.shef.oak.com6510.model.Moment;
import uk.ac.shef.oak.com6510.view.adapter.MomentAdapter;
import uk.ac.shef.oak.com6510.viewmodel.MomentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class JourneyDetailView extends AppCompatActivity {
    private MomentAdapter mAdapter;
    private Activity activity;
    private MomentViewModel mMomentViewModel;
    private int journeyId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journey_detail_view);
        activity = this;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) { journeyId = bundle.getInt("journeyId"); }

        mMomentViewModel = createMomentViewModel(journeyId);

        RecyclerView mRecyclerView = initialiseRecyclerViewWithGridLayoutManager(
                R.id.images, true, 3);

        mAdapter = new MomentAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mMomentViewModel.getAllMoments().observe(this, createMomentObserver());

        initEasyImage();

        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        createFabGalleryClickListener(fabGallery);

        FloatingActionButton fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        createFabCameraClickListener(fabCamera);
    }


    /**
     * Create an on click listener to the camera fab.
     * When clicked, it will open the camera using Easy Image.
     * @param fabCamera
     */
    private void createFabCameraClickListener(FloatingActionButton fabCamera) {
        // TODO: 23/12/2020 Fix camera dependencies
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);
            }
        });
    }


    /**
     * Create an on click listener to the gallery fab.
     * When clicked, it will open the phone's gallery using Easy Image
     * @param fabGallery
     */
    private void createFabGalleryClickListener(FloatingActionButton fabGallery) {
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });
    }


    /**
     * Creates a Moment View Model
     * @param journeyId - the id of the journey in which its moments will be displayed
     * @return
     */
    private MomentViewModel createMomentViewModel(int journeyId) {
        MomentViewModel momentViewModel =  ViewModelProviders.of(this).get(MomentViewModel.class);
        momentViewModel.setRepository(journeyId);
        return momentViewModel;
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
     * @param hasFixedSize - determines whether the recycler view's will be affected by
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
     * Convert the passed files into Moments and insert them into the Moment View Model
     * @param returnedPhotos
     */
    private void insertPhotosIntoMomentViewModel(List<File> returnedPhotos) {
        List<Moment> moments = createMomentsFromFiles(returnedPhotos);
        for (Moment moment : moments) {
            moment.setJourneyId(this.journeyId);
            mMomentViewModel.insert(moment);
        }
    }


    /**
     * Convert files into Moments
     * @param files - the files to be converted
     * @return
     */
    private List<Moment> createMomentsFromFiles(List<File> files) {
        List<Moment> moments = new ArrayList<>();
        Moment tmpMoment;
        for (File file: files){
            tmpMoment = new Moment();
            tmpMoment.setImageFilePath(file.getPath());
            moments.add(tmpMoment);
        }
        return moments;
    }


    /**
     * Inserts the selected photos from the gallery into the Moment View Model
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                insertPhotosIntoMomentViewModel(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }


    /**
     * Returns the current activity
     * @return
     */
    public Activity getActivity() {
        return activity;
    }
}