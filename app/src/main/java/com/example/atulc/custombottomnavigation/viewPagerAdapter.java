package com.example.atulc.custombottomnavigation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.LinkedList;
import java.util.List;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;
import static com.zomato.photofilters.SampleFilters.getAweStruckVibeFilter;
import static com.zomato.photofilters.SampleFilters.getBlueMessFilter;
import static com.zomato.photofilters.SampleFilters.getLimeStutterFilter;
import static com.zomato.photofilters.SampleFilters.getNightWhisperFilter;
import static com.zomato.photofilters.SampleFilters.getStarLitFilter;

public class viewPagerAdapter extends PagerAdapter {


    Context context;
    LayoutInflater inflater;
    Bitmap unFilteredImage;
    Bitmap outputImage;
    ImageView imgslide;
    int post;
    String filePath;

    public viewPagerAdapter(String filePath) {
        this.filePath = filePath;
        Log.e("atulLog", "viewPagerAdapter: >>" + filePath);
    }

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    public int[] lst_images = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4
    };


    public viewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slider_viewpager, container, false);
        Log.e("atulLog", "instantiateItem: inside adapter");
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        imgslide = (ImageView) view.findViewById(R.id.slideimg);

//        filteredImage = drawableToBitmap(context.getDrawable(R.drawable.back_pager));
//        filteredImage = filteredImage.copy(Bitmap.Config.ARGB_8888,true);
        //      imgslide.setImageBitmap(filterFunction(position));

//        imgslide.setImageResource(lst_images[position]);
//        imgslide.set

        Bitmap checkfilter=null;
        //checkfilter = checkfilter.copy(Bitmap.Config.ARGB_8888, true);
        unFilteredImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.back_pager);
        unFilteredImage = unFilteredImage.copy(Bitmap.Config.ARGB_8888, true);
        Filter fooFilter11 = SampleFilters.getBlueMessFilter();
        //Bitmap newBimap = overlay(unFilteredImage,fooFilter11.processFilter();
        if (position == 0) {

            Filter fooFilter1 = SampleFilters.getBlueMessFilter();
            Bitmap outputImage1 = fooFilter1.processFilter(unFilteredImage);
            imgslide.setImageBitmap(outputImage1);
            Log.e("atulLog", "filterFunction: 0");
        } else if (position == 1) {
            Filter fooFilter2 = SampleFilters.getBlueMessFilter();
            Bitmap outputImage2 = fooFilter2.processFilter(unFilteredImage);
            imgslide.setImageBitmap(outputImage2);
            Log.e("atulLog", "filterFunction: 1");
        } else if (position == 2) {
            Filter fooFilter3 = SampleFilters.getAweStruckVibeFilter();
            Bitmap outputImage3 = fooFilter3.processFilter(checkfilter);
            imgslide.setImageBitmap(outputImage3);
            Log.e("atulLog", "filterFunction: 2");
        } else if (position == 3) {
            Filter fooFilter4 = SampleFilters.getLimeStutterFilter();
            Bitmap outputImage4 = fooFilter4.processFilter(unFilteredImage);
            imgslide.setImageBitmap(outputImage4);
            Log.e("atulLog", "filterFunction: 3");
        } else if (position == 4) {
            Filter fooFilter5 = SampleFilters.getBlueMessFilter();
            Bitmap outputImage5 = fooFilter5.processFilter(checkfilter);
            imgslide.setImageBitmap(outputImage5);
            Log.e("atulLog", "filterFunction: 4");
        }
//        imgslide.setImageBitmap(unFilteredImage);
        //       Camera_fragement camera_fragement = new Camera_fragement();
        //     camera_fragement.characterImage.setImageBitmap(bitmap);


        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public Bitmap filterFunction(int position) {
        Log.e("atulLog", "filterFunction: lets see >>" + position);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
//        //bitmap = bitmap.copy(Bitmap.Config.ARGB_8888,true);
        //Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.back_pager1);
        //       Camera_fragement camera_fragement = new Camera_fragement();
        //      camera_fragement.characterImage.setImageBitmap(bitmap);
        if (position == 0) {
            Filter fooFilter1 = SampleFilters.getBlueMessFilter();
            outputImage = fooFilter1.processFilter(unFilteredImage);
            Log.e("atulLog", "filterFunction: 0");
        } else if (position == 1) {
            Filter fooFilter2 = SampleFilters.getBlueMessFilter();
            outputImage = fooFilter2.processFilter(unFilteredImage);
            Log.e("atulLog", "filterFunction: 1");
        } else if (position == 2) {
            Filter fooFilter3 = SampleFilters.getAweStruckVibeFilter();
            outputImage = fooFilter3.processFilter(unFilteredImage);
            Log.e("atulLog", "filterFunction: 2");
        } else if (position == 3) {
            Filter fooFilter4 = SampleFilters.getLimeStutterFilter();
            outputImage = fooFilter4.processFilter(unFilteredImage);
            Log.e("atulLog", "filterFunction: 3");
        } else if (position == 4) {
            Filter fooFilter5 = SampleFilters.getBlueMessFilter();
            outputImage = fooFilter5.processFilter(unFilteredImage);
            Log.e("atulLog", "filterFunction: 4");
        }

        return outputImage;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private Bitmap overlay(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }

    // Convert transparentColor to be transparent in a Bitmap.
//    public static Bitmap makeTransparent(Bitmap bit, Color transparentColor) {
//        int width =  bit.getWidth();
//        int height = bit.getHeight();
//        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        int [] allpixels = new int [ myBitmap.getHeight()*myBitmap.getWidth()];
//        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(),myBitmap.getHeight());
//        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);
//
//        for(int i =0; i<myBitmap.getHeight()*myBitmap.getWidth();i++){
//            if( allpixels[i] == transparentColor)
//
//                allpixels[i] = Color.alpha(Color.TRANSPARENT);
//        }
//
//        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
//        return myBitmap;
//    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage){

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, 0f, 0f, null);
        canvas.drawBitmap(secondImage, 10, 10, null);
        return result;
    }
}
