package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.android.angole.databinding.ActivityEditProfileBinding
import com.android.angole.models.AvatarItems
import com.android.angole.utils.Constants
import com.bumptech.glide.Glide

class EditProfileActivity : AppCompatActivity() {
    private var binding: ActivityEditProfileBinding? = null
    private var avatarItem: AvatarItems? = null
    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK){
                val imageId = it.data?.getStringExtra("imageId")
                val imageUrl = it.data?.getStringExtra("imageUrl")
                imageUrl?.let {url ->
                    loadImage(url)
                }
            }
        }

        binding?.btnSelectAvatar?.setOnClickListener{
            val intent = Intent(this, SelectAvatarActivity::class.java)
            activityResultLauncher?.launch(intent)
        }
    }

    private fun loadImage(url: String){
        val fullUrl = Constants.BASE_URL+url

        Glide.with(this)
            .load(fullUrl)
            .into(binding?.ivProfile!!)
    }
}