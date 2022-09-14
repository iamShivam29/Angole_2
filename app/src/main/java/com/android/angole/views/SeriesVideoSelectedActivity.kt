package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.angole.databinding.ActivitySeriesVideoSelectedBinding

class SeriesVideoSelectedActivity : AppCompatActivity() {
    private var binding: ActivitySeriesVideoSelectedBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeriesVideoSelectedBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}