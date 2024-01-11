package com.ilyanvk.meditation.presentation.choosetimescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.slider.RangeSlider
import com.ilyanvk.meditation.R
import com.ilyanvk.meditation.databinding.FragmentChooseTimeBinding
import kotlin.math.roundToInt

class ChooseTimeFragment : Fragment() {
    private var _binding: FragmentChooseTimeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChooseTimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChooseTimeBinding.inflate(inflater, container, false)

        binding.timeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
            }
        })

        binding.timeSlider.addOnChangeListener { _, value, _ ->
            val selectedTime = value.roundToInt()
            viewModel.updateSelectedTime(selectedTime)
            binding.timeValueText.text = selectedTime.toTimeString()
            binding.apply {
                if (selectedTime != 0) {
                    startButton.isClickable = true
                    startButton.isEnabled = true
                }
            }
        }

        binding.startButton.setOnClickListener {
            val soundNumber: Int = ChooseTimeFragmentArgs.fromBundle(requireArguments()).sound
            view?.findNavController()?.navigate(
                ChooseTimeFragmentDirections.actionChooseTimeFragmentToTimerFragment(
                    soundNumber, viewModel.selectedTime.value ?: 0
                )
            )
        }

        return binding.root
    }

    private fun Int.toTimeString(): String {
        return if (this == 1) getString(R.string.min)
        else getString(R.string.mins, this)
    }
}
