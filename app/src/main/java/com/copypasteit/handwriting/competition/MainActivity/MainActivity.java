package com.copypasteit.handwriting.competition.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.copypasteit.handwriting.competition.GalleryActivity.GalleryActivity;
import com.copypasteit.handwriting.competition.HomeActivity.HomeActivity;
import com.copypasteit.handwriting.competition.R;
import com.copypasteit.handwriting.competition.main.imageutils.ApiClient;
import com.copypasteit.handwriting.competition.main.data.remote.InterfaceFileUpload;
import com.copypasteit.handwriting.competition.main.imageutils.ClsGlobal;
import com.copypasteit.handwriting.competition.main.model.UploadResponse;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.startapp.sdk.adsbase.StartAppSDK;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.copypasteit.handwriting.competition.main.imageutils.ClsGlobal.CreateProgressDialog;
import static com.copypasteit.handwriting.competition.main.imageutils.ClsGlobal.hideProgress;
import static com.copypasteit.handwriting.competition.main.imageutils.ClsGlobal.showProgress;
import static com.copypasteit.handwriting.competition.main.imageutils.ClsGlobal.updateProgress;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener {
    //STEP-1 for InterstitialAd=====================================
    public InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd ;

    EditText edname, edemail, ednumber, edschool, edaddress;
    private static final int CAPTURE_REQUEST_CODE = 0;
    private final int REQUEST_PERMISSION = 1;
    private final int REQUEST_FILE_CODE = 34;
    ImageView imageView;
    private TextView txt_file_Name;
    private Button choose_file, Upload_file, Take_capta;
    String FilePath = "";
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //for startapps ads
        StartAppSDK.init(this, "207503939", true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //apps pub id
        MobileAds.initialize(MainActivity.this, "ca-app-pub-3010341507881755~8029122586");
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



        txt_file_Name = findViewById(R.id.txt_file_Name);
        choose_file = findViewById(R.id.choose_file);
        Upload_file = findViewById(R.id.Upload_file);

        imageView = findViewById(R.id.imageView);

        //for progress dialog
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Image Upload....");

        //Edittext maping
        edname = findViewById(R.id.editText_name);
        edemail = findViewById(R.id.editText_email);
        ednumber = findViewById(R.id.editText_number);
        edschool = findViewById(R.id.editText_school);
        edaddress = findViewById(R.id.editText_address);



        requestLocationPermission();

//
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }



        choose_file.setOnClickListener(v -> {

            String manufacturer = Build.MANUFACTURER;

            if (manufacturer.contains("samsung")) {
                Log.e("manufacturer", "samsung call");
                Intent intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
                intent.putExtra("CONTENT_TYPE", "*/*");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent, REQUEST_FILE_CODE);
            } else {
                Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
                chooser.addCategory(Intent.CATEGORY_OPENABLE);
                chooser.setDataAndType(uri, "*/*");

                try {
                    PackageManager pm = getPackageManager();
                    if (chooser.resolveActivity(pm) != null) {
                        startActivityForResult(chooser, REQUEST_FILE_CODE);
                    }

                } catch (android.content.ActivityNotFoundException ex) {

                    Toast.makeText(MainActivity.this, "Please install a File Manager."
                            , Toast.LENGTH_SHORT).show();
                }
            }

        });

        Upload_file.setOnClickListener(v -> {




            if (FilePath != null && !FilePath.equalsIgnoreCase("")){
                UploadFile(new File(FilePath));

                progressDialog.show();


                //new UploadAsyncTask().execute();


//                FileUploader fileUploader = new FileUploader(new File(FilePath),MainActivity.this);
//                fileUploader.SetCallBack(new FileUploader.FileUploaderCallback() {
//                        @Override
//                        public void onError() {
//                            hideProgress();
//                        }
//
//                        @Override
//                        public void onFinish(String responses) {
//                            hideProgress();
//                        }
//
//                        @Override
//                        public void onProgressUpdate(int currentpercent, int totalpercent,String msg) {
//                            updateProgress(totalpercent,"Uploading Backup",msg);
//                        }
//                });


                FilePath = "";
                txt_file_Name.setText("File Path");
            }else {
                Toast.makeText(MainActivity.this,"Please Wait..",Toast.LENGTH_SHORT).show();
            }

        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (EasyPermissions.hasPermissions(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {


                Uri getUri = data.getData();
                Glide
                        .with(MainActivity.this)
                        .load(getUri)
                        .centerCrop()
                        .placeholder(R.drawable.ic_love)
                        .into(imageView);
                if (getUri != null) {

                    try {
                        String getpathFromFileManager = data.getData().getPath();

                        Log.e("contains", getpathFromFileManager);

                        if ("content".equals(getUri.getScheme())) {
                            Log.e("contains", "getUri.getScheme()");
                            FilePath = ClsGlobal.getPathFromUri(MainActivity.this, getUri);
                            txt_file_Name.setText(FilePath);
                        } else {

                            FilePath = getUri.getPath();

                            txt_file_Name.setText(FilePath);
                        }

                        Log.e("contains", FilePath);

                        if (getpathFromFileManager != null && getpathFromFileManager.contains("primary:")) {
                            Log.e("contains", "contains inside");

                            if (FilePath != null) {

//                                UploadFile(new File(FilePath));
//                                new LocalRestoreAsyncTask(this, FilePath).execute();
                                Toast.makeText(this, FilePath, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Incorrect file", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Log.e("contains", "contains outside");
                            if (getpathFromFileManager != null && FilePath != null) {
//                                UploadFile(new File(FilePath));
//                                new LocalRestoreAsyncTask(this, FilePath).execute();
                                Toast.makeText(this, FilePath, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Incorrect file", Toast.LENGTH_LONG).show();
                            }

                        }


                    } catch (Exception e) {
                        Log.e("Exception", e.getMessage());
                    }

                }


            }else {
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage",
                        REQUEST_PERMISSION, Manifest.permission.READ_EXTERNAL_STORAGE);
            }

        }

    }

    @AfterPermissionGranted(REQUEST_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = new String[0];
        perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        } else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission",
                    REQUEST_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions,
                grantResults, this);
    }




    private void UploadFile(File file) {


        InterfaceFileUpload interfaceFileUpload = ApiClient.getRetrofitInstance().create(InterfaceFileUpload.class);
//      InterfaceFileUpload interfaceFileUpload = retrofit.create(InterfaceFileUpload.class);

        RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("post_image", file.getName(), mFile);

        ///RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody exam_name = RequestBody.create(okhttp3.MultipartBody.FORM, edname.getText().toString());
        RequestBody par_school = RequestBody.create(okhttp3.MultipartBody.FORM, edschool.getText().toString());
        RequestBody par_phone = RequestBody.create(okhttp3.MultipartBody.FORM, ednumber.getText().toString());
        RequestBody par_email = RequestBody.create(okhttp3.MultipartBody.FORM, edemail.getText().toString());
        RequestBody par_address = RequestBody.create(okhttp3.MultipartBody.FORM, edaddress.getText().toString());


        Call <UploadResponse> fileUpload = interfaceFileUpload.UploadFile(exam_name, par_school, par_phone,par_email, par_address, fileToUpload);

        fileUpload.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<UploadResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Successfully Submited", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void BackClick(View view) {

        mRewardedVideoAd.show();
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you Back Now?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

        mRewardedVideoAd.show();
    }


    //for ads

    //STEP - 3 InterstitialAd load ads ===================================
    //====================================================================
    private void loadInterstitialAd() {
        //ads unit id
        interstitialAd.loadAd(new AdRequest.Builder().build());
    }
    //ENDSTEP - 3 InterstitialAd load ads =================================

    //for adds
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

