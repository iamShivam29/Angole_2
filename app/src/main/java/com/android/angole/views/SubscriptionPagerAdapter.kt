package com.android.angole.views

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.angole.views.MonthlyFragment
import com.android.angole.views.QuarterlyFragment
import com.android.angole.views.YearlyFragment

class SubscriptionPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return MonthlyFragment()
        }else if (position == 1){
            return QuarterlyFragment()
        }

        return YearlyFragment()
    }
}