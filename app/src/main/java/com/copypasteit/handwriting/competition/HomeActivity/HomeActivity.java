package com.copypasteit.handwriting.competition.HomeActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.copypasteit.handwriting.competition.BuildConfig;
import com.copypasteit.handwriting.competition.GalleryActivity.GalleryActivity;
import com.copypasteit.handwriting.competition.MainActivity.MainActivity;
import com.copypasteit.handwriting.competition.R;
import com.copypasteit.handwriting.competition.main.data.remote.ApiUtils;
import com.copypasteit.handwriting.competition.main.model.Post;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.material.navigation.NavigationView;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RewardedVideoAdListener {

    //STEP-1 for InterstitialAd=====================================
    public InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd ;


    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    ActionBarDrawerToggle mToogle;
    Toolbar mToolbar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout refreshLayout;
    CustomAdapter adapter;

    ProgressDialog pd;
    List<Post> modelList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // NOTE always use test ads during development and testing
        //StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);
        //for startapps ads
        StartAppSDK.init(this, "207503939", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //apps pub id
        MobileAds.initialize(HomeActivity.this, "ca-app-pub-3010341507881755~8029122586");
        mRewardedVideoAd  = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd .setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        //STEP - 2 Intertatial ads =======================================
        //add below 3 lines with rewordvideo ads
        interstitialAd = new InterstitialAd(this);
        //Interstitial ads unit add
        interstitialAd.setAdUnitId("ca-app-pub-3010341507881755/1463714231");
        loadInterstitialAd();

        //ENDSTEP - 2 Intertatial ads ====================================



        mDrawerLayout = findViewById(R.id.drawlayoutId);
        mNavigationView = findViewById(R.id.nav_view);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        recyclerView = findViewById(R.id.recyclerId);
        refreshLayout = findViewById(R.id.swipe_container);
        pd = new ProgressDialog(HomeActivity.this);

        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new CustomAdapter(HomeActivity.this, modelList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        // START for internet connection ==========================================================================

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null) && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable
        }else{
            //no connection
            showAlertDialog();
        }

        // END for internet connection ==========================================================================


        //for title color
        //getSupportActionBar().setTitle(Html.fromHtml("<font color='#17BC1E' size='15px'>An</font>"));

        setupNavigationView();
        getData();
        sweeprefresh();

    }



    private void sweeprefresh()
    {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {
        pd.setTitle("Loading data...");
        pd.show();
        Call<List<Post>> userList = ApiUtils.getAPIService().getpost();
        userList.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();
                adapter.setData(posts);
                recyclerView.setAdapter(adapter);

                pd.dismiss();
                //show data
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });



    }


    private void setupNavigationView() {

        mNavigationView.bringToFront();
        mToogle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.action_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                setupNavigationView();
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();
                startActivity(intent);
                return true;
            case R.id.menu_order:
                //Way one
                String facebookId = "fb://page/398829314010657";
                String urlPage = "http://www.facebook.com/sasoftbd0";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
                } catch (Exception e) {

                    //Open url web page.
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }
                return true;

            case R.id.menu_rateus:
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getBaseContext().getPackageName()));
                startActivity(rateIntent);
                return true;

            case R.id.menu_share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=com.copypasteit.handwriteing.competition";
                String shareSub = "HandWriteing Competition";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                return true;
            case R.id.menu_result:
                Intent intentgallery = new Intent(HomeActivity.this, GalleryActivity.class);
                startActivity(intentgallery);
                finish();
                return true;
            case R.id.menu_write:
                Intent intent2 = new Intent(HomeActivity.this, HomeActivity.class);
                startActivity(intent2);
                finish();
                return true;
            case R.id.menu_submit:
                Intent intent3 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent3);
                finish();
                return true;

            case R.id.menu_privcy:
                Intent intentprivacy = new Intent(Intent.ACTION_VIEW, Uri.parse("http://apps.androwep.com/writeing/privacy-policy.html"));
                startActivity(intentprivacy);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

        mRewardedVideoAd.show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_rateus)
        {
            Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getBaseContext().getPackageName()));
            startActivity(rateIntent);

        }

        else if(item.getItemId()==R.id.menu_order){
            //Way one
            String facebookId = "fb://page/398829314010657";
            String urlPage = "http://www.facebook.com/sasoftbd0";

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId)));
            } catch (Exception e) {

                //Open url web page.
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
            }
        }

        else if(item.getItemId()==R.id.menu_share)
        {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "https://play.google.com/store/apps/details?id=com.copypasteit.handwriteing.competition";
            String shareSub = "HandWriteing Competition";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
        else if(item.getItemId() == R.id.menu_privcy){
            //PRIVACY ACCEPT SECTION====================================================================

            //do something
            LinearLayout item_contact = (LinearLayout) findViewById(R.id.contect_id);
            //for diaglog show
            final Dialog myDialog  = new Dialog(HomeActivity.this);
            myDialog.setContentView(R.layout.dialog_contact);
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();

            myDialog.findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });

            //PRIVACY ACCEPT SECTION====================================================================
        }

        else if(item.getItemId()==R.id.menu_result) {
            Intent intent = new Intent(HomeActivity.this, GalleryActivity.class);
            startActivity(intent);
            finish();
        }
        else if(item.getItemId()==R.id.menu_submit) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(item.getItemId()==R.id.menu_refresh)
        {
            setupNavigationView();
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }

        mDrawerLayout.closeDrawers();
        return true;
    }
    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Offline");
        builder.setMessage("No Internet Connection").setCancelable(false)
                .setIcon(R.drawable.ic_error);
        AlertDialog alert = builder.create();
        alert.show();
    }



    //for ads

    //STEP - 3 InterstitialAd load ads ===================================
    //====================================================================
    private void loadInterstitialAd() {
        //ads unit id
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    //ENDSTEP - 3 InterstitialAd load ads =================================


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    //user define method for loadRewardedVideoAd load ads=================
    //===================================================================
    private void loadRewardedVideoAd() {

        //ads unit id
        mRewardedVideoAd.loadAd("ca-app-pub-3010341507881755/7262835820", new AdRequest.Builder().build());
    }

    //copy threds method onResume, onPause, onDestroy=========
    //========================================================
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
    //implements methods ======================================
    //from RewardAdListener ===================================
}
