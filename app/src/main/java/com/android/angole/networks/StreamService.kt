package com.android.angole.networks

import com.android.angole.models.*
import retrofit2.Response
import retrofit2.http.*

interface StreamService {
    @GET("dashboard/home/")
    suspend fun getHome(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/shows/")
    suspend fun getWebShows(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/movies/")
    suspend fun getMovies(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/live")
    suspend fun getLiveTv(@Header("Authorization") authToken: String): Response<LiveTvData>

    @GET("stream/movie/{id}")
    suspend fun getMovieDetails(@Header("Authorization") authToken: String, @Path("id") id: Int, @Query("featured") isFeatured: Boolean): Response<MovieDetails>

    @GET("stream/show/{id}")
    suspend fun getShowDetails(@Header("Authorization") authToken: String, @Path("id") id: Int, @Query("featured") isFeatured: Boolean): Response<ShowDetails>

    @GET("onboard/list/")
    suspend fun getSuggestionList(@Header("Authorization") authToken: String): Response<SuggestionData>

    @POST("mylist/")
    suspend fun setLikeUnlike(@Header("Authorization") authToken: String, @Body favDetails: HashMap<String,Any>): Response<FavData>

    @GET("dashboard/movies/category/{id}")
    suspend fun getAllMovies(@Header("Authorization") authToken: String, @Path("id") id: Int, @Query("page") pageNo: Int): Response<SeeMoreData>

    @GET("dashboard/shows/category/{id}")
    suspend fun getAllSeries(@Header("Authorization") authToken: String, @Path("id") id: Int, @Query("page") pageNo: Int): Response<SeeMoreData>

    @GET("mylist/home/")
    suspend fun getMyHomeList(@Header("Authorization") authToken: String): Response<MyListData>

    @GET("mylist/shows/")
    suspend fun getMyShowsList(@Header("Authorization") authToken: String): Response<MyListData>

    @GET("mylist/movies/")
    suspend fun getMyMoviesList(@Header("Authorization") authToken: String): Response<MyListData>
}