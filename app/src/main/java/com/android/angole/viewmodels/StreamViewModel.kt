package com.android.angole.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.angole.models.*
import com.android.angole.repositories.StreamRepo
import com.android.angole.utils.Resource
import kotlinx.coroutines.launch

class StreamViewModel : ViewModel(){
    var homeData = MutableLiveData<Resource<HomeData>>()
    var webData = MutableLiveData<Resource<HomeData>>()
    var moviesData = MutableLiveData<Resource<HomeData>>()
    var liveData = MutableLiveData<Resource<LiveTvData>>()
    var movieDetailsData = MutableLiveData<Resource<MovieDetails>>()
    var showDetailData = MutableLiveData<Resource<ShowDetails>>()
    var suggestionData = MutableLiveData<Resource<SuggestionData>>()
    var favData = MutableLiveData<Resource<FavData>>()
    var allMoviesData = MutableLiveData<Resource<SeeMoreData>>()
    var allSeriesData = MutableLiveData<Resource<SeeMoreData>>()
    var myHomeListData = MutableLiveData<Resource<MyListData>>()
    var myShowListData = MutableLiveData<Resource<MyListData>>()
    var myMoviesListData = MutableLiveData<Resource<MyListData>>()

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

    fun getMovieDetails(authToken: String, id: Int, isFeatured: Boolean){
        viewModelScope.launch {
            movieDetailsData.value = StreamRepo().getMovieDetails(authToken, id, isFeatured)
        }
    }

    fun getShowDetails(authToken: String, id: Int, isFeatured: Boolean){
        viewModelScope.launch {
            showDetailData.value = StreamRepo().getShowDetails(authToken, id, isFeatured)
        }
    }

    fun getSuggestionList(authToken: String){
        viewModelScope.launch {
            suggestionData.value = StreamRepo().getSuggestionList(authToken)
        }
    }

    fun setLikeUnlike(authToken: String, favDetails: HashMap<String, Any>){
        viewModelScope.launch {
            favData.value = StreamRepo().setLikeUnlike(authToken, favDetails)
        }
    }

    fun getAllMovies(authToken: String, id: Int, pageNo: Int){
        viewModelScope.launch {
            allMoviesData.value = StreamRepo().getAllMovies(authToken, id, pageNo)
        }
    }

    fun getAllSeries(authToken: String, id: Int, pageNo: Int){
        viewModelScope.launch {
            allSeriesData.value = StreamRepo().getAllSeries(authToken, id, pageNo)
        }
    }

    fun getMyHomeList(authToken: String){
        viewModelScope.launch {
            myHomeListData.value = StreamRepo().getMyHomeList(authToken)
        }
    }

    fun getMyShowsList(authToken: String){
        viewModelScope.launch {
            myShowListData.value = StreamRepo().getMyShowsList(authToken)
        }
    }

    fun getMyMoviesList(authToken: String){
        viewModelScope.launch {
            myMoviesListData.value = StreamRepo().getMyMoviesList(authToken)
        }
    }


}