package com.mrpatocode.digimonmemories.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
        }

        binding.btnList.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_digimonFragment)
        }

        binding.btnScore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_scoreFragment)
        }
    }

}