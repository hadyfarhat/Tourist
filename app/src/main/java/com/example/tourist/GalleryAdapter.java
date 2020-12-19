package com.example.tourist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.View_Holder> {

    private Context context;
    private static List<ImageElement> items;

    public class View_Holder extends RecyclerView.ViewHolder {
        ImageView imageView;

        View_Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public GalleryAdapter(List<ImageElement> items) {
        this.items = items;
    }

    public GalleryAdapter(Context context, List<ImageElement> items) {
        super();
        this.items = items;
        this.context = context;
    }

    public static List<ImageElement> getItems() {
        return items;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,
                parent, false);
        View_Holder holder = new View_Holder(v);
        context = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        if (holder != null && items.get(position) != null) {
            if (items.get(position).image != -1) {
                holder.imageView.setImageResource(items.get(position).image);
            } else if (items.get(position).file != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(items.get(position).file.getAbsolutePath());
                holder.imageView.setImageBitmap(bitmap);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowImageActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
