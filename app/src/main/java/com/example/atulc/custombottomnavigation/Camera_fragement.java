package com.example.atulc.custombottomnavigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.isaacudy.kfilter.KfilterView;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraUtils;
import com.otaliastudios.cameraview.CameraView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera_fragement extends Fragment {

    View view;
    CameraView camera;
    Bitmap bitmapyo;
    String path;
    KfilterView characterImage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.camera_fragement, container, false);
        final View nav_layout = getActivity().findViewById(R.id.nav_layout);
        final ImageView clickButton = (ImageView) view.findViewById(R.id.click);
        final ImageView nextArrow = (ImageView) view.findViewById(R.id.next_arrow_white);
        final ImageView backArrow = (ImageView) view.findViewById(R.id.back_arrow_white);
        characterImage = (KfilterView) view.findViewById(R.id.character_image);
        camera = (CameraView) view.findViewById(R.id.camera);
        camera.setLifecycleOwner(this);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camera.capturePicture();

            }
        });


        camera.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                super.onCameraOpened(options);
            }

            @Override
            public void onPictureTaken(final byte[] data) {
                super.onPictureTaken(data);

                bitmapyo = BitmapFactory.decodeByteArray(data, 0, data.length);
                Thread thread = new Thread(){
                    @Override
                    public void run() {

                        storeImage(getResizedBitmap(bitmapyo,1000));

                    }
                };
                //bitmapyo = bitmapyo.copy( Bitmap.Config.ARGB_8888 , true);


                //Glide.with(getActivity()).load(getBitmapBytes(outputImage)).into(characterImage);
                animateBottonNav(getActivity(), true);
                characterImage.setVisibility(View.VISIBLE);
                camera.setVisibility(View.GONE);
                clickButton.setVisibility(View.GONE);
                backArrow.setVisibility(View.VISIBLE);
                nextArrow.setVisibility(View.VISIBLE);

            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //characterImage.setImageBitmap(null);
                characterImage.setVisibility(View.INVISIBLE);
                camera.setVisibility(View.VISIBLE);
                clickButton.setVisibility(View.VISIBLE);
                backArrow.setVisibility(View.GONE);
                nextArrow.setVisibility(View.GONE);
                nav_layout.setVisibility(View.VISIBLE);
                nav_layout.animate().translationY(0);
                //nav_layout.setVisibility(View.VISIBLE);
                //slideUp(nav_layout);


            }
        });

        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), ShareImageActivtiy.class);
                startActivity(intent);

            }
        });


        return view;
    }

    //compression of image in this activtiy
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void animateBottonNav(final FragmentActivity mActivity, final Boolean isHide) {
        Log.e("SahajLOG99", "animate");
        if (mActivity != null)
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final LinearLayout bottomNav = (LinearLayout) mActivity.findViewById(R.id.nav_layout);
                    bottomNav.animate().translationY(isHide ? bottomNav.getHeight() : 0).setDuration(200).start();
                }
            });

    }

    //function to convert bitmap into byteArray
    private byte[] getBitmapBytes(Bitmap bitmap) {
        int chunkNumbers = 10;
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        byte[] imageBytes = new byte[bitmapSize];
        int rows, cols;
        int chunkHeight, chunkWidth;
        rows = cols = (int) Math.sqrt(chunkNumbers);
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        int yCoord = 0;
        int bitmapsSizes = 0;

        for (int x = 0; x < rows; x++) {
            int xCoord = 0;
            for (int y = 0; y < cols; y++) {
                Bitmap bitmapChunk = Bitmap.createBitmap(bitmap, xCoord, yCoord, chunkWidth, chunkHeight);
                byte[] bitmapArray = getBytesFromBitmapChunk(bitmapChunk);
                System.arraycopy(bitmapArray, 0, imageBytes, bitmapsSizes, bitmapArray.length);
                bitmapsSizes = bitmapsSizes + bitmapArray.length;
                xCoord += chunkWidth;

                bitmapChunk.recycle();
                bitmapChunk = null;
            }
            yCoord += chunkHeight;
        }

        return imageBytes;
    }


    private byte[] getBytesFromBitmapChunk(Bitmap bitmap) {
        int bitmapSize = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmapSize);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
    }


    //method for storing images
    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.WEBP, 50, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
    }

    //method for retreving the file stored as image
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");
        Log.e("atulLog",">>>"+mediaStorageDir);

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String mImageName = "img-" + timeStamp;
        path = mediaStorageDir.getPath() + File.separator + mImageName;
        File mediaFile = new File(path);
        //characterImage.setContentPath(path);
        return mediaFile;
    }


}



