package com.fortes.match.afar.jogi

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.fortes.match.afar.jogi.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity(), Navigator {

    private var binding: ActivityMenuBinding? = null
    private lateinit var clickPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        turnOnSound()
        initView()
        setFullWindow()
    }

    private fun setFullWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun turnOnSound() {
        clickPlayer = MediaPlayer.create(this, R.raw.click_sound)
        clickPlayer.isLooping = false
        clickPlayer.setVolume(VolumeData.clickVolume, VolumeData.clickVolume)
    }

    private fun initView() {
        binding?.let {
            it.playButton.setOnClickListener {
                clickPlayer.start()
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
            }
            it.optionsButton.setOnClickListener {
                clickPlayer.start()
                goToOptions()
            }
            it.settingsButton.setOnClickListener {
                clickPlayer.start()
                goToOptions()
            }
            it.exitButton.setOnClickListener { _ ->
                clickPlayer.start()
                it.exitScreen.root.isVisible = true
                it.exitScreen.tryAgain.setOnClickListener { button ->
                    clickPlayer.start()
                    it.exitScreen.root.isVisible = false
                }
                it.exitScreen.exit.setOnClickListener {
                    clickPlayer.start()
                    finishAffinity()
                }
            }
        }
    }

    private fun goToOptions() {
        supportFragmentManager.beginTransaction()
            .replace(binding?.fragmentContainer!!.id, VolumeFragment())
            .addToBackStack("name")
            .commit()
        binding?.fragmentContainer?.isVisible = true
    }

    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 0) supportFragmentManager.popBackStack()
        else onBackPressed()
    }
}