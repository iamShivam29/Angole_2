package com.android.angole.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.adapters.CategoryListItemAdapter
import com.android.angole.adapters.GenreRecyclerAdapter
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.FragmentMoviesBinding
import com.android.angole.models.BannersData
import com.android.angole.models.CategoryData
import com.android.angole.models.CategoryItems
import com.android.angole.utils.Constants
import com.android.angole.viewmodels.StreamViewModel
import com.bumptech.glide.Glide

class MoviesFragment : Fragment(), CategoryListItemAdapter.OnMainClick, MainRecyclerAdapter.OnClickEvent {
    private var binding: FragmentMoviesBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var moviesAdapter: MainRecyclerAdapter? = null
    private var moviesData = listOf<CategoryData>()
    private var banners: List<CategoryItems>? = null
    private var isLooping = false
    private var isMyListLoaded = false
    private var isMainDataLoaded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        initView()
        return binding?.root
    }

    private fun initView(){
//        binding?.mainItems?.visibility = View.VISIBLE
        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()

//        looperForFlipper()

        loadMyListData()
        loadData()

        handler = Handler(Looper.getMainLooper())

        binding?.btnRetry?.setOnClickListener {
            loadData()

//            binding?.mainItems?.visibility = View.VISIBLE
            binding?.shimmerContainer?.visibility = View.VISIBLE
            binding?.shimmerContainer?.startShimmer()

            binding?.lvInfo?.visibility = View.GONE
        }

        binding?.tvSuggestion1?.setOnClickListener {
            banners?.let {
                moveToDetails(it[0])
            }
        }

        binding?.tvSuggestion2?.setOnClickListener {
            banners?.let {
                moveToDetails(it[1])
            }
        }

        binding?.tvSuggestion3?.setOnClickListener {
            banners?.let {
                moveToDetails(it[2])
            }
        }

        binding?.tvSuggestion4?.setOnClickListener {
            banners?.let {
                moveToDetails(it[3])
            }
        }

        binding?.tvSuggestion5?.setOnClickListener {
            banners?.let {
                moveToDetails(it[4])
            }
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
        streamViewModel?.getMovies(authToken)

        streamViewModel?.moviesData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        if(it.data.items != null) {
                            banners = it.data.items!!.banners
                            setFlipperData(banners!!)
                            looperForFlipper()
                            isLooping = true

                            moviesData = it.data.items!!.categoryData!!
                        }

                        if (moviesData.isNotEmpty()){
                            isMainDataLoaded = true
                            moviesAdapter = MainRecyclerAdapter(requireContext(), moviesData, this, this)
                            binding?.rvMovies?.adapter = moviesAdapter


                            binding?.rvMovies?.visibility = View.VISIBLE
                            binding?.flipper?.visibility = View.VISIBLE
                            if(isMyListLoaded) {
                                binding?.sectionMyList?.visibility = View.VISIBLE
                            }
                        }else{
//                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
//                            binding?.mainItems?.visibility = View.GONE
                            binding?.flipper?.visibility = View.GONE
                            binding?.rvMovies?.visibility = View.GONE
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "No data found"

                            streamViewModel?.moviesData?.removeObservers(viewLifecycleOwner)
                            streamViewModel?.moviesData?.value = null
                        }

                    }else if (subCode == 404){
//                        Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()

//                        binding?.mainItems?.visibility = View.GONE
                        binding?.flipper?.visibility = View.GONE
                        binding?.rvMovies?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "No data found"

                        streamViewModel?.moviesData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.moviesData?.value = null
                    }
                    else{
//                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                        binding?.mainItems?.visibility = View.GONE
                        binding?.flipper?.visibility = View.GONE
                        binding?.rvMovies?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"

                        streamViewModel?.moviesData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.moviesData?.value = null
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

                    streamViewModel?.moviesData?.removeObservers(viewLifecycleOwner)
                    streamViewModel?.moviesData?.value = null
                }

                binding?.shimmerContainer?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()

//                streamViewModel?.moviesData?.removeObservers(viewLifecycleOwner)
//                streamViewModel?.moviesData?.value = null
            }
        }
    }

    private fun setFlipperData(banners: List<CategoryItems>){
        val cover1 = banners[0].cover
        val backdrop1 = banners[0].backdrop_path
        if (cover1?.isNotEmpty()!!){
            setFlipperImage(cover1, binding?.ivSuggestions1!!)
        }else if (backdrop1?.isNotEmpty()!!){
            setFlipperImage(backdrop1[0], binding?.ivSuggestions1!!)
        }

        val cover2 = banners[1].cover
        val backdrop2 = banners[1].backdrop_path
        if (cover2?.isNotEmpty()!!){
            setFlipperImage(cover2, binding?.ivSuggestions2!!)
        }else if (backdrop2?.isNotEmpty()!!){
            setFlipperImage(backdrop2[0], binding?.ivSuggestions2!!)
        }

        val cover3 = banners[2].cover
        val backdrop3 = banners[2].backdrop_path
        if (cover3?.isNotEmpty()!!){
            setFlipperImage(cover3, binding?.ivSuggestions3!!)
        }else if (backdrop3?.isNotEmpty()!!){
            setFlipperImage(backdrop3[0], binding?.ivSuggestions3!!)
        }

        val cover4 = banners[3].cover
        val backdrop4 = banners[3].backdrop_path
        if (cover4?.isNotEmpty()!!){
            setFlipperImage(cover4, binding?.ivSuggestions4!!)
        }else if (backdrop4?.isNotEmpty()!!){
            setFlipperImage(backdrop4[0], binding?.ivSuggestions4!!)
        }

        val cover5 = banners[4].cover
        val backdrop5 = banners[4].backdrop_path
        if (cover5?.isNotEmpty()!!){
            setFlipperImage(cover5, binding?.ivSuggestions5!!)
        }else if (backdrop5?.isNotEmpty()!!){
            setFlipperImage(backdrop5[0], binding?.ivSuggestions5!!)
        }

        binding?.tvSuggestion1?.text = banners[0].name
        binding?.tvSuggestion2?.text = banners[1].name
        binding?.tvSuggestion3?.text = banners[2].name
        binding?.tvSuggestion4?.text = banners[3].name
        binding?.tvSuggestion5?.text = banners[4].name

        val genreList1 = banners[0].genre?.split(",", " ") as MutableList
        if (genreList1.contains("")){
            genreList1.clear()
        }
        if (genreList1.isNotEmpty()) {
            val adapter1 = GenreRecyclerAdapter(requireContext(), genreList1)
            binding?.rvSuggestion1?.adapter = adapter1
        }


        val genreList2 = banners[1].genre?.split(",", " ") as MutableList
        if (genreList2.contains("")){
            genreList2.clear()
        }
        if (genreList2.isNotEmpty()) {
            val adapter2 = GenreRecyclerAdapter(requireContext(), genreList2)
            binding?.rvSuggestion2?.adapter = adapter2
        }


        val genreList3 = banners[2].genre?.split(",", " ") as MutableList
        if (genreList3.contains("")){
            genreList3.clear()
        }
        if (genreList3.isNotEmpty()) {
            val adapter3 = GenreRecyclerAdapter(requireContext(), genreList3)
            binding?.rvSuggestion3?.adapter = adapter3
        }


        val genreList4 = banners[3].genre?.split(",", " ") as MutableList
        if (genreList4.contains("")){
            genreList4.clear()
        }
        if (genreList4.isNotEmpty()) {
            val adapter4 = GenreRecyclerAdapter(requireContext(), genreList4)
            binding?.rvSuggestion4?.adapter = adapter4
        }


        val genreList5 = banners[4].genre?.split(",", " ") as MutableList
        if (genreList5.contains("")){
            genreList5.clear()
        }
        if (genreList5.isNotEmpty()) {
            val adapter5 = GenreRecyclerAdapter(requireContext(), genreList5)
            binding?.rvSuggestion5?.adapter = adapter5
        }
    }

    private fun setFlipperImage(imageUrl: String, view: ImageView){
        Glide.with(this)
            .load(imageUrl)
            .into(view)
    }

    private fun loadMyListData(){
        streamViewModel = ViewModelProvider(requireActivity())[StreamViewModel::class.java]

        val token = AuthConfig(requireContext()).getToken()
        val authToken = "Bearer $token"
        streamViewModel?.getMyMoviesList(authToken)

        streamViewModel?.myMoviesListData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    if (subCode == 200) {
                        it.data.items?.let { myListItems ->
                            if (myListItems.isNotEmpty()) {
                                setMyListData(myListItems)

                                isMyListLoaded = true
                                if (isMainDataLoaded){
                                    binding?.rvMovies?.visibility = View.VISIBLE
                                    binding?.flipper?.visibility = View.VISIBLE
                                    binding?.sectionMyList?.visibility = View.VISIBLE
                                }
                            }else{
                                binding?.sectionMyList?.visibility = View.GONE
                            }

//                            isMyListLoaded = true
                        }
                    }
                }
            }
        }
    }

    private fun setMyListData(categoryItemList: List<CategoryItems>){
        val categoryAdapter = CategoryListItemAdapter(requireContext(), categoryItemList, this)
        binding?.rvCategoryItem?.adapter = categoryAdapter
    }

    override fun onResume() {
        super.onResume()

        loadMyListData()
//        if (!isLooping) {
//            looperForFlipper()
//            isLooping = true
//        }
    }

    override fun onItemClick(categoryItems: CategoryItems) {
//        val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
//        intent.putExtra("type", categoryItems.type)
//        intent.putExtra("id", categoryItems.id)
//        intent.putExtra("FromHome", false)
//        startActivity(intent)

        moveToDetails(categoryItems)
    }

    private fun moveToDetails(categoryItems: CategoryItems){
        val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
        intent.putExtra("type", categoryItems.type)
        intent.putExtra("id", categoryItems.id)
        intent.putExtra("FromHome", false)
        startActivity(intent)
    }

    override fun onItemClick(categoryData: CategoryData) {
        val intent = Intent(requireContext(), SeeMoreActivity::class.java)
        intent.putExtra("categoryId", categoryData.categoryId)
        intent.putExtra("categoryName", categoryData.categoryName)
        intent.putExtra("from", Constants.FROM_MOVIES)
        startActivity(intent)
    }
}