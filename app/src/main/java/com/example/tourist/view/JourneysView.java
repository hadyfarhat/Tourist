package com.example.tourist.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.tourist.R;
import com.example.tourist.model.JourneyWithMoments;
import com.example.tourist.model.Moment;
import com.example.tourist.view.adapter.JourneyAdapter;
import com.example.tourist.viewmodel.JourneyViewModel;

import java.util.List;

public class JourneysView extends AppCompatActivity {
    private JourneyAdapter mAdapter;
    private Activity activity;
    private JourneyViewModel mJourneyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.journeys_view);
        activity = this;

        mJourneyViewModel = createJourneyViewModel();

        RecyclerView mRecyclerView = initialiseRecyclerViewWithGridLayoutManager(
                R.id.journeys, true, 3);
        mAdapter = new JourneyAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mJourneyViewModel.getAllJourneys().observe(this, createJourneyWithMomentsObserver());
    }


    /**
     * Creates a Journey View Model
     */
    private JourneyViewModel createJourneyViewModel() {
        return ViewModelProviders.of(this).get(JourneyViewModel.class);
    }


    /**
     * Create an observer for the Journey View Model
     *
     * @return
     */
    private Observer<List<JourneyWithMoments>> createJourneyWithMomentsObserver() {
        return new Observer<List<JourneyWithMoments>>() {
            @Override
            public void onChanged(@Nullable final List<JourneyWithMoments> journeys) {
                mAdapter.setJourneys(journeys);
            }
        };
    }


    /**
     * Creates a recycler view and assigns it the passed view id. Recycler view will use the
     * Grid Layout manager.
     *
     * @param viewId                   - id of the view to be assigned to the recycler view
     * @param hasFixedSize             - determines whether the recycler view's will be affected by
     *                                 the adapter contents
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
}