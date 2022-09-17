package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.angole.R
import com.android.angole.adapters.AvatarRecyclerAdapter
import com.android.angole.databinding.ActivitySelectAvatarBinding
import com.android.angole.utils.MarginItemDecoration

class SelectAvatarActivity : AppCompatActivity() {
    private var binding: ActivitySelectAvatarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySelectAvatarBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        val avatarList = listOf("", "", "", "", "", "", "", "", "", "", "", "")

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = GridLayoutManager.VERTICAL
        binding?.rvAvatar?.layoutManager = layoutManager

        val itemDecoration = MarginItemDecoration(16, 3)
        binding?.rvAvatar?.addItemDecoration(itemDecoration)

        val adapter = AvatarRecyclerAdapter(this, avatarList)
        binding?.rvAvatar?.adapter = adapter

        binding?.ibBack?.setOnClickListener {
            finish()
        }
    }
}