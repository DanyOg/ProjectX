package com.focus.projectx;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.focus.projectx.fragments.TopFragment;
import com.focus.projectx.fragments.TrackFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class UserInfoActivity extends AppCompatActivity {

    private TextView userName;
    private TextView userDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.top_track, TopFragment.class)
                .add(R.string.track, TrackFragment.class)
                .create());

        userName = (TextView) findViewById(R.id.user_profile_name);
        userDesc = (TextView) findViewById(R.id.user_profile_description);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(!getIntent().getExtras().isEmpty()){
            userName.setText(getIntent().getExtras().getString("name"));
            userDesc.setText(getIntent().getExtras().getString("desc"));
            TextView toolbar = (TextView) findViewById(R.id.toolbar_title);
            toolbar.setText(getIntent().getExtras().getString("name"));
        }
    }
}
