package com.example.atulc.custombottomnavigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.atulc.custombottomnavigation.BitmapUtils.BitmapCache;
import com.example.atulc.custombottomnavigation.BitmapUtils.BitmapFilters;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera_fragement extends Fragment implements GestureDetector.OnGestureListener {

    View view;
    CameraView camera;
    String path;
    public ImageView characterImage;
    ViewPager viewPager;
    viewPagerAdapter viewPagerAdapter;
    Bitmap bitmapCamera;
    private int mTouchX;
    private boolean mIsFlingFired;
    private ScreenOrientation mCurrentOrientation;
    private GestureDetector mGestureDetector;
    private ScrollDirection mCurrentScrollDirection;

    private Bitmap mCurrentBitmap;
    private Bitmap mPreviousBitmap;
    private Bitmap mNextBitmap;
    private Bitmap mResultBitmap;
    private BitmapFilters.Filters mCurrentFilter;
    private BitmapFilters.Filters mPreviousFilter;
    private BitmapFilters.Filters mNextFilter;
    private Canvas mImageCanvas;
    private BitmapCache mBitmapCache;



    public static final int DEGREE_ZERO = 0;
    public static final int DEGREE_FORTY_FIVE = 45;
    public static final int DEGREE_NINETY = 90;
    public static final int DEGREE_ONE_THIRTY_FIVE = 135;
    public static final int DEGREE_ONE_EIGHTY = 180;
    public static final int DEGREE_TWO_TWENTY_FIVE = 225;
    public static final int DEGREE_TWO_SEVENTY = 270;
    public static final int DEGREE_THREE_ONE_FIVE = 315;
    public static final int DEGREE_THREE_SIXTY = 360;


    static {
        System.loadLibrary("NativeImageProcessor");
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.camera_fragement, container, false);
        final View nav_layout = getActivity().findViewById(R.id.nav_layout);
        final ImageView clickButton = (ImageView) view.findViewById(R.id.click);
        final ImageView nextArrow = (ImageView) view.findViewById(R.id.next_arrow_white);
        final ImageView backArrow = (ImageView) view.findViewById(R.id.back_arrow_white);
        characterImage = (ImageView) view.findViewById(R.id.character_image);
        camera = (CameraView) view.findViewById(R.id.camera);
        camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM); // Pinch to zoom!
        camera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER); // Tap to focus!
        camera.mapGesture(Gesture.LONG_TAP, GestureAction.CAPTURE); // Long tap to shoot!
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
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
            public void onPictureTaken(byte[] data) {
                super.onPictureTaken(data);

                int size= data.length;
                Log.e("atulLOg", "onPictureTaken: "+size );
                bitmapCamera = BitmapFactory.decodeByteArray(data, 0, data.length);
                characterImage.post(new Runnable() {
                    @Override
                    public void run() {

                        characterImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapCamera, characterImage.getWidth(), characterImage.getHeight(), false));
                        //characterImage.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.back_pager1));
                        //viewPager = (ViewPager) view.findViewById(R.id.viewPager);
                        //viewPagerAdapter = new viewPagerAdapter(getActivity().getApplicationContext());
                        //viewPager.setAdapter(viewPagerAdapter);

                    }
                });
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

                characterImage.setVisibility(View.INVISIBLE);
                camera.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.INVISIBLE);
                clickButton.setVisibility(View.VISIBLE);
                clickButton.setEnabled(true);
                backArrow.setVisibility(View.GONE);
                nextArrow.setVisibility(View.GONE);
                animateBottonNav(getActivity(), false);
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

    //hiding the bottom layout or navigation layout
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

    //method for storing images
    public void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("TAG", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
    }

    //method for retreving the file stored as image
    public File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");

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
        String mImageName = "img-" + timeStamp + ".jpg";
        String path = mediaStorageDir.getPath() + File.separator + mImageName;
        File mediaFile = new File(path);
        return mediaFile;
    }

    //compression of images
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


    @Override
    public boolean onDown(MotionEvent e) {
        if (mIsFlingFired) {
            mIsFlingFired = false;
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mCurrentScrollDirection.ordinal() == ScrollDirection.NONE.ordinal()) {
            if (distanceX > 0) {
                mCurrentScrollDirection = ScrollDirection.LEFT;
            } else {
                mCurrentScrollDirection = ScrollDirection.RIGHT;
            }
        }
        mTouchX = (int) e2.getX();
        overlayBitmaps(mTouchX);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        switch (mCurrentScrollDirection) {
            case LEFT: {
                overlayNextBitmap(mCurrentBitmap.getWidth());
                characterImage.setImageDrawable(new BitmapDrawable(getResources(), mNextBitmap));
                shuffleBitmap(true);
                break;
            }
            case RIGHT: {
                //width can't be 0
                overlayPreviousBitmap(1);
                characterImage.setImageDrawable(new BitmapDrawable(getResources(), mPreviousBitmap));
                shuffleBitmap(false);
                break;
            }
        }
        mIsFlingFired = true;
        mCurrentScrollDirection = ScrollDirection.NONE;

        return false;
    }

    private void overlayBitmaps(int coordinateX) {

        switch (mCurrentScrollDirection) {
            case NONE: {
                //do nothing here
                break;
            }
            case LEFT: {
                overlayNextBitmap(coordinateX);
                break;
            }
            case RIGHT: {
                overlayPreviousBitmap(coordinateX);
                break;
            }
        }
    }


    private void overlayPreviousBitmap(int coordinateX) {

        int widthValue = mCurrentBitmap.getWidth() - coordinateX;

        //added width value check to avoid illegalArgumentException crash
        if (widthValue > 0) {
            mImageCanvas.save();

            Bitmap OSBitmap = Bitmap.createBitmap(mCurrentBitmap, coordinateX, 0, widthValue, mCurrentBitmap.getHeight());
            mImageCanvas.drawBitmap(OSBitmap, coordinateX, 0, null);

            Bitmap FSBitmap = Bitmap.createBitmap(mPreviousBitmap, 0, 0, coordinateX, mCurrentBitmap.getHeight());
            mImageCanvas.drawBitmap(FSBitmap, 0, 0, null);

            mImageCanvas.restore();

            characterImage.setImageDrawable(new BitmapDrawable(getResources(), mResultBitmap));
        }
    }

    private void overlayNextBitmap(int coordinateX) {
        int widthValue = mCurrentBitmap.getWidth() - coordinateX;

        //added width value check to avoid illegalArgumentException crash
        if (widthValue > 0) {
            mImageCanvas.save();

            Bitmap OSBitmap = Bitmap.createBitmap(mCurrentBitmap, 0, 0, coordinateX, mCurrentBitmap.getHeight());
            mImageCanvas.drawBitmap(OSBitmap, 0, 0, null);

            Bitmap FSBitmap = Bitmap.createBitmap(mNextBitmap, coordinateX, 0, mCurrentBitmap.getWidth() - coordinateX, mCurrentBitmap.getHeight());
            mImageCanvas.drawBitmap(FSBitmap, coordinateX, 0, null);

            mImageCanvas.restore();

            characterImage.setImageDrawable(new BitmapDrawable(getResources(), mResultBitmap));
        }
    }

    private void shuffleBitmap(boolean isSwipeLeft) {
        if (isSwipeLeft) {
            mPreviousBitmap = mCurrentBitmap;
            mPreviousFilter = mCurrentFilter;
            mCurrentBitmap = mNextBitmap;
            mCurrentFilter = mNextFilter;
            mNextFilter = mCurrentFilter.next();
            Log.d("LOG_TAG", "next filter name " + mNextFilter.name());
            //note next bitmap can be null if not found in cache
            mNextBitmap = mBitmapCache.getBitmapCache(mNextFilter.name());
            if (mNextBitmap != null) {
                Log.d("LOG_TAG", "next filter index " + mNextFilter.ordinal() + " " + mNextFilter.name());
            } else {
                Log.d("LOG_TAG", "next bitmap IS NULL");
            }
        } else {
            mNextBitmap = mCurrentBitmap;
            mNextFilter = mCurrentFilter;
            mCurrentFilter = mPreviousFilter;
            mCurrentBitmap = mPreviousBitmap;
            mPreviousFilter = mCurrentFilter.previous();
            Log.d("LOG_TAG", "previous filter name " + mPreviousFilter.name());
            //note previous bitmap can be null if not found in cache
            mPreviousBitmap = mBitmapCache.getBitmapCache(mPreviousFilter.name());
            if (mPreviousBitmap != null) {
                Log.d("LOG_TAG", "previous filter index " + mPreviousFilter.ordinal() + " " + mPreviousFilter.name());
            } else {
                Log.d("LOG_TAG", "previous bitmap IS NULL");
            }
        }
    }



    public enum ScreenOrientation {
        PORTRAIT,
        LEFT_LANDSCAPE,
        PORTRAIT_UPSIDE_DOWN,
        RIGHT_LANDSCAPE;
    }

    public enum ScrollDirection {
        NONE, LEFT, RIGHT
    }

    public static ScreenOrientation getOrientation(int orientationValue) {
        if ((DEGREE_ZERO <= orientationValue && orientationValue < DEGREE_FORTY_FIVE) ||
                (orientationValue >= DEGREE_THREE_ONE_FIVE && orientationValue < DEGREE_THREE_SIXTY)) {
            return ScreenOrientation.PORTRAIT;
        } else if ((orientationValue >= DEGREE_FORTY_FIVE && orientationValue < DEGREE_ONE_THIRTY_FIVE)) {
            return ScreenOrientation.RIGHT_LANDSCAPE;
        } else if ((orientationValue >= DEGREE_ONE_THIRTY_FIVE && orientationValue < DEGREE_TWO_TWENTY_FIVE)) {
            return ScreenOrientation.PORTRAIT_UPSIDE_DOWN;
        } else if ((orientationValue >= DEGREE_TWO_TWENTY_FIVE && orientationValue < DEGREE_THREE_ONE_FIVE)) {
            return ScreenOrientation.LEFT_LANDSCAPE;
        }
        return null;
    }
}



