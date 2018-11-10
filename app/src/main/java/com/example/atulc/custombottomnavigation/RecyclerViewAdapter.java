package com.example.atulc.custombottomnavigation;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolde> {
    Context context;
    private ArrayList<String> imageData = new ArrayList<String>();


    public RecyclerViewAdapter(ArrayList<String> imageData, FragmentActivity activity) {
        this.imageData = imageData;
        this.context = activity;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item_for_gallery, parent, false);

        return new MyViewHolde(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolde holder, int position) {
        String data = imageData.get(position);

        if (data != null){
            Glide.with(context).load(data).into(holder.singleImageView);

        }else {
            Toast.makeText(context, "Images Empty", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return imageData.size();
    }

    public class MyViewHolde extends RecyclerView.ViewHolder {
        RoundedImageView singleImageView;

        public MyViewHolde(View itemView) {
            super(itemView);
            singleImageView = (RoundedImageView) itemView.findViewById(R.id.ImageView);
        }
    }
}