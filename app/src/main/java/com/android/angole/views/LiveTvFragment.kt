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
import com.android.angole.databinding.FragmentLiveTvBinding
import com.android.angole.models.CategoryData
import com.android.angole.models.CategoryItems
import com.android.angole.models.HomeItems
import com.android.angole.utils.Constants
import com.android.angole.viewmodels.StreamViewModel

class LiveTvFragment : Fragment(), CategoryListItemAdapter.OnMainClick, MainRecyclerAdapter.OnClickEvent {
    private var binding: FragmentLiveTvBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var liveTvAdapter: MainRecyclerAdapter? = null
    private var liveTvData = listOf<CategoryData>()
    private var isLooping = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentLiveTvBinding.inflate(inflater, container, false)

        initView()
        return binding?.root
    }

    private fun initView(){
//        binding?.mainItems?.visibility = View.VISIBLE
        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()

//        looperForFlipper()

        loadData()

        handler = Handler(Looper.getMainLooper())

        binding?.btnRetry?.setOnClickListener {
            loadData()

//            binding?.mainItems?.visibility = View.VISIBLE
            binding?.shimmerContainer?.visibility = View.VISIBLE
            binding?.shimmerContainer?.startShimmer()

            binding?.lvInfo?.visibility = View.GONE
        }
    }

    private fun looperForFlipper(){
        handler?.postDelayed({
            activity?.isFinishing?.let {
                if (!it) {
                    binding?.flipper?.setInAnimation(requireContext(), R.anim.fade_in)
                    binding?.flipper?.setOutAnimation(requireContext(), R.anim.fade_out)

                    binding?.flipper?.showNext()
                    looperForFlipper()
                }
            }
        }, 5000)
    }

    private fun loadData(){
        streamViewModel = ViewModelProvider(requireActivity())[StreamViewModel::class.java]

        val token = AuthConfig(requireContext()).getToken()
        val authToken = "Bearer $token"
        streamViewModel?.getLiveTv(authToken)

        streamViewModel?.liveData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode

                    if (subCode == 200){
                        if(it.data.items != null) {
                            liveTvData = it.data.items!!
                        }

                        if (liveTvData.isNotEmpty()){
                            liveTvAdapter = MainRecyclerAdapter(requireContext(), liveTvData, this,this)
                            binding?.rvMovies?.adapter = liveTvAdapter
                            binding?.rvMovies?.visibility = View.VISIBLE
                            binding?.flipper?.visibility = View.VISIBLE
                        }else{
                            Log.i("Live", "live tv data is empty ${liveTvData.isEmpty()}")

//                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()

//                            binding?.mainItems?.visibility = View.GONE
                            binding?.flipper?.visibility = View.GONE
                            binding?.rvMovies?.visibility = View.GONE
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "No data found"

                            streamViewModel?.liveData?.removeObservers(viewLifecycleOwner)
                            streamViewModel?.liveData?.value = null
                        }
                    }else if (subCode == 404){
//                        Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()

//                        binding?.mainItems?.visibility = View.GONE
                        binding?.flipper?.visibility = View.GONE
                        binding?.rvMovies?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "No data found"

                        streamViewModel?.liveData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.liveData?.value = null
                    }else{
//                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

//                        binding?.mainItems?.visibility = View.GONE
                        binding?.flipper?.visibility = View.GONE
                        binding?.rvMovies?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"

                        streamViewModel?.liveData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.liveData?.value = null
                    }

                }else{
//                    if (!it.message.isNullOrEmpty()) {
//                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }

//                    binding?.mainItems?.visibility = View.GONE
                    binding?.flipper?.visibility = View.GONE
                    binding?.rvMovies?.visibility = View.GONE
                    binding?.lvInfo?.visibility = View.VISIBLE
                    binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                    binding?.tvInfo?.text = "Something went wrong"

                    streamViewModel?.liveData?.removeObservers(viewLifecycleOwner)
                    streamViewModel?.liveData?.value = null
                }

                binding?.shimmerContainer?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()

//                streamViewModel?.liveData?.removeObservers(viewLifecycleOwner)
//                streamViewModel?.liveData?.value = null
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!isLooping) {
            looperForFlipper()
            isLooping = true
        }
    }

    override fun onItemClick(categoryItems: CategoryItems) {
        val intent = Intent(requireContext(), PlayerActivity::class.java)
        intent.putExtra("videoUri", categoryItems.playableUrl)
        intent.putExtra("videoName", categoryItems.name)
        startActivity(intent)
    }

    override fun onItemClick(categoryData: CategoryData) {
        val intent = Intent(requireContext(), SeeMoreActivity::class.java)
        intent.putExtra("categoryId", categoryData.categoryId)
        intent.putExtra("categoryName", categoryData.categoryName)
        intent.putExtra("from", Constants.FROM_LIVETV)
        startActivity(intent)
    }
}