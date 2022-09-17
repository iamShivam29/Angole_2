package com.android.angole.views

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.android.angole.R
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.AlertDialogLayoutBinding
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
            startActivity(Intent(requireContext(), EditProfile::class.java))
        }

        binding?.lvSignOut?.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog(){
        val inflater = LayoutInflater.from(requireContext())
        val binding = AlertDialogLayoutBinding.inflate(inflater)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()

        alertDialog.show()

//        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.tvTitle.text = "Confirm Sign Out"
        binding.tvMessage.text = "Are you sure you want to sign out from angola."
        binding.btnConfirm.setOnClickListener {
            val authConfig = AuthConfig(requireContext())
            authConfig.setProfile("", false)

            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}