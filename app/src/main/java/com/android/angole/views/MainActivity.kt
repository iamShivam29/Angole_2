package com.android.angole.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.angole.R
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        loadFragment(HomeFragment())

        binding?.lvHome?.setOnClickListener{
            loadFragment(HomeFragment())
            makeLightIcons()

            binding?.ibHome?.setImageResource(R.drawable.ic_round_home)
            binding?.tvHome?.setTextColor(Color.WHITE)
        }

        binding?.lvWebShows?.setOnClickListener {
            loadFragment(WebShowsFragment())
            makeLightIcons()

            binding?.ibWebShows?.setImageResource(R.drawable.ic_web_shows)
            binding?.tvWebShows?.setTextColor(Color.WHITE)
        }

        binding?.lvMovies?.setOnClickListener {
            loadFragment(MoviesFragment())
            makeLightIcons()

            binding?.ibMovies?.setImageResource(R.drawable.ic_movies)
            binding?.tvMovies?.setTextColor(Color.WHITE)
        }

        binding?.lvLiveTv?.setOnClickListener {
            loadFragment(LiveTvFragment())
            makeLightIcons()

            binding?.ibLiveTv?.setImageResource(R.drawable.ic_live_tv)
            binding?.tvLiveTv?.setTextColor(Color.WHITE)
        }

        binding?.lvMore?.setOnClickListener {
            loadFragment(MoreFragment())
            makeLightIcons()

            binding?.ibMore?.setImageResource(R.drawable.ic_more)
            binding?.tvMore?.setTextColor(Color.WHITE)
        }
    }

    private fun loadFragment(fragment: Fragment?){
        fragment?.let {
            supportFragmentManager.beginTransaction().replace(R.id.flMain, it).commit()
        }
    }

    private fun makeLightIcons(){
        binding?.ibHome?.setImageResource(R.drawable.ic_round_home_unselected)
        binding?.ibWebShows?.setImageResource(R.drawable.ic_web_show_light)
        binding?.ibMovies?.setImageResource(R.drawable.ic_movies_light)
        binding?.ibLiveTv?.setImageResource(R.drawable.ic_live_tv_light)
        binding?.ibMore?.setImageResource(R.drawable.ic_more_light)

        binding?.tvHome?.setTextColor(Color.parseColor("#989898"))
        binding?.tvWebShows?.setTextColor(Color.parseColor("#989898"))
        binding?.tvMovies?.setTextColor(Color.parseColor("#989898"))
        binding?.tvLiveTv?.setTextColor(Color.parseColor("#989898"))
        binding?.tvMore?.setTextColor(Color.parseColor("#989898"))
    }
}