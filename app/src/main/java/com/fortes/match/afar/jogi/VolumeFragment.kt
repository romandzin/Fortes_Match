package com.fortes.match.afar.jogi

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SeekBar
import com.fortes.match.afar.jogi.databinding.FragmentVolumeBinding


class VolumeFragment : Fragment() {

    lateinit var binding: FragmentVolumeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVolumeBinding.inflate(layoutInflater)
        val soundPlayer = MediaPlayer.create(requireContext(), R.raw.click_sound)
        soundPlayer.setVolume(VolumeData.clickVolume, VolumeData.clickVolume)
        soundPlayer.isLooping = false
        binding.musicSeek.progress = (VolumeData.musicVolume * 10).toInt()
        binding.musicSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                VolumeData.musicVolume = (progress.toFloat()/10)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })
        binding.soundSeek.progress = (VolumeData.clickVolume * 10).toInt()
        binding.soundSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                VolumeData.clickVolume = (progress.toFloat()/10)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {    }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {     }
        })
        binding.continueButton.setOnClickListener {
            (activity as Navigator).goBack()
            soundPlayer.start()
        }
        binding.optionsElement.setOnClickListener {
            (activity as Navigator).goBack()
            soundPlayer.start()
        }
        return binding.root
    }
}