package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.android.angole.databinding.ActivityPlayerBinding
import com.android.angole.databinding.PlayerControllerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        val videoUri = intent.getStringExtra("videoUri")
        val videoName = intent.getStringExtra("videoName")

        val inflater = LayoutInflater.from(this)
        val controllerBinding = PlayerControllerBinding.inflate(inflater)

        controllerBinding.ibBack.setOnClickListener {

        }

        controllerBinding.tvVideoName.text = videoName

        exoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(10000)
            .setSeekForwardIncrementMs(10000)
            .build()

        binding?.playerView?.player = exoPlayer
        binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

        val mediaItem = MediaItem.fromUri("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4")
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.play()

//        val currentPosition = exoPlayer?.currentPosition
//        exoPlayer?.seekTo(currentPosition!!.toInt(), 30000)

        val duration = exoPlayer?.contentDuration

        exoPlayer?.addListener(object : Player.Listener{
            override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
                super.onSeekBackIncrementChanged(seekBackIncrementMs)
                Log.i("Player", "Seek to Back")
            }

            override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
                super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
                Log.i("Player", "Seek to forward")
            }
        })
    }
}