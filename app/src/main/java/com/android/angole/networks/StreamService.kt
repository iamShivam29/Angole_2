package com.android.angole.networks

import com.android.angole.models.HomeData
import com.android.angole.models.MovieDetails
import com.android.angole.models.ShowDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface StreamService {
    @GET("dashboard/home/")
//    suspend fun getHome(): Response<HomeData>
    suspend fun getHome(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/shows/")
//    suspend fun getWebShows(): Response<HomeData>
    suspend fun getWebShows(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/movies/")
//    suspend fun getMovies(): Response<HomeData>
    suspend fun getMovies(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("dashboard/live")
//    suspend fun getLiveTv(): Response<HomeData>
    suspend fun getLiveTv(@Header("Authorization") authToken: String): Response<HomeData>

    @GET("stream/movie/{id}/")
//    suspend fun getMovieDetails(@Path("type") type: String, @Path("id") id: Int): Response<MovieDetails>
    suspend fun getMovieDetails(@Header("Authorization") authToken: String, @Path("id") id: Int): Response<MovieDetails>

    @GET("stream/show/{id}/")
//    suspend fun getShowDetails(@Path("type") type: String, @Path("id") id: Int): Response<ShowDetails>
    suspend fun getShowDetails(@Header("Authorization") authToken: String, @Path("id") id: Int): Response<ShowDetails>
}