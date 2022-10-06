package com.android.angole.repositories

import com.android.angole.models.*
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

    suspend fun getLiveTv(authToken: String): Resource<LiveTvData>{
        return apiRequest { RetrofitBuilder().streamService().getLiveTv(authToken) }
    }

    suspend fun getMovieDetails(authToken: String, id: Int, isFeatured: Boolean): Resource<MovieDetails>{
        return apiRequest { RetrofitBuilder().streamService().getMovieDetails(authToken, id, isFeatured) }
    }

    suspend fun getShowDetails(authToken: String, id: Int, isFeatured: Boolean): Resource<ShowDetails>{
        return apiRequest { RetrofitBuilder().streamService().getShowDetails(authToken, id, isFeatured) }
    }

    suspend fun getSuggestionList(authToken: String): Resource<SuggestionData>{
        return apiRequest { RetrofitBuilder().streamService().getSuggestionList(authToken) }
    }

    suspend fun setLikeUnlike(authToken: String, favDetails: HashMap<String, Any>): Resource<FavData>{
        return apiRequest { RetrofitBuilder().streamService().setLikeUnlike(authToken, favDetails) }
    }

    suspend fun getAllMovies(authToken: String, id: Int, pageNo: Int): Resource<SeeMoreData>{
        return apiRequest { RetrofitBuilder().streamService().getAllMovies(authToken, id, pageNo) }
    }

    suspend fun getAllSeries(authToken: String, id: Int, pageNo: Int): Resource<SeeMoreData>{
        return apiRequest { RetrofitBuilder().streamService().getAllSeries(authToken, id, pageNo) }
    }

    suspend fun getMyHomeList(authToken: String): Resource<MyListData>{
        return apiRequest { RetrofitBuilder().streamService().getMyHomeList(authToken) }
    }

    suspend fun getMyShowsList(authToken: String): Resource<MyListData>{
        return apiRequest { RetrofitBuilder().streamService().getMyShowsList(authToken) }
    }

    suspend fun getMyMoviesList(authToken: String): Resource<MyListData>{
        return apiRequest { RetrofitBuilder().streamService().getMyMoviesList(authToken) }
    }
}