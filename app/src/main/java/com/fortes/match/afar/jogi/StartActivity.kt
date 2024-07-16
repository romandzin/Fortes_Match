package com.fortes.match.afar.jogi

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {

    private lateinit var clickPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        val button = findViewById<ImageView>(R.id.start_button)
        button.setOnClickListener {
            clickPlayer.start()
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        setFullWindow()
        turnOnSound()
    }

    private fun turnOnSound() {
        clickPlayer = MediaPlayer.create(this, R.raw.click_sound)
        clickPlayer.isLooping = false
        clickPlayer.setVolume(VolumeData.clickVolume, VolumeData.clickVolume)
    }

    private fun setFullWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}