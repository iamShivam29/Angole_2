package com.android.angole.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.angole.R
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivityWelcomeBinding

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
        looperForFlipper()

        binding?.btnGetStarted?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
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
}