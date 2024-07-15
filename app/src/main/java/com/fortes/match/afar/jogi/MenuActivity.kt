package com.fortes.match.afar.jogi

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fortes.match.afar.jogi.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private var binding: ActivityMenuBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        initView()
        setFullWindow()
    }

    private fun setFullWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun initView() {
        binding?.let {
            it.playButton.setOnClickListener {
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
            }
        }
    }
}