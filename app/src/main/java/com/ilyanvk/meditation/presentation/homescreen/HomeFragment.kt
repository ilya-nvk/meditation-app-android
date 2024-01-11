package com.ilyanvk.meditation.presentation.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ilyanvk.meditation.R
import com.ilyanvk.meditation.databinding.FragmentHomeBinding
import com.ilyanvk.meditation.presentation.util.Sound

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            silenceButton.setOnClickListener {
                view?.findNavController()?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseTimeFragment(Sound.SILENCE.ordinal)
                )
            }
            oceanButton.setOnClickListener {
                view?.findNavController()?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseTimeFragment(Sound.OCEAN.ordinal)
                )
            }
            forestButton.setOnClickListener {
                view?.findNavController()?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseTimeFragment(Sound.FOREST.ordinal)
                )
            }
            stormButton.setOnClickListener {
                view?.findNavController()?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseTimeFragment(Sound.STORM.ordinal)
                )
            }
            musicButton.setOnClickListener {
                view?.findNavController()?.navigate(
                    HomeFragmentDirections.actionHomeFragmentToChooseTimeFragment(Sound.MUSIC.ordinal)
                )
            }

            settingsButton.setOnClickListener {
                view?.findNavController()?.navigate(R.id.action_homeFragment_to_settingsFragment)
            }
        }

        return binding.root
    }
}