package com.ilyanvk.meditation.presentation.timerscreen

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ilyanvk.meditation.R
import com.ilyanvk.meditation.databinding.FragmentTimerBinding
import com.ilyanvk.meditation.presentation.util.Sound

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding
    private val viewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_timer, container, false
        )

        binding.timerImage.setOnClickListener { timerControl() }
        binding.doneButton.setOnClickListener { navigateHome() }
        binding.backButton.setOnClickListener { navigateHome() }

        val bell = MediaPlayer.create(context, R.raw.bell)
        val endBell = MediaPlayer.create(context, R.raw.end_bell)
        val sound = MediaPlayer.create(
            context, when (Sound.entries[TimerFragmentArgs.fromBundle(requireArguments()).sound]) {
                Sound.SILENCE -> R.raw.silence
                Sound.OCEAN -> R.raw.ocean_sound
                Sound.FOREST -> R.raw.forest_sound
                Sound.STORM -> R.raw.storm_sound
                Sound.MUSIC -> R.raw.meditation_music
            }
        )

        viewModel.timerControl = object : TimerControl {
            override fun onStart() {
                bell.start()
                sound.start()
            }

            override fun onPause() {
                binding.startPauseText.setText(R.string.tap_to_continue)
                sound.pause()
            }

            override fun onResume() {
                binding.startPauseText.setText(R.string.tap_to_pause)
                sound.start()
            }

            override fun onEnd() {
                endBell.start()
                sound.stop()
            }

            override fun onTick(millisUntilFinished: Long) {
                binding.timerText.text = makeText(millisUntilFinished)
                if (millisUntilFinished <= 30_000) {
                    binding.doneButton.visibility = View.VISIBLE
                    binding.backButton.visibility = View.GONE
                } else {
                    binding.doneButton.visibility = View.GONE
                    binding.backButton.visibility = View.VISIBLE
                }
            }

            override fun onCancel() {
                sound.stop()
            }
        }

        val millisecondsLeft =
            (TimerFragmentArgs.fromBundle(requireArguments()).time * 60).toLong() * 1000 + 1000

        viewModel.startTimer(millisecondsLeft)

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelTimer()
    }

    private fun makeText(time: Long): String {
        val seconds = time / 1000
        val text: String = if (seconds % 60 < 10) {
            "${seconds / 60}:0${seconds % 60}"
        } else {
            "${seconds / 60}:${seconds % 60}"
        }
        return text
    }

    private fun timerControl() {
        if (viewModel.millisecondsLeft.value != 0L) {
            if (viewModel.timerIsRunning.value == true) {
                viewModel.pauseTimer()
            } else {
                viewModel.resumeTimer()
            }
        }
    }

    private fun navigateHome() {
        view?.findNavController()
            ?.navigate(TimerFragmentDirections.actionTimerFragmentToHomeFragment())
        viewModel.cancelTimer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelTimer()
    }
}