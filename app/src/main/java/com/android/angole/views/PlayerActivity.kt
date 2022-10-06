package com.android.angole.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.angole.databinding.ActivityPlayerBinding
import com.android.angole.databinding.PlayerControllerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.analytics.PlaybackStatsListener
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerControlView

class PlayerActivity : AppCompatActivity() {
    private var binding: ActivityPlayerBinding? = null
    private var exoPlayer: ExoPlayer? = null
    private var playbackStateListener: Player.Listener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        val videoUri = intent.getStringExtra("videoUri")
        val videoName = intent.getStringExtra("videoName")

//        val inflater = LayoutInflater.from(this)
//        val controllerBinding = PlayerControllerBinding.inflate(inflater)

//        binding?.playerView.player

        binding?.ibBack?.setOnClickListener {
            onBackPressed()
        }

        binding?.tvVideoName?.text = videoName

        exoPlayer = ExoPlayer.Builder(this)
            .setSeekBackIncrementMs(30000)
            .setSeekForwardIncrementMs(30000)
            .build()

        binding?.playerView?.player = exoPlayer
        binding?.playerView?.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

        val mediaItem = MediaItem.fromUri(videoUri!!)                          // "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
        exoPlayer?.setMediaItem(mediaItem)
        exoPlayer?.prepare()
        exoPlayer?.play()

//        val currentPosition = exoPlayer?.currentPosition
//        exoPlayer?.seekTo(currentPosition!!.toInt(), 30000)

        val duration = exoPlayer?.contentDuration

        binding?.playerView?.setControllerVisibilityListener {visibility ->
            if (visibility == View.VISIBLE){
                binding?.ibBack?.visibility = View.VISIBLE
                binding?.tvVideoName?.visibility = View.VISIBLE
            }else{
                binding?.ibBack?.visibility = View.INVISIBLE
                binding?.tvVideoName?.visibility = View.INVISIBLE
            }
        }

        playbackStateListener = object : Player.Listener{
            override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
                super.onSeekBackIncrementChanged(seekBackIncrementMs)
                Log.i("Player", "Seek to Back")
            }

            override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
                super.onSeekForwardIncrementChanged(seekForwardIncrementMs)
                Log.i("Player", "Seek to forward")
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState){
                    Player.STATE_IDLE -> {
//                        Log.i("Player", "State is Idle")
                        binding?.playerProgress?.visibility = View.VISIBLE
                    }
                    Player.STATE_BUFFERING -> {
                        binding?.playerProgress?.visibility = View.VISIBLE
                    }
                    Player.STATE_READY -> {
//                        Log.i("Player", "State is Ready")
                        binding?.playerProgress?.visibility = View.INVISIBLE
                    }
                    Player.STATE_ENDED -> {
                        binding?.playerProgress?.visibility = View.INVISIBLE
                    }
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Log.i("Player", "Error is ${error.localizedMessage}")
                Toast.makeText(this@PlayerActivity, "Cannot load the video", Toast.LENGTH_SHORT).show()
            }
        }

        exoPlayer?.addListener(playbackStateListener!!)
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.let {exoPlayer ->
            playbackStateListener?.let {
                exoPlayer.removeListener(it)
            }
            exoPlayer.release()
        }
        exoPlayer = null
    }
}