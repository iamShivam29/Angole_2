package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import com.android.angole.R
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityWelcomeBinding
import com.android.angole.utils.OnBoardSingleton.onBoardList
import com.bumptech.glide.Glide

class WelcomeActivity : AppCompatActivity() {
    private var binding: ActivityWelcomeBinding? = null
    private var position = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val authConfig = AuthConfig(this)
        val isLogin = authConfig.getIsLogin()
        if (isLogin) {
            initView()
        }else{
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initView(){
        setData()
        looperForFlipper()

        binding?.btnGetStarted?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun looperForFlipper(){
        if (position < 4){
            Handler(Looper.getMainLooper()).postDelayed({
                binding?.flipper?.setInAnimation(this, R.anim.slide_right)
                binding?.flipper?.setOutAnimation(this, R.anim.slide_left)
                position += 1

                binding?.flipper?.showNext()
                changeTheDots()
                looperForFlipper()
            }, 2000)
        }
    }

    private fun changeTheDots() {
        binding?.dot1?.setImageResource(R.drawable.dot_disabled)
        binding?.dot2?.setImageResource(R.drawable.dot_disabled)
        binding?.dot3?.setImageResource(R.drawable.dot_disabled)
        binding?.dot4?.setImageResource(R.drawable.dot_disabled)

        when(position){
            1 -> {
                binding?.dot1?.setImageResource(R.drawable.dot_enabled)
            }

            2 -> {
                binding?.dot2?.setImageResource(R.drawable.dot_enabled)
            }

            3 -> {
                binding?.dot3?.setImageResource(R.drawable.dot_enabled)
            }

            4 -> {
                binding?.dot4?.setImageResource(R.drawable.dot_enabled)
            }
        }
    }

    private fun setData(){
        setImage(binding?.image1!!, onBoardList[0]["posterLink"]!!)
        setImage(binding?.image2!!, onBoardList[1]["posterLink"]!!)
        setImage(binding?.image3!!, onBoardList[2]["posterLink"]!!)
        setImage(binding?.image4!!, onBoardList[3]["posterLink"]!!)

        setHeadline(binding?.welcomeTitle1!!, onBoardList[0]["headLine"]!!)
        setHeadline(binding?.welcomeTitle2!!, onBoardList[1]["headLine"]!!)
        setHeadline(binding?.welcomeTitle3!!, onBoardList[2]["headLine"]!!)
        setHeadline(binding?.welcomeTitle4!!, onBoardList[3]["headLine"]!!)

        setSubtitle(binding?.welcomeSubTitle1!!, onBoardList[0]["subtitle"]!!)
        setSubtitle(binding?.welcomeSubTitle2!!, onBoardList[1]["subtitle"]!!)
        setSubtitle(binding?.welcomeSubTitle3!!, onBoardList[2]["subtitle"]!!)
        setSubtitle(binding?.welcomeSubTitle4!!, onBoardList[3]["subtitle"]!!)
    }

    private fun setImage(view: ImageView, url: String){
        Glide.with(this)
            .load(url)
            .into(view)
    }

    private fun setHeadline(view: TextView, headline: String){
        view.text = headline
    }

    private fun setSubtitle(view: TextView, subtitle: String){
        view.text = subtitle
    }
}