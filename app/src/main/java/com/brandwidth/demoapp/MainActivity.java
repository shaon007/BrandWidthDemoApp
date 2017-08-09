package com.brandwidth.demoapp;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.brandwidth.demoapp.Fragment.OneFragment;
import com.brandwidth.demoapp.Fragment.ThreeFragment;
import com.brandwidth.demoapp.Fragment.TwoFragment;


public class MainActivity extends AppCompatActivity
{
private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize_tabs();

    }

    private void initialize_tabs()
    {
     /*   toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "Movies");
        adapter.addFragment(new TwoFragment(), "TV Shows");
        adapter.addFragment(new ThreeFragment(), "People");
        viewPager.setAdapter(adapter);
    }

}
