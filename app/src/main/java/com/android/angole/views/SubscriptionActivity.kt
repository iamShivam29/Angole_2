package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.android.angole.databinding.ActivitySubscriptionBinding

class SubscriptionActivity : AppCompatActivity() {
    private var binding: ActivitySubscriptionBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySubscriptionBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        binding?.subscriptionViewPager?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> showMonthly()
                    1 -> showQuarterly()
                    2 -> showYearly()
                }
            }
        })

        val pagerAdapter: FragmentStateAdapter = SubscriptionPagerAdapter(supportFragmentManager, lifecycle)
        binding?.subscriptionViewPager?.adapter = pagerAdapter

        binding?.tvMonthly?.setOnClickListener {
            showMonthly()
            binding?.subscriptionViewPager?.currentItem = 0
        }

        binding?.tvQuarterly?.setOnClickListener {
            showQuarterly()
            binding?.subscriptionViewPager?.currentItem = 1
        }

        binding?.tvYearly?.setOnClickListener {
            showYearly()
            binding?.subscriptionViewPager?.currentItem = 2
        }
    }

    private fun showYearly() {
        binding?.tvMonthly?.alpha = 0.25f
        binding?.tvQuarterly?.alpha = 0.25f
        binding?.tvYearly?.alpha = 1f
    }

    private fun showQuarterly() {
        binding?.tvMonthly?.alpha = 0.25f
        binding?.tvQuarterly?.alpha = 1f
        binding?.tvYearly?.alpha = 0.25f
    }

    private fun showMonthly() {
        binding?.tvMonthly?.alpha = 1f
        binding?.tvQuarterly?.alpha = 0.25f
        binding?.tvYearly?.alpha = 0.25f
    }
}