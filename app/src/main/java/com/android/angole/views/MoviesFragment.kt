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
import com.android.angole.databinding.FragmentMoviesBinding
import com.android.angole.models.HomeItems
import com.android.angole.viewmodels.StreamViewModel

class MoviesFragment : Fragment(), CategoryListItemAdapter.OnMainClick, MainRecyclerAdapter.OnClickEvent {
    private var binding: FragmentMoviesBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var moviesAdapter: MainRecyclerAdapter? = null
    private var moviesData = listOf<HomeItems>()
    private var isLooping = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        initView()
        return binding?.root
    }

    private fun initView(){
        streamViewModel = ViewModelProvider(requireActivity())[StreamViewModel::class.java]
        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()

        looperForFlipper()

        loadData()

        handler = Handler(Looper.getMainLooper())
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
        val token = AuthConfig(requireContext()).getToken()
        val authToken = "Bearer $token"
        streamViewModel?.getMovies(authToken)

        streamViewModel?.moviesData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        if(it.data.items != null) {
                            moviesData = it.data.items
                        }

                        if (moviesData.isNotEmpty()){
                            moviesAdapter = MainRecyclerAdapter(requireContext(), moviesData, this, this)
                            binding?.rvMovies?.adapter = moviesAdapter
                        }else{
                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
                        }
                    }else if (subCode == 404){
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

        if (!isLooping) {
            looperForFlipper()
            isLooping = true
        }
    }

    override fun onItemClick(type: String, id: Int) {
        val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
        intent.putExtra("type", type)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onItemClick(homeItems: HomeItems) {
        TODO("Not yet implemented")
    }
}