package com.brandwidth.demoapp;


import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.brandwidth.demoapp.Fragment.OneFragment;
import com.brandwidth.demoapp.Fragment.ThreeFragment;
import com.brandwidth.demoapp.Fragment.TwoFragment;
import com.brandwidth.demoapp.Model.BeanDetails;
import com.bumptech.glide.Glide;


public class DetailsActivity extends AppCompatActivity
{
    ImageView imgView;
    TextView txtVwName, txtVwDate, txtVwOverview, txtVwProductionPlace, txtVwExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initialize();

        Intent i = getIntent();
        BeanDetails myParcelableObject = (BeanDetails) i.getParcelableExtra("extra_parcel_obj");

        String imgUrl = "http://image.tmdb.org/t/p/w185//" + myParcelableObject.getImg_path();

        Glide.with(this).load(imgUrl)
                .placeholder(R.mipmap.ic_launcher)
/*                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.NONE)*/
                .into(imgView);

        txtVwName.setText(myParcelableObject.getName());
        txtVwDate.setText(myParcelableObject.getRelease_date());
        txtVwOverview.setText(myParcelableObject.getOverview());
        txtVwProductionPlace.setText(myParcelableObject.getProduction_place());
        txtVwExtra.setText(myParcelableObject.getExtraString());
    }

    private void  initialize()
    {
        imgView = (ImageView) findViewById(R.id.detail_img);
        txtVwName = (TextView)findViewById(R.id.detail_name);
        txtVwDate = (TextView)findViewById(R.id.detail_date);
        txtVwOverview = (TextView)findViewById(R.id.detail_overview);
        txtVwProductionPlace = (TextView)findViewById(R.id.detail_production_place);
        txtVwExtra = (TextView)findViewById(R.id.detail_extra);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}
