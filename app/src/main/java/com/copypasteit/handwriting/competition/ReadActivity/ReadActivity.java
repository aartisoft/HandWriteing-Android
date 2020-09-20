package com.copypasteit.handwriting.competition.ReadActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.copypasteit.handwriting.competition.HomeActivity.HomeActivity;
import com.copypasteit.handwriting.competition.MainActivity.MainActivity;
import com.copypasteit.handwriting.competition.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ReadActivity extends AppCompatActivity implements RewardedVideoAdListener {
    //STEP-1 for InterstitialAd=====================================
    public InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        //apps pub id
        MobileAds.initialize(ReadActivity.this, "ca-app-pub-3010341507881755~8029122586");
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


        getDataFromIntent();
    }

    //STEP - 3 InterstitialAd load ads ===================================
    //====================================================================
    private void loadInterstitialAd() {
        //ads unit id
        interstitialAd.loadAd(new AdRequest.Builder().build());

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }
    //ENDSTEP - 3 InterstitialAd load ads =================================

    //STEP-4 for InterstitialAd=====================================
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ReadActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

        mRewardedVideoAd.show();
    }




    private void getDataFromIntent() {

        String dress_name = getIntent().getStringExtra("dress_name");
        String dress_model_no = getIntent().getStringExtra("dress_model_no");
        String dress_size = getIntent().getStringExtra("dress_size");
        String dress_image_url = getIntent().getStringExtra("dress_image_url");
        String dress_details = getIntent().getStringExtra("dress_details");

        setDataFromIntent(dress_name, dress_model_no, dress_size, dress_image_url, dress_details);

    }

    private void setDataFromIntent(String dress_name, String dress_model_no, String dress_size, String dress_image_url, String dress_details) {
        TextView postTitle  = findViewById(R.id.heder_text);
        postTitle.setText(dress_name);

        TextView postAuthor = findViewById(R.id.dress_modelId);
        postAuthor.setText("Write in Hand on a Page and take picture and Submit This..");

        HtmlTextView postDetails = (HtmlTextView)findViewById(R.id.html_text);
        postDetails.setHtml(dress_size);

        TextView PostCategory = findViewById(R.id.dress_sizeid);
        PostCategory.setText("Result Public: "+dress_model_no);

        //PhotoView dress_image = (PhotoView) findViewById(R.id.imageView);


//        post_image = findViewById(R.id.post_image);
//        post_category = findViewById(R.id.post_category);
//        post_date = findViewById(R.id.post_date);

//        Glide
//                .with(this)
//                .load(dress_image_url)
//                .override(380, 320)
//                .centerCrop()
//                .placeholder(R.drawable.ic_spinner)
//                .into(dress_image);

    }


    public void BackClick(View view) {
        loadInterstitialAd();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        Intent intent = new Intent(ReadActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void SubmitBtnClicked(View view) {
        Intent intent = new Intent(ReadActivity.this, MainActivity.class);
        loadInterstitialAd();
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        startActivity(intent);
    }




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

    //copy threds method onResume, onPause, onDestroy=================
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
}
