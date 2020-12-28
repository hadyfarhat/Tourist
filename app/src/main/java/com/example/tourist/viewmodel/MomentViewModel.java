package com.example.tourist.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tourist.model.Moment;
import com.example.tourist.model.MomentRepository;

import java.util.List;

public class MomentViewModel extends AndroidViewModel {
    private MomentRepository mRepository;
    private LiveData<List<Moment>> mAllMoments;

    public MomentViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MomentRepository(application);
        mAllMoments = mRepository.getAllMoments();
    }

    public LiveData<List<Moment>> getAllMoments() {
        return this.mAllMoments;
    }

    public void insert(Moment moment) {
        this.mRepository.insert(moment);
    }
}
