package com.copypasteit.handwriting.competition.GalleryActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.copypasteit.handwriting.competition.HomeActivity.HomeActivity;
import com.copypasteit.handwriting.competition.R;
import com.copypasteit.handwriting.competition.main.data.remote.ApiUtils;
import com.copypasteit.handwriting.competition.main.model.Post;
import com.copypasteit.handwriting.competition.main.model.Result;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GalleryActivity extends AppCompatActivity {
    //STEP-1 for InterstitialAd=====================================
    public InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd ;


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GalleryAdapter adapter;
    ProgressDialog pd;
    List<Result> modelList = new ArrayList<>();


    @Override
    public void onBackPressed() {
        interstitialAd.show();
        //StartAppAd.onBackPressed(this);
        Intent intent = new Intent(GalleryActivity.this, HomeActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //for startapps ads
        StartAppSDK.init(this, "207503939", true);

        //STEP - 2 Intertatial ads =======================================
        //add below 3 lines with rewordvideo ads
        interstitialAd = new InterstitialAd(this);
        //Interstitial ads unit add
        interstitialAd.setAdUnitId("ca-app-pub-3010341507881755/1463714231");
        loadInterstitialAd();

        //ENDSTEP - 2 Intertatial ads ====================================


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        recyclerView = findViewById(R.id.recyclerId1);
        pd = new ProgressDialog(this);
        pd.setTitle("Loading Data...");
        recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new GalleryAdapter(GalleryActivity.this, modelList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GalleryActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        getData();

    }

    //for ads

    //STEP - 3 InterstitialAd load ads ===================================
    //====================================================================
    private void loadInterstitialAd() {
        //ads unit id
        interstitialAd.loadAd(new AdRequest.Builder().build());

    }
    //ENDSTEP - 3 InterstitialAd load ads =================================


    private void getData() {

        pd.show();

        Call<List<Result>> result = ApiUtils.getAPIService().getresult();

        result.enqueue(new Callback<List<Result>>() {
            @Override
            public void onResponse(Call<List<Result>> call, Response<List<Result>> response) {
                List<Result> posts = response.body();
                adapter.setData(posts);
                recyclerView.setAdapter(adapter);
                pd.dismiss();
                //show data
            }

            @Override
            public void onFailure(Call<List<Result>> call, Throwable t) {

            }
        });
//
//        Call<List<Result>> userList = ApiUtils.getAPIService().getresult();
//
//
//        userList.enqueue(new Callback<List<Post>>() {
//            @Override
//            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
//                List<Result> posts = response.body();
//                adapter.setData(posts);
//                recyclerView.setAdapter(adapter);
//                pd.dismiss();
//                //show data
//            }
//
//            @Override
//            public void onFailure(Call<List<Post>> call, Throwable t) {
//
//            }
//        });

    }

    public void BackClick(View view) {

        interstitialAd.show();

    }
}
