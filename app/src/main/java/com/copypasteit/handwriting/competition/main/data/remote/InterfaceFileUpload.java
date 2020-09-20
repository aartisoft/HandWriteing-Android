package com.copypasteit.handwriting.competition.main.data.remote;

import com.copypasteit.handwriting.competition.main.model.UploadResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface InterfaceFileUpload {

    @Multipart
    @POST("/api/submit")
    Call<UploadResponse> UploadFile(
                                    @Part("exam_name") RequestBody exam_name,
                                    @Part("par_school") RequestBody par_school,
                                    @Part("par_phone") RequestBody par_phone,
                                    @Part("par_email") RequestBody par_email,
                                    @Part("par_address") RequestBody par_address,

                                    @Part MultipartBody.Part file

                                  );
}
