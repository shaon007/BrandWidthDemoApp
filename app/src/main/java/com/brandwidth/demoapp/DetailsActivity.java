package com.brandwidth.demoapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brandwidth.demoapp.Model.BeanDetails;
import com.bumptech.glide.Glide;


public class DetailsActivity extends AppCompatActivity
{
    ImageView imgView;
    TextView txtVwName, txtVwDate, txtVwOverview, txtVwProductionPlace, txtVwExtra;
    private Toolbar toolbar;

    TextView tv;
    ProgressBar pBar;
    int pStatus = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


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
        txtVwExtra.setText(myParcelableObject.getExtraString() +"\n");

        float fprog= Float.valueOf(myParcelableObject.getRating());
        int prog=(int)fprog;
        pBar.setProgress(prog);
        tv.setText(prog + "/" + pBar.getMax());
    }

    private void  initialize()
    {
        imgView = (ImageView) findViewById(R.id.detail_img);
        txtVwName = (TextView)findViewById(R.id.detail_name);
        txtVwDate = (TextView)findViewById(R.id.detail_date);
        txtVwOverview = (TextView)findViewById(R.id.detail_overview);
        txtVwProductionPlace = (TextView)findViewById(R.id.detail_production_place);
        txtVwExtra = (TextView)findViewById(R.id.detail_extra);

        tv = (TextView) findViewById(R.id.textView1);
        pBar = (ProgressBar) findViewById(R.id.progressBar1);


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
