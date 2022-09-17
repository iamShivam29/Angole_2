package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.angole.databinding.ActivityEditProfileBinding

class EditProfile : AppCompatActivity() {
    private var binding: ActivityEditProfileBinding? = null

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
}