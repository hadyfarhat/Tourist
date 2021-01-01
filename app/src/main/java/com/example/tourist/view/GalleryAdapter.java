package com.example.tourist.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tourist.R;
import com.example.tourist.model.Moment;

import java.lang.ref.WeakReference;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.View_Holder> {

    private Context context;
    private static List<Moment> mMoments;


    public class View_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }


    public GalleryAdapter(Context context) {
        super();
        this.context = context;
    }


    /**
     * returns the current list of moments
     * @return
     */
    public static List<Moment> getMoments() {
        return mMoments;
    }


    /**
     * Creates an inflater which inflates the view with a view item.
     * Creates a view holder based on the inflater and returns it
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View v = inflater.inflate(R.layout.image_item, parent, false);
        View_Holder holder = new View_Holder(v);
        context = parent.getContext();
        return holder;
    }


    /**
     * Binds the view holder with the required view item.
     * The view item is of type ImageView
     * The image being bounded could be either an int or a file.
     *
     * A click listener will be set on the view holder which will be executed when an image
     * is clicked. The view holder will then start another activity for the image to be viewed.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        if (holder != null && mMoments.get(position) != null) {
            if (mMoments.get(position).imageFilePathIsInt()) {
                holder.imageView.setImageResource(mMoments.get(position).getImageFilePathInt());
            } else if (mMoments.get(position).imageFilePathIsString()) {
                new UploadSingleImageTask().execute(new HolderAndPosition(position, holder));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MomentView.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }


    /**
     * Get count of moments array
     * @return
     */
    @Override
    public int getItemCount() {
        if (mMoments != null ) {
            return mMoments.size();
        }
        return 0;
    }


    /**
     * Sets the member moments array to the passed parameter
     * @param moments
     */
    public void setMoments(List<Moment> moments) {
        mMoments = moments;
        notifyDataSetChanged();
    }


    public static Bitmap decodeSampledBitmapFromResource(String filePath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


    /**
     * Creates a bitmap based on the file provided.
     * It then uses this bitmap to set the image of the view holder's image item
     */
    // TODO: 23/12/2020 Save bitmap into a file for better efficiency
    private class UploadSingleImageTask extends AsyncTask<HolderAndPosition, Void, Bitmap> {
        private WeakReference<HolderAndPosition>  holderAndPositionReference;

        @Override
        protected Bitmap doInBackground(HolderAndPosition... holderAndPosition) {
            holderAndPositionReference = new WeakReference<>(holderAndPosition[0]);
            HolderAndPosition holdAndPos = holderAndPositionReference.get();
            Bitmap bitmap = decodeSampledBitmapFromResource(
                    mMoments.get(holdAndPos.position).getImageFilePath(), 230, 270);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            HolderAndPosition holdAndPos = holderAndPositionReference.get();
            holdAndPos.holder.imageView.setImageBitmap(bitmap);
        }
    }


    private class HolderAndPosition {
        int position;
        View_Holder holder;

        public HolderAndPosition(int position, View_Holder holder) {
            this.position = position;
            this.holder = holder;
        }
    }
}
