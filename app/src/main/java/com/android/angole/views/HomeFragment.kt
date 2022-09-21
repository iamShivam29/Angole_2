package com.android.angole.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.android.angole.databinding.FragmentHomeBinding
import com.android.angole.models.HomeItems
import com.android.angole.viewmodels.StreamViewModel

class HomeFragment : Fragment(), CategoryListItemAdapter.OnMainClick, MainRecyclerAdapter.OnClickEvent {
    private var binding: FragmentHomeBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var homeAdapter: MainRecyclerAdapter? = null
    private var homeData = listOf<HomeItems>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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

        binding?.ibPlay1?.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            startActivity(intent)
        }

        binding?.ibPlay2?.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            startActivity(intent)
        }

        binding?.ibPlay3?.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            startActivity(intent)
        }

        binding?.ibPlay4?.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            startActivity(intent)
        }

        binding?.ibPlay5?.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            startActivity(intent)
        }
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
        streamViewModel?.getHome(authToken)

        streamViewModel?.homeData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode

                    if (subCode == 200){
                        if(it.data.items != null) {
                            homeData = it.data.items
                        }

                        if (homeData.isNotEmpty()){
                            homeAdapter = MainRecyclerAdapter(requireContext(), homeData, this, this)
                            binding?.rvHome?.adapter = homeAdapter
                        }else{
                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if (subCode == 404){
                        Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
                    }
                    else{
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
        if (type == "SHOWS"){
            val intent = Intent(requireContext(), SeriesVideoSelectedActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }else if (type == "MOVIES"){
            val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    override fun onItemClick(homeItems: HomeItems) {
        val intent = Intent(requireContext(), SeeMoreActivity::class.java)
        startActivity(intent)
    }
}