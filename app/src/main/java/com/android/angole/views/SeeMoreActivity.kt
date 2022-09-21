package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.android.angole.adapters.MoreItemsRecyclerAdapter
import com.android.angole.databinding.ActivitySeeMoreBinding
import com.android.angole.utils.MarginItemDecoration

class SeeMoreActivity : AppCompatActivity() {
    var binding: ActivitySeeMoreBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeMoreBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        val list = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        val adapter = MoreItemsRecyclerAdapter(this, list)
        val itemDecoration = MarginItemDecoration(16, 3, GridLayoutManager.VERTICAL)
        binding?.rvSeeMore?.addItemDecoration(itemDecoration)
        binding?.rvSeeMore?.adapter = adapter
    }
}