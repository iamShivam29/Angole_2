package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivitySplashBinding
import com.android.angole.utils.OnBoardSingleton.onBoardList
import com.android.angole.viewmodels.StreamViewModel
import com.google.android.material.snackbar.Snackbar

class SplashActivity : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null
    private var streamViewMode: StreamViewModel? = null
//    private var onBoardMap = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        streamViewMode = ViewModelProvider(this)[StreamViewModel::class.java]
        loadData()
    }

    private fun loadData(){
        val authConfig = AuthConfig(this)
        val authToken = "Bearer " + authConfig.getToken()

        streamViewMode?.getSuggestionList(authToken)
        streamViewMode?.suggestionData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if(subCode == 200) {
                        val suggestionList = it.data.items

                        suggestionList?.let { items ->
                            for (item in items) {
                                val onBoardMap = HashMap<String, String>()
                                onBoardMap["posterLink"] = item.posterLink!!
                                onBoardMap["headLine"] = item.headLine!!
                                onBoardMap["subtitle"] = item.subtitle!!
                                onBoardList.add(onBoardMap)
                            }

                            val intent = Intent(this, WelcomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }else{
                        val snackBar = Snackbar.make(binding?.root!!, "Something went wrong", Snackbar.LENGTH_SHORT)
                        snackBar.setAction("Retry"){
                            loadData()
                        }
                        snackBar.show()
                    }
                }else{
                    val snackBar = Snackbar.make(binding?.root!!, "Something went wrong", Snackbar.LENGTH_SHORT)
                    snackBar.setAction("Retry"){
                        loadData()
                    }
                    snackBar.show()
                }
            }
        }

    }
}