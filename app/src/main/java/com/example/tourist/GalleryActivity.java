package com.example.tourist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class GalleryActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyElement[] data = new MyElement[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mRecyclerView = (RecyclerView) findViewById(R.id.images);
//        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager((mLayoutManager));

        data[0] = new MyElement(R.drawable.joe1, "Hello", "Hello, there!");
        data[1] = new MyElement(R.drawable.joe1, "Hello", "Hello, there!");
        data[2] = new MyElement(R.drawable.joe1, "Hello", "Hello, there!");

        mAdapter = new GalleryAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
    }
}