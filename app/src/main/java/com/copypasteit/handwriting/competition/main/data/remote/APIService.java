package com.copypasteit.handwriting.competition.main.data.remote;

import com.copypasteit.handwriting.competition.main.model.Post;
import com.copypasteit.handwriting.competition.main.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("/api/role")
    Call<List<Post>> getpost();

    @GET("/api/role")
    Call<List<Post>> getUsers(@Query("name") String keyword);

    @GET("/api/result")
    Call<List<Result>> getresult();

//    @FormUrlEncoded
//    @POST("/api/order")
//    Call<Post> savePost(@Body Post post);
//
//    @FormUrlEncoded
//    @POST("/api/role")
//    Call<Post> savePost(@Field("name") String name, @Field("email") String email, @Field("number") String number, @Field("cash") String cash,
//                        @Field("address") String address, @Field("state") String state, @Field("zip") String zip, @Field("role") String role);

//    @FormUrlEncoded
//    @PUT("/api/order/{id}")
//    Call<Post> putPost(
//            @Path("id") int id,
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("number") String number,
//            @Field("cash") String cash,
//            @Field("address") String address,
//            @Field("state") String state,
//            @Field("zip") String zip,
//            @Field("role") String role);

//    @PATCH("/api/order")
//    Call<Post> patchPost(@Path("id") int id, @Field("name") String name, @Field("email") String email, @Field("number") String number, @Field("cash") String cash,
//                         @Field("address") String address, @Field("state") String state, @Field("zip") String zip, @Field("role") String role);

//    @DELETE("/api/order/{id}")
//    Call<Void> deletePost(@Path("id") int id);

}
