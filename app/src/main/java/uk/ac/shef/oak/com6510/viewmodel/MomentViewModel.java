package uk.ac.shef.oak.com6510.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import uk.ac.shef.oak.com6510.model.Moment;
import uk.ac.shef.oak.com6510.model.MomentRepository;

import java.util.List;

/**
 * Represents the View Model of the Moment entity
 */
public class MomentViewModel extends AndroidViewModel {
    Application application;
    private MomentRepository mRepository;
    private LiveData<List<Moment>> mAllMoments;

    /**
     * Constructor of the class which initialises the Moment repository
     *
     * @param application the application state
     */
    public MomentViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void setRepository(int journeyId) {
        mRepository = new MomentRepository(this.application, journeyId);
        mAllMoments = mRepository.getAllMoments();
    }


    public LiveData<List<Moment>> getAllMoments() {
        return this.mAllMoments;
    }

    public void insert(Moment moment) {
        this.mRepository.insert(moment);
    }
}
