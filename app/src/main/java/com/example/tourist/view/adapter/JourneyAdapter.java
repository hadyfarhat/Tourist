package com.example.tourist.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourist.R;
import com.example.tourist.model.JourneyWithMoments;
import com.example.tourist.model.Moment;
import com.example.tourist.view.JourneyDetailView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Represents the adapter class for managing Journeys
 */
public class JourneyAdapter extends RecyclerView.Adapter<JourneyAdapter.View_Holder> {

    private Context context;
    private static List<JourneyWithMoments> mJourneys;

    public class View_Holder extends RecyclerView.ViewHolder {
        ImageView journeyImage;
        TextView journeyTitle;

        public View_Holder(View itemView) {
            super(itemView);
            journeyImage = (ImageView) itemView.findViewById(R.id.journey_image);
            journeyTitle = (TextView) itemView.findViewById(R.id.journey_title);
        }
    }


    public JourneyAdapter(Context context) {
        super();
        this.context = context;
    }


    /**
     * returns the current list of journeys
     * @return
     */
    public static List<JourneyWithMoments> getJourneys() {
        return mJourneys;
    }


    /**
     * Creates an inflater which inflates the view with a view item.
     * Creates a view holder based on the inflater and returns it.
     * The view item is the layout file which will hold a single journey item
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.journey_item, parent, false);
        View_Holder holder = new View_Holder(view);
        return holder;
    }


    /**
     * Binds the view holder with the required view items. It also sets an on click listener for a
     * journey image to start the Journey activity for that journey.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {
        JourneyWithMoments journey = mJourneys.get(position);
        if (journey != null) {
            /*
            if the journey has any moment, it takes the first moment and sets the journey image
            to the image of that moment. If it doesn't have a moment, it uses a default image from
            the drawable folder.
             */
            if (journey.moments.size() > 0 && journey.moments.get(0) != null) {
                Moment moment = journey.moments.get(0);
                if (moment.imageFilePathIsInt()) {
                    holder.journeyImage.setImageResource(moment.getImageFilePathInt());
                } else if (moment.imageFilePathIsString()) {
                    new UploadSingleImageTask().execute(new HolderAndPosition(position, holder));
                }
            } else {
                holder.journeyImage.setImageResource(R.drawable.joe1);
            }

            // Set the title
            if (journey.journey.getTitle() != null) {
                holder.journeyTitle.setText(journey.journey.getTitle());
            } else {
                holder.journeyTitle.setText("This is a title");
            }

            // Intent for Journey Detail View
            holder.journeyImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, JourneyDetailView.class);
                        intent.putExtra("journeyId", journey.journey.getId());
                        context.startActivity(intent);
                    }
            });
        }
    }


    /**
     * @return size of the JourneyWithMoments list
     */
    @Override
    public int getItemCount() {
        if (mJourneys != null)
            return mJourneys.size();

        return 0;
    }

    /**
     * Sets the member moments list to the passed parameter
     * @param - journey with moments
     */
    public void setJourneys(List<JourneyWithMoments> journeys) {
        mJourneys = journeys;
        notifyDataSetChanged();
    }


    /**
     * Creates a bitmap based on the file provided.
     * It then uses this bitmap to set the image of the view holder's image item
     */
    // TODO: 23/12/2020 Save bitmap into a file for better efficiency
    private class UploadSingleImageTask extends AsyncTask<JourneyAdapter.HolderAndPosition, Void, Bitmap> {
        private WeakReference<JourneyAdapter.HolderAndPosition> holderAndPositionReference;

        @Override
        protected Bitmap doInBackground(JourneyAdapter.HolderAndPosition... holderAndPosition) {
            holderAndPositionReference = new WeakReference<>(holderAndPosition[0]);
            JourneyAdapter.HolderAndPosition holdAndPos = holderAndPositionReference.get();
            Bitmap bitmap = AdapterHelper.decodeSampledBitmapFromResource(
                    mJourneys.get(holdAndPos.position).moments.get(0).getImageFilePath(),
                    R.dimen.journey_image_width, R.dimen.journey_image_height);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            JourneyAdapter.HolderAndPosition holdAndPos = holderAndPositionReference.get();
            holdAndPos.holder.journeyImage.setImageBitmap(bitmap);
        }
    }


    private class HolderAndPosition {
        int position;
        JourneyAdapter.View_Holder holder;

        public HolderAndPosition(int position, JourneyAdapter.View_Holder holder) {
            this.position = position;
            this.holder = holder;
        }
    }

}
