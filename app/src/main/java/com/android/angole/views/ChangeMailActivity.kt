package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.angole.databinding.ActivityChangeMailBinding

class ChangeMailActivity : AppCompatActivity() {
    private var binding: ActivityChangeMailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeMailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}