package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityVideoSelectedBinding
import com.android.angole.models.MovieItems
import com.android.angole.viewmodels.StreamViewModel
import com.bumptech.glide.Glide

class VideoSelectedActivity : AppCompatActivity() {
    private var binding: ActivityVideoSelectedBinding? = null
    private var streamViewModel: StreamViewModel? = null
    private var streamId: Int? = null
    private var detailsData: MovieItems? = null
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoSelectedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        streamViewModel = ViewModelProvider(this)[StreamViewModel::class.java]

        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer2?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()
        binding?.shimmerContainer2?.startShimmer()

        loadData()

        binding?.ibPlaySelected?.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            startActivity(intent)
        }

        binding?.ibLike?.setOnClickListener {
            setLikeUnlike()
        }
    }

    private fun loadData(){
        val token = AuthConfig(this).getToken()
        val authToken = "Bearer $token"

        streamId = intent.getIntExtra("id", 0)
        streamViewModel?.getMovieDetails(authToken, streamId!!)

        streamViewModel?.movieDetailsData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        detailsData = it.data.items

                        binding?.layoutMain?.visibility = View.VISIBLE
                        if (detailsData != null) {
                            setDetails(detailsData!!)
                        }else{
                            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
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

    private fun setDetails(detailsData: MovieItems) {
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
            favDetails["cover"] = imageUrl
            favDetails["id"] = intent.getIntExtra("id", 0)
            favDetails["type"] = "MOVIES"

            streamViewModel?.setLikeUnlike(authToken, favDetails)
            streamViewModel?.favData?.observe(this){
                it?.let {
                    if (it.data != null){
                        val status = it.data.status
                        val subCode = it.data.subCode
                        if (status){
                            if (subCode == 201){
                                binding?.ibLike?.setImageResource(R.drawable.ic_heart_color)
                            }else{
                                binding?.ibLike?.setImageResource(R.drawable.ic_heart)
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
}