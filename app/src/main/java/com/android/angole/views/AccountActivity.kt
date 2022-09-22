package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.angole.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private var binding: ActivityAccountBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.tvSubscription?.setOnClickListener {
            startActivity(Intent(this, SubscriptionActivity::class.java))
        }

        binding?.tvChangePassword?.setOnClickListener {
            startActivity(Intent(this, ChangeDetailsActivity::class.java))
        }
    }

}