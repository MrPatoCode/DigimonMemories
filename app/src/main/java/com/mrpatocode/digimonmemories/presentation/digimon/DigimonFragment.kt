package com.mrpatocode.digimonmemories.presentation.digimon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.FragmentDigimonBinding
import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DigimonFragment : Fragment() {
    private lateinit var _binding: FragmentDigimonBinding
    private val binding get() = _binding

    private val digimonViewModel: DigimonViewModel by viewModels()
    private lateinit var digimonAdapter: DigimonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        digimonViewModel.loadDigimonList()

        binding.btnExit.setOnClickListener {
            findNavController().navigate(R.id.action_digimonFragment_to_homeFragment)
        }

        observableAllDigimon()
    }

    private fun observableAllDigimon() {
        digimonViewModel.allDigimon.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.rvList.visibility = View.GONE
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.rvList.visibility = View.VISIBLE
                    binding.rvList.layoutManager = LinearLayoutManager(context)

                    resource.data?.let {digimonAdapter = DigimonAdapter(it)}

                    binding.rvList.adapter = digimonAdapter
                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Unknown error: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_digimonFragment_to_homeFragment)
                }
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDigimonBinding.inflate(inflater, container, false)
        return binding.root
    }
}