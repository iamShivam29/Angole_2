package com.android.angole.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.angole.databinding.FragmentMoreBinding

class MoreFragment : Fragment() {
    private var binding: FragmentMoreBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentMoreBinding.inflate(inflater)

        initView()
        return binding?.root
    }

    private fun initView(){
        binding?.lvAccount?.setOnClickListener {
            startActivity(Intent(requireContext(), AccountActivity::class.java))
        }

        binding?.lvEditProfile?.setOnClickListener {
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        }

        binding?.lvSignOut?.setOnClickListener {
            startActivity(Intent(requireContext(), SignUpActivity::class.java))
        }
    }
}