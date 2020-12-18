package com.example.tourist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.View_Holder> {

    private Context context;
    private MyElement[] items;

    public class View_Holder extends RecyclerView.ViewHolder {
        TextView title;
        TextView preview;
        ImageView imageView;

        View_Holder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.image_title);
            preview = (TextView) view.findViewById(R.id.image_preview);
            imageView = (ImageView) view.findViewById(R.id.image);
        }
    }

    public GalleryAdapter(MyElement[] items) {
        this.items = items;
    }

    public GalleryAdapter(Context context, MyElement[] items) {
        super();
        this.items = items;
        this.context = context;
    }

    @Override
    public View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item,
                parent, false);
        View_Holder holder = new View_Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {
        if (holder != null && items[position] != null) {
            holder.title.setText(items[position].title);
            holder.preview.setText(items[position].preview);
            holder.imageView.setImageResource(items[position].image);
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }
}
