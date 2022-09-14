package com.android.angole.repositories

import com.android.angole.models.HomeData
import com.android.angole.models.MovieDetails
import com.android.angole.models.ShowDetails
import com.android.angole.networks.RetrofitBuilder
import com.android.angole.utils.Resource
import com.android.angole.utils.SafeApiRequest
import retrofit2.http.Path

class StreamRepo: SafeApiRequest() {
    suspend fun getHome(authToken: String): Resource<HomeData>{
        return apiRequest { RetrofitBuilder().streamService().getHome(authToken) }
    }

    suspend fun getWebShows(authToken: String): Resource<HomeData>{
        return apiRequest { RetrofitBuilder().streamService().getWebShows(authToken) }
    }

    suspend fun getMovies(authToken: String): Resource<HomeData>{
        return apiRequest { RetrofitBuilder().streamService().getMovies(authToken) }
    }

    suspend fun getLiveTv(authToken: String): Resource<HomeData>{
        return apiRequest { RetrofitBuilder().streamService().getLiveTv(authToken) }
    }

    suspend fun getMovieDetails(authToken: String, id: Int): Resource<MovieDetails>{
        return apiRequest { RetrofitBuilder().streamService().getMovieDetails(authToken, id) }
    }

    suspend fun getShowDetails(authToken: String, id: Int): Resource<ShowDetails>{
        return apiRequest { RetrofitBuilder().streamService().getShowDetails(authToken, id) }
    }
}