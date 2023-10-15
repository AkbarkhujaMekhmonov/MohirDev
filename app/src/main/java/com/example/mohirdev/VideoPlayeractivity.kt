package com.example.mohirdev

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.mohirdev.models.Video
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView

class VideoPlayeractivity : AppCompatActivity() {
    lateinit var videoView: VideoView
    var isFullScreen: Boolean=false
    var isLock=false
    lateinit var bt_fullscreen:ImageView
    lateinit var simpleExoPlayer:SimpleExoPlayer
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_playeractivity)
        val video = intent.extras!!.getSerializable("video") as Video

        val playerView = findViewById<PlayerView>(R.id.player)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
         bt_fullscreen = findViewById<ImageView>(R.id.bt_fullscreen)
        val bt_lockscreen = findViewById<ImageView>(R.id.exo_lock)

        bt_fullscreen.setOnClickListener {
            if(!isFullScreen){
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen_exit_24))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }else{
                bt_fullscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.ic_baseline_fullscreen))
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            isFullScreen=!isFullScreen
        }

        bt_lockscreen.setOnClickListener{
            lockScreen(isLock)

            if(!isLock){
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_baseline_lock_24))
            }else{
                bt_lockscreen.setImageDrawable(ContextCompat.getDrawable(applicationContext,R.drawable.ic_baseline_lock_open))
            }
            isLock = !isLock
        }
         simpleExoPlayer= SimpleExoPlayer.Builder(this)
            .setSeekBackIncrementMs(5000)
            .setSeekForwardIncrementMs(5000)
            .build()
        playerView.player=simpleExoPlayer
        playerView.keepScreenOn=true
        simpleExoPlayer.addListener(object : Player.Listener{
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playbackState ==Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                }else{
                    if(playbackState == Player.STATE_READY){
                        progressBar.visibility = View.GONE
                    }
                }
            }
        })
        val videoSource = Uri.parse(video.link)
        val mediaItem= MediaItem.fromUri(videoSource)
        simpleExoPlayer.setMediaItem(mediaItem)
        simpleExoPlayer.prepare()
        simpleExoPlayer.play()
    }
    private fun lockScreen(lock:Boolean) {
        val sec_mid=findViewById<LinearLayout>(R.id.sec_controlvid1)
        val sec_bottom=findViewById<LinearLayout>(R.id.sec_controlvid2)
        if(lock){
            sec_mid.visibility = View.INVISIBLE
            sec_bottom.visibility = View.INVISIBLE
        }else{
            sec_mid.visibility = View.VISIBLE
            sec_bottom.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        if(isLock) return
        if (resources.configuration.orientation== Configuration.ORIENTATION_LANDSCAPE){
            bt_fullscreen.performClick()
        }
        else super.onBackPressed()

    }

    override fun onStop() {
        super.onStop()
        simpleExoPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        simpleExoPlayer.release()
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer.pause()
    }
}