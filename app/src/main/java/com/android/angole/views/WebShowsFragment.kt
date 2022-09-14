package com.android.angole.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.adapters.CategoryListItemAdapter
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.FragmentWebShowsBinding
import com.android.angole.viewmodels.StreamViewModel

class WebShowsFragment : Fragment(), CategoryListItemAdapter.OnMainClick {
    private var binding: FragmentWebShowsBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var webAdapter: MainRecyclerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentWebShowsBinding.inflate(inflater, container, false)
        initView()
        return binding?.root
    }

    private fun initView(){
        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()
        streamViewModel = ViewModelProvider(requireActivity())[StreamViewModel::class.java]

        looperForFlipper()
        loadData()

        handler = Handler(Looper.getMainLooper())
    }

    private fun looperForFlipper(){
//        if (position < 5){
//
//        }

        handler?.postDelayed({
            activity?.isFinishing?.let {
                if (!it) {
                    binding?.flipper?.setInAnimation(requireContext(), R.anim.fade_in)
                    binding?.flipper?.setOutAnimation(requireContext(), R.anim.fade_out)
//                    position += 1

                    binding?.flipper?.showNext()
                    looperForFlipper()
                }
            }
        }, 5000)
    }

    private fun loadData(){
        val token = AuthConfig(requireContext()).getToken()
        val authToken = "Bearer $token"
        streamViewModel?.getWebShows(authToken)

        streamViewModel?.webData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    val homeData = it.data.items
                    if (subCode == 200){
                        if (homeData.isNotEmpty()){
                            webAdapter = MainRecyclerAdapter(requireContext(), homeData, this)
                            binding?.rvWebShows?.adapter = webAdapter
                        }else{
                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
                        }
                    }else{
//                        if (homeData.isEmpty()) {
//                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
//                        }else{
//                            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                        }

                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    if (!it.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }

                binding?.shimmerContainer?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()
                binding?.flipper?.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()

        looperForFlipper()
    }

    override fun onItemClick(type: String, id: Int) {
//        val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
//        intent.putExtra("type", type)
//        intent.putExtra("id", id)
//        startActivity(intent)
    }
}