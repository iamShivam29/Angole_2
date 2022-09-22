package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.angole.databinding.ActivityEditProfileBinding
import com.android.angole.models.AvatarItems
import com.android.angole.utils.Constants
import com.android.angole.utils.ImageSelectedSingleton.avatarItem
import com.bumptech.glide.Glide

class EditProfile : AppCompatActivity() {
    private var binding: ActivityEditProfileBinding? = null
    private var avatarItem: AvatarItems? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.btnSelectAvatar?.setOnClickListener{
            startActivity(Intent(this, SelectAvatarActivity::class.java))
        }
    }

    private fun loadImage(avatarItem: AvatarItems){
        val url = Constants.BASE_URL+avatarItem.url
        Log.i("Edit", "Url is $url")
        Glide.with(this)
            .load(url)
            .into(binding?.ivProfile!!)
    }

    override fun onResume() {
        super.onResume()
        Log.i("Edit", "On Resume")
        avatarItem?.let {
            loadImage(it)
            avatarItem = it
            avatarItem = null
        }
    }
}