package com.android.angole.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.angole.models.HomeData
import com.android.angole.models.MovieDetails
import com.android.angole.models.ShowDetails
import com.android.angole.repositories.StreamRepo
import com.android.angole.utils.Resource
import kotlinx.coroutines.launch

class StreamViewModel : ViewModel(){
    var homeData = MutableLiveData<Resource<HomeData>>()
    var webData = MutableLiveData<Resource<HomeData>>()
    var moviesData = MutableLiveData<Resource<HomeData>>()
    var liveData = MutableLiveData<Resource<HomeData>>()
    var movieDetailsData = MutableLiveData<Resource<MovieDetails>>()
    var showDetailData = MutableLiveData<Resource<ShowDetails>>()

    fun getHome(authToken: String){
        viewModelScope.launch {
            homeData.value = StreamRepo().getHome(authToken)
        }
    }

    fun getWebShows(authToken: String){
        viewModelScope.launch {
            webData.value = StreamRepo().getWebShows(authToken)
        }
    }

    fun getMovies(authToken: String){
        viewModelScope.launch {
            moviesData.value = StreamRepo().getMovies(authToken)
        }
    }

    fun getLiveTv(authToken: String){
        viewModelScope.launch {
            liveData.value = StreamRepo().getLiveTv(authToken)
        }
    }

    fun getMovieDetails(authToken: String, id: Int){
        viewModelScope.launch {
            movieDetailsData.value = StreamRepo().getMovieDetails(authToken, id)
        }
    }

    fun getShowDetails(authToken: String, id: Int){
        viewModelScope.launch {
            showDetailData.value = StreamRepo().getShowDetails(authToken, id)
        }
    }
}