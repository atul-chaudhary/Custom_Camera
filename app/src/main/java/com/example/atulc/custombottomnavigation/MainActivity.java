package com.example.atulc.custombottomnavigation;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout nav_layout;
    LinearLayout gallery_nav_button;
    LinearLayout camera_nav_button;
    LinearLayout text_nav_button;
    Fragment fragment = null;
    ImageView photoIcon;
    ImageView line_1;
    ImageView cameraIcon;
    ImageView line_2;
    ImageView textIcon;
    ImageView line_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gallery_nav_button = (LinearLayout) findViewById(R.id.gallery_nav_button);
        camera_nav_button = (LinearLayout) findViewById(R.id.camera_nav_button);
        text_nav_button = (LinearLayout) findViewById(R.id.text_nav_button);
        nav_layout = (LinearLayout) findViewById(R.id.nav_layout);
        photoIcon = (ImageView) findViewById(R.id.photo_icon);
        line_1 = (ImageView) findViewById(R.id.line_1);
        cameraIcon = (ImageView) findViewById(R.id.camera_icon);
        line_2 = (ImageView) findViewById(R.id.line_2);
        textIcon = (ImageView) findViewById(R.id.text_icon);
        line_3 = (ImageView) findViewById(R.id.line_3);


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new Gallery_fragement()).commit();

        photoIcon.setImageResource(R.drawable.gallery_nav);
        line_1.setVisibility(View.VISIBLE);

        cameraIcon.setImageResource(R.drawable.camera_nav_faded);
        line_2.setVisibility(View.GONE);

        textIcon.setImageResource(R.drawable.text_nav_faded);
        line_3.setVisibility(View.GONE);

        gallery_nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNavigationIcons(v);
                fragment = new Gallery_fragement();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

            }
        });

        camera_nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNavigationIcons(v);
                fragment = new Camera_fragement();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

            }
        });

        text_nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeNavigationIcons(v);
                fragment = new Text_fragement();
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

            }
        });


    }

    public void ChangeNavigationIcons(View view) {

        int resId = view.getId();

        switch (resId) {

            case R.id.gallery_nav_button:

                photoIcon.setImageResource(R.drawable.gallery_nav);
                line_1.setVisibility(View.VISIBLE);

                cameraIcon.setImageResource(R.drawable.camera_nav_faded);
                line_2.setVisibility(View.GONE);

                textIcon.setImageResource(R.drawable.text_nav_faded);
                line_3.setVisibility(View.GONE);

                gallery_nav_button.setEnabled(false);
                camera_nav_button.setEnabled(true);
                text_nav_button.setEnabled(true);

                break;

            case R.id.camera_nav_button:

                photoIcon.setImageResource(R.drawable.gallery_nav_faded);
                line_1.setVisibility(View.GONE);

                cameraIcon.setImageResource(R.drawable.camera_nav);
                line_2.setVisibility(View.VISIBLE);

                textIcon.setImageResource(R.drawable.text_nav_faded);
                line_3.setVisibility(View.GONE);

                camera_nav_button.setEnabled(false);
                gallery_nav_button.setEnabled(true);
                text_nav_button.setEnabled(true);

                break;

            case R.id.text_nav_button:

                photoIcon.setImageResource(R.drawable.gallery_nav_faded);
                line_1.setVisibility(View.GONE);

                cameraIcon.setImageResource(R.drawable.camera_nav_faded);
                line_2.setVisibility(View.GONE);

                textIcon.setImageResource(R.drawable.text_nav);
                line_3.setVisibility(View.VISIBLE);

                text_nav_button.setEnabled(false);
                gallery_nav_button.setEnabled(true);
                camera_nav_button.setEnabled(true);

                break;


        }

    }



}

