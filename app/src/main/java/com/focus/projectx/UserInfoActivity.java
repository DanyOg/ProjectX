package com.focus.projectx;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.focus.projectx.fragments.TopFragment;
import com.focus.projectx.fragments.TrackFragment;
import com.focus.projectx.model.UserModel;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.focus.projectx.fragments.SignInFragment.PUBLIC_TOKEN_KEY;
import static com.focus.projectx.helpers.Urls.ACTIVE_USER;
import static com.focus.projectx.helpers.Urls.APP_PREFERENCES;
import static com.focus.projectx.helpers.Urls.USER_AVATAR;


public class UserInfoActivity extends AppCompatActivity   implements AppBarLayout.OnOffsetChangedListener{
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;

    private LinearLayout     mTitleContainer;
    private TextView         mTitle;
    private AppBarLayout     mAppBarLayout;
    private Toolbar          mToolbar;

    @BindView(R.id.user_profile_image)   CircleImageView mUserProfileImage;
    @BindView(R.id.main_textview_title)  TextView        toolbarTitleUserName;
    @BindView(R.id.user_name)            TextView        userName;
    @BindView(R.id.user_description)     TextView        userDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        bindActivity();
        setMenu(mToolbar);
        mAppBarLayout.addOnOffsetChangedListener(this);
        mToolbar.inflateMenu(R.menu.menu);
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

        try {
            UserModel userModel = (UserModel) getIntent().getParcelableExtra("Edit");
            if(userModel==null){
                UserModel newUser = getUser();
                setupUser(newUser.getUser().getPersonAvatar(),
                          newUser.getUser().getPersonFirstName(),
                          newUser.getUser().getPersonFirstName(),
                          newUser.getUser().getPersonDescription());
            } else {
                String json = new Gson().toJson(userModel);
                saveUser(json);
                setupUser(userModel.getUser().getPersonAvatar(),
                        userModel.getUser().getPersonFirstName(),
                        userModel.getUser().getPersonFirstName(),
                        userModel.getUser().getPersonDescription());
            }
        }catch (Exception ignored){
            Log.d("Ex", ignored.getMessage()+" ");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll    = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    private void setMenu(Toolbar toolbar){
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Main");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName("Settings");

        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("Log out").withIdentifier(3),
                        new SecondaryDrawerItem().withName("Player").withIdentifier(4)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        Toast.makeText(getApplicationContext(), position+"", Toast.LENGTH_SHORT).show();
                        switch (position){
                            case 2 : {
                                Intent intent = new Intent(getApplication(), AllUserActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case 3 : {
                                logOut();
                                break;
                            }
                            case 4 : {
                                Intent intent = new Intent(getApplication(), MusicPlayerActivity.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        return false;
                    }
                })
                .build();
    }

    private void logOut(){
        SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PUBLIC_TOKEN_KEY, "logout");
        editor.apply();
        Log.d("sharedNewToken", sharedPref.getString(PUBLIC_TOKEN_KEY, "logout"));
    }

    private void saveUser(String user){
        SharedPreferences sharedPref = getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACTIVE_USER, user);
        editor.apply();
    }

    private UserModel getUser(){
        SharedPreferences sharedPref = getApplication().getSharedPreferences(APP_PREFERENCES,Context.MODE_PRIVATE);
        String json = sharedPref.getString(ACTIVE_USER, "user");
        return  new Gson().fromJson(json, UserModel.class);
    }

    private void setupUser(String userUrlProfile, String firstName, String lastName, String description){
        Picasso.with(this)
                .load(USER_AVATAR + userUrlProfile)
                .into(mUserProfileImage);

        userName.setText(firstName + " " + lastName);
        toolbarTitleUserName.setText(firstName + " " + lastName);
        userDescription.setText(description);
    }
}
