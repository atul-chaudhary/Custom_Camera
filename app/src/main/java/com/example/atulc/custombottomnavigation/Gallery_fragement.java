package com.example.atulc.custombottomnavigation;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import java.util.ArrayList;

public class Gallery_fragement extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    ArrayList<String> imageData;
    RecyclerViewAdapter adapter;
    BottomSheetBehavior sheetBehavior;
    View view;

    public Gallery_fragement() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.gallery_fargement, container, false);
        context = getActivity();

        ImageView image_from_recyclerView = (ImageView)view.findViewById(R.id.image_from_recyclerView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        ImageView arrow_white_button = (ImageView)view.findViewById(R.id.arrow_white);
        ImageView up_button_white = (ImageView)view.findViewById(R.id.up_button_arrow);



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 4);
        recyclerView.setLayoutManager(layoutManager);

        MyTask myTask = new MyTask();
        myTask.execute();

       // sheetBehavior = BottomSheetBehavior.from();

        return view;
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            imageData = getAllShownImagesPath(getActivity());
            Log.e("imageData: ", String.valueOf(imageData.size()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new RecyclerViewAdapter(imageData, getActivity());
            recyclerView.setAdapter(adapter);

        }

    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }
}