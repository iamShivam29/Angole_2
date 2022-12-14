package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.adapters.EpisodesRecyclerAdapter
import com.android.angole.adapters.SeasonRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivitySeriesVideoSelectedBinding
import com.android.angole.models.Episodes
import com.android.angole.models.SeriesInfo
import com.android.angole.models.ShowItems
import com.android.angole.utils.Constants
import com.android.angole.viewmodels.StreamViewModel
import com.bumptech.glide.Glide

class SeriesVideoSelectedActivity : AppCompatActivity(), SeasonRecyclerAdapter.OnClickEvent, EpisodesRecyclerAdapter.OnClickEvent {
    private var binding: ActivitySeriesVideoSelectedBinding? = null
    private var streamViewModel: StreamViewModel? = null
    private var streamId: Int? = null
    private var detailsData: ShowItems? = null
    private var isFeatured = false
    private var isAdded = false              // this variable will check if the item has added,if yes we pass this through setResult with intent
    private var isRemoved = false            // this variable will check if the item has removed, if yes we pass this through setResult with intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeriesVideoSelectedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        streamViewModel = ViewModelProvider(this)[StreamViewModel::class.java]

        isFeatured = intent.getBooleanExtra("FromHome", false)

        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer2?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()
        binding?.shimmerContainer2?.startShimmer()

        loadData()

        binding?.ibPlaySelected?.setOnClickListener {
            detailsData?.let {
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra("videoUri", it.seriesInfo!![0].episodes!![0].playableUrl)
                intent.putExtra("videoName", it.seriesInfo[0].episodes!![0].title)
                startActivity(intent)
            }
        }

        binding?.ibLike?.setOnClickListener {
            setLikeUnlike()
        }

        binding?.btnRetry?.setOnClickListener {
            binding?.shimmerContainer?.visibility = View.VISIBLE
            binding?.shimmerContainer2?.visibility = View.VISIBLE
            binding?.shimmerContainer?.startShimmer()
            binding?.shimmerContainer2?.startShimmer()

            binding?.lvInfo?.visibility = View.GONE

            loadData()
        }
    }

    private fun loadData(){
        val token = AuthConfig(this).getToken()
        val authToken = "Bearer $token"

        streamId = intent.getIntExtra("id", 0)
        streamViewModel?.getShowDetails(authToken, streamId!!, isFeatured)

        streamViewModel?.showDetailData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        detailsData = it.data.items

                        binding?.layoutMain?.visibility = View.VISIBLE
                        if(detailsData != null) {
                            setDetails(detailsData!!)
                            setSeasons(it.data.items?.seriesInfo)
                        }else{
//                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "No data found"
                        }
                    }else if (subCode == 404){
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "No data found"
                    }else{
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"
                    }

                }else{
//                    if (!it.message.isNullOrEmpty()) {
//                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }

                    binding?.lvInfo?.visibility = View.VISIBLE
                    binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                    binding?.tvInfo?.text = "Something went wrong"
                }

                binding?.shimmerContainer?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()
                binding?.shimmerContainer2?.visibility = View.GONE
                binding?.shimmerContainer2?.stopShimmer()
            }
        }
    }

    private fun setDetails(detailsData: ShowItems) {
        val notAvailable = "N/A"

        if(detailsData.name.isNullOrEmpty()){
            binding?.tvSelectedMovieName?.text = notAvailable
        }else{
            binding?.tvSelectedMovieName?.text = detailsData.name
        }

        if (detailsData.genre.isNullOrEmpty()){
            binding?.tvSelectedGenre?.text = notAvailable
        }else{
            binding?.tvSelectedGenre?.text = detailsData.genre
        }

//        if (detailsData.duration.isNullOrEmpty()){
//            binding?.tvSelectedTime?.text = notAvailable
//        }else{
//            binding?.tvSelectedTime?.text = detailsData.duration
//        }

        if (detailsData.description.isNullOrEmpty()){
            binding?.tvDescriptionSelected?.text = notAvailable
        }else{
            binding?.tvDescriptionSelected?.text = detailsData.description
        }

        if (detailsData.stars.isNullOrEmpty()){
            binding?.tvStarCast?.text = notAvailable
        }else{
            binding?.tvStarCast?.text = detailsData.stars
        }

        if (detailsData.director.isNullOrEmpty()){
            binding?.tvDirectorName?.text = notAvailable
        }else{
            binding?.tvDirectorName?.text = detailsData.director
        }

        if (detailsData.youtube_trailer.isNullOrEmpty()){
            binding?.tvPlayTrailer?.visibility = View.INVISIBLE
        }

        var imageUrl = ""
        if (!detailsData.cover.isNullOrEmpty()){
            imageUrl = detailsData.cover
        }else{
            Glide.with(this)
                .load(R.drawable.ic_image_placeholder)
                .into(binding?.ivSelected!!)
        }

        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                .load(imageUrl)
                .into(binding?.ivSelected!!)
        }

        detailsData.isFavourie?.let {
            if (it){
                binding?.ibLike?.setImageResource(R.drawable.ic_heart_color)
            }else{
                binding?.ibLike?.setImageResource(R.drawable.ic_heart)
            }
        }
    }

    private fun setSeasons(seriesList: List<SeriesInfo>?){
        if (!seriesList.isNullOrEmpty()) {
            val adapter = SeasonRecyclerAdapter(this, seriesList, this)
            binding?.rvSeasons?.adapter = adapter

            setEpisodes(seriesList[0].episodes)

            binding?.layoutMain2?.visibility = View.VISIBLE

            binding?.shimmerContainer2?.visibility = View.GONE
            binding?.shimmerContainer2?.stopShimmer()
        }
    }

    private fun setEpisodes(episodesList: List<Episodes>?){
        if (!episodesList.isNullOrEmpty()) {
            val episodeAdapter = EpisodesRecyclerAdapter(this, episodesList,this)
            binding?.rvEpisodes?.adapter = episodeAdapter
        }
    }

    override fun onItemClicked(episodesList: List<Episodes>?) {
        setEpisodes(episodesList)
    }

    override fun onItemClicked(episode: Episodes) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra("videoUri", episode.playableUrl)
        intent.putExtra("videoName", episode.title)
        startActivity(intent)
    }

    private fun setLikeUnlike(){
        detailsData?.let {showItem ->
            val authConfig = AuthConfig(this)
            val authToken = "Bearer " + authConfig.getToken()
            val favDetails = HashMap<String, Any>()

            if(detailsData?.isFavourie!!) {
                Log.i("Series", "is Favourite")
                favDetails["id"] = intent.getIntExtra("id", 0)
                favDetails["forRemove"] = true
                favDetails["isFeatured"] = isFeatured
            }else{
                Log.i("Series", "is not Favourite")
                favDetails["cover"] = showItem.cover!!
                favDetails["id"] = intent.getIntExtra("id", 0)
                favDetails["type"] = "SHOWS"
                favDetails["forRemove"] = false
                favDetails["isFeatured"] = isFeatured
            }

            streamViewModel?.setLikeUnlike(authToken, favDetails)
            streamViewModel?.favData?.observe(this){
                it?.let {
                    if (it.data != null){
                        val status = it.data.status
                        val subCode = it.data.subCode
                        if (status){
                            if (subCode == 201){
                                binding?.ibLike?.setImageResource(R.drawable.ic_heart_color)
                                detailsData?.isFavourie = true
                                isAdded = true
                                isRemoved = false
                            }else if (subCode == 200){
                                binding?.ibLike?.setImageResource(R.drawable.ic_heart)
                                detailsData?.isFavourie = false
                                isAdded = false
                                isRemoved = true
                            }
                        }else{
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                    }

                    binding?.shimmerContainer?.visibility = View.GONE
                    binding?.shimmerContainer?.stopShimmer()
                }
            }
        }
    }

    override fun onBackPressed() {
        val what = if (isAdded) Constants.CONST_ADDED else Constants.CONST_REMOVED

        val intent = Intent()
        intent.putExtra("into", Constants.CONST_TYPE_SHOWS)
        intent.putExtra("what", what)
        intent.putExtra("id", streamId)
        setResult(RESULT_OK, intent)

        finish()
    }

}