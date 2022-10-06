package com.android.angole.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.android.angole.R
import com.android.angole.adapters.CategoryListItemAdapter
import com.android.angole.adapters.GenreRecyclerAdapter
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.FragmentHomeBinding
import com.android.angole.models.*
import com.android.angole.utils.Constants
import com.android.angole.utils.Resource
import com.android.angole.viewmodels.StreamViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment(), CategoryListItemAdapter.OnMainClick, MainRecyclerAdapter.OnClickEvent {
    private var binding: FragmentHomeBinding? = null
    private var handler: Handler? = null
    private var streamViewModel: StreamViewModel? = null
    private var homeAdapter: MainRecyclerAdapter? = null
    private var categoryData = listOf<CategoryData>()
    private var banners: List<CategoryItems>? = null
    private var isLooping = false
    private var isMyListLoaded = false
    private var isMainDataLoaded = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initView()
        return binding?.root
    }

    private fun initView(){
//        binding?.mainItems?.visibility = View.VISIBLE
        binding?.shimmerContainer?.visibility = View.VISIBLE
        binding?.shimmerContainer?.startShimmer()

        loadMyListData()
        loadData()

        handler = Handler(Looper.getMainLooper())


        binding?.tvSuggestion1?.setOnClickListener {
//            val intent = Intent(requireContext(), PlayerActivity::class.java)
//            startActivity(intent)
            banners?.let {
                moveToDetails(it[0])
            }
        }

        binding?.tvSuggestion2?.setOnClickListener {
//            val intent = Intent(requireContext(), PlayerActivity::class.java)
//            startActivity(intent)

            banners?.let {
                moveToDetails(it[1])
            }
        }

        binding?.tvSuggestion3?.setOnClickListener {
//            val intent = Intent(requireContext(), PlayerActivity::class.java)
//            startActivity(intent)

            banners?.let {
                moveToDetails(it[2])
            }
        }

        binding?.tvSuggestion4?.setOnClickListener {
//            val intent = Intent(requireContext(), PlayerActivity::class.java)
//            startActivity(intent)

            banners?.let {
                moveToDetails(it[3])
            }
        }

        binding?.tvSuggestion5?.setOnClickListener {
//            val intent = Intent(requireContext(), PlayerActivity::class.java)
//            startActivity(intent)

            banners?.let {
                moveToDetails(it[4])
            }
        }

        binding?.btnRetry?.setOnClickListener {
            loadMyListData()
            loadData()

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
        streamViewModel?.getHome(authToken)

        streamViewModel?.homeData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){

                        if(it.data.items != null) {
                            banners = it.data.items!!.banners
                            setFlipperData(banners!!)
                            looperForFlipper()
                            isLooping = true

                            categoryData = it.data.items!!.categoryData!!
                        }

                        if (categoryData.isNotEmpty()){
                            isMainDataLoaded = true
                            homeAdapter = MainRecyclerAdapter(requireContext(), categoryData, this, this)
                            binding?.rvHome?.adapter = homeAdapter
//                            if (isMyListLoaded) {
//
//                            }

                            binding?.rvHome?.visibility = View.VISIBLE
                            binding?.flipper?.visibility = View.VISIBLE
                            if (isMyListLoaded) {
                                binding?.sectionMyList?.visibility = View.VISIBLE
                            }

                        }else{
//                            Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
//                            binding?.mainItems?.visibility = View.GONE
                            binding?.flipper?.visibility = View.GONE
                            binding?.rvHome?.visibility = View.GONE
                            binding?.sectionMyList?.visibility = View.GONE
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "No data found"

                            streamViewModel?.homeData?.removeObservers(viewLifecycleOwner)
                            streamViewModel?.homeData?.value = null
                        }
                    }
                    else if (subCode == 404){
//                        Toast.makeText(requireContext(), "Data Not found", Toast.LENGTH_SHORT).show()
//                        binding?.mainItems?.visibility = View.GONE

                        binding?.flipper?.visibility = View.GONE
                        binding?.rvHome?.visibility = View.GONE
                        binding?.sectionMyList?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "No data found"

                        streamViewModel?.homeData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.homeData?.value = null
                    }
                    else{
//                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()

//                        binding?.mainItems?.visibility = View.GONE

                        Log.i("Home", "subcode is $subCode with message ${it.data.message}")

                        binding?.flipper?.visibility = View.GONE
                        binding?.rvHome?.visibility = View.GONE
                        binding?.sectionMyList?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"

                        streamViewModel?.homeData?.removeObservers(viewLifecycleOwner)
                        streamViewModel?.homeData?.value = null
                    }

                }else{
//                    if (!it.message.isNullOrEmpty()) {
//                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }

//                    binding?.mainItems?.visibility = View.GONE

                    Log.i("Home", "Error is ${it.message}")

                    binding?.flipper?.visibility = View.GONE
                    binding?.rvHome?.visibility = View.GONE
                    binding?.sectionMyList?.visibility = View.GONE
                    binding?.lvInfo?.visibility = View.VISIBLE
                    binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                    binding?.tvInfo?.text = "Something went wrong"

                    streamViewModel?.homeData?.removeObservers(viewLifecycleOwner)
                    streamViewModel?.homeData?.value = null
                }

                binding?.shimmerContainer?.visibility = View.GONE
                binding?.shimmerContainer?.stopShimmer()

//                streamViewModel?.homeData?.removeObservers(viewLifecycleOwner)
//                streamViewModel?.homeData?.value = null
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
        streamViewModel?.getMyHomeList(authToken)

        streamViewModel?.myHomeListData?.observe(viewLifecycleOwner){
            it?.let {
                if (it.data != null) {
                    val subCode = it.data.subCode
                    if (subCode == 200) {
                        it.data.items?.let { myListItems ->
                            if (myListItems.isNotEmpty()) {
                                setMyListData(myListItems)
                                isMyListLoaded = true
                                if (isMainDataLoaded){
                                    binding?.rvHome?.visibility = View.VISIBLE
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
//        if (categoryItems.type == "SHOWS"){
//            val intent = Intent(requireContext(), SeriesVideoSelectedActivity::class.java)
//            intent.putExtra("id", categoryItems.id)
//            intent.putExtra("FromHome", true)
//            startActivity(intent)
//
//        }else if (categoryItems.type == "MOVIES"){
//            val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
//            intent.putExtra("id", categoryItems.id)
//            intent.putExtra("FromHome", true)
//            startActivity(intent)
//        }

        moveToDetails(categoryItems)
    }

    private fun moveToDetails(categoryItems: CategoryItems){
        if (categoryItems.type == "SHOWS"){
            val intent = Intent(requireContext(), SeriesVideoSelectedActivity::class.java)
            intent.putExtra("id", categoryItems.id)
            intent.putExtra("FromHome", true)
            startActivity(intent)

        }else if (categoryItems.type == "MOVIES"){
            val intent = Intent(requireContext(), VideoSelectedActivity::class.java)
            intent.putExtra("id", categoryItems.id)
            intent.putExtra("FromHome", true)
            startActivity(intent)
        }
    }

    override fun onItemClick(categoryData: CategoryData) {
        var from = ""
        if (categoryData.categoryType == "SHOWS"){
            from = Constants.FROM_SERIES
        }else if (categoryData.categoryType == "MOVIES"){
            from = Constants.FROM_MOVIES
        }

        val intent = Intent(requireContext(), SeeMoreActivity::class.java)
        intent.putExtra("categoryId", categoryData.categoryId)
        intent.putExtra("categoryName", categoryData.categoryName)
        intent.putExtra("from", from)
        startActivity(intent)
    }
}