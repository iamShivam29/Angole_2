package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.angole.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private var binding: ActivityProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}