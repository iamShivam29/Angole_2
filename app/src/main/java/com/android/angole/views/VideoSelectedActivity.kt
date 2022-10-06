package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.adapters.CategoryListItemAdapter
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityVideoSelectedBinding
import com.android.angole.models.CategoryItems
import com.android.angole.models.MovieItems
import com.android.angole.models.MoviesData
import com.android.angole.viewmodels.StreamViewModel
import com.bumptech.glide.Glide

class VideoSelectedActivity : AppCompatActivity(), CategoryListItemAdapter.OnMainClick {
    private var binding: ActivityVideoSelectedBinding? = null
    private var streamViewModel: StreamViewModel? = null
    private var streamId: Int? = null
    private var detailsData: MoviesData? = null
    private var imageUrl = ""
    private var isFeatured = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoSelectedBinding.inflate(layoutInflater)
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
            if (detailsData != null){
                val intent = Intent(this, PlayerActivity::class.java)
                intent.putExtra("videoUri", detailsData?.stream_link)
                intent.putExtra("videoName", detailsData?.name)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding?.ibLike?.setOnClickListener {
            setLikeUnlike()
        }
    }

    private fun loadData(){
        val token = AuthConfig(this).getToken()
        val authToken = "Bearer $token"

        streamId = intent.getIntExtra("id", 0)

        streamViewModel?.getMovieDetails(authToken, streamId!!, isFeatured)

        streamViewModel?.movieDetailsData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        detailsData = it.data.items?.data

                        val similarData = it.data.items?.similar
                        val categoryAdapter = CategoryListItemAdapter(this, similarData!!, this)
                        binding?.rvSimilar?.adapter = categoryAdapter


                        binding?.layoutMain?.visibility = View.VISIBLE
                        binding?.layoutMain2?.visibility = View.VISIBLE
                        if (detailsData != null) {
                            setDetails(detailsData!!)
                        }else{
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "No data found"
//                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
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
                binding?.shimmerContainer2?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()
                binding?.shimmerContainer2?.stopShimmer()
            }
        }
    }

    private fun setDetails(detailsData: MoviesData) {
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

        if (detailsData.duration.isNullOrEmpty()){
            binding?.tvSelectedTime?.text = notAvailable
        }else{
            binding?.tvSelectedTime?.text = detailsData.duration
        }

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


        if (!detailsData.cover_big.isNullOrEmpty()){
            imageUrl = detailsData.cover_big!!
        }else if (!detailsData.movie_image.isNullOrEmpty()){
            imageUrl = detailsData.movie_image!!
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

        detailsData.isFavourite?.let {
            if (it){
                binding?.ibLike?.setImageResource(R.drawable.ic_heart_color)
            }else{
                binding?.ibLike?.setImageResource(R.drawable.ic_heart)
            }
        }
    }

    private fun setLikeUnlike(){
        detailsData?.let {
            val authConfig = AuthConfig(this)
            val authToken = "Bearer " + authConfig.getToken()
            val favDetails = HashMap<String, Any>()
//            favDetails["cover"] = imageUrl
//            favDetails["id"] = intent.getIntExtra("id", 0)
//            favDetails["type"] = "MOVIES"

            if(detailsData?.isFavourite!!) {
                Log.i("Series", "is Favourite")
                favDetails["id"] = intent.getIntExtra("id", 0)
                favDetails["forRemove"] = true
                favDetails["isFeatured"] = isFeatured
            }else{
                Log.i("Series", "is not Favourite")
                favDetails["cover"] = imageUrl
                favDetails["id"] = intent.getIntExtra("id", 0)
                favDetails["type"] = "MOVIES"
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
                                detailsData?.isFavourite = true
                            }else if (subCode == 200){
                                binding?.ibLike?.setImageResource(R.drawable.ic_heart)
                                detailsData?.isFavourite = false
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

    override fun onItemClick(categoryItems: CategoryItems) {
        val intent = Intent(this, VideoSelectedActivity::class.java)
        intent.putExtra("type", categoryItems.type)
        intent.putExtra("id", categoryItems.id)
        intent.putExtra("FromHome", false)
        startActivity(intent)
    }
}