package com.mrpatocode.digimonmemories.presentation.score

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.FragmentScoreBinding
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment() {
    private lateinit var _binding: FragmentScoreBinding
    private val binding get() = _binding

    private val scoreViewModel: ScoreViewModel by viewModels()

    private var orderSuccess: Boolean = true
    private var orderAttempts: Boolean = true
    private var orderErrors: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        scoreViewModel.getStatistics()

        binding.btnExit.setOnClickListener { findNavController().navigate(R.id.action_scoreFragment_to_homeFragment) }

        binding.btnErrors.setOnClickListener {
            if (orderErrors) {
                resetColors()
                orderErrors = false
                resetColors()
                it.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondary_gray))
                scoreViewModel.orderStatistics("errors")
            } else {
                orderErrors = true
                resetColors()
                scoreViewModel.orderStatistics("")
            }
        }

        binding.btnAttempts.setOnClickListener {
            if (orderAttempts) {
                resetColors()
                orderAttempts = false
                it.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondary_gray))
                scoreViewModel.orderStatistics("attempts")
            } else {
                orderAttempts = true
                resetColors()
                scoreViewModel.orderStatistics("")
            }
        }

        binding.btnSuccess.setOnClickListener {
            if (orderSuccess) {
                resetColors()
                orderSuccess = false
                it.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondary_gray))
                scoreViewModel.orderStatistics("success")
            } else {
                orderSuccess = true
                resetColors()
                scoreViewModel.orderStatistics("")
            }
        }

        observableStatistics()
    }

    private fun resetColors() {
        binding.btnErrors.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.main_orange))
        binding.btnAttempts.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondary_blue))
        binding.btnSuccess.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.main_green))
        orderAttempts = true
        orderErrors = true
        orderSuccess = true
    }

    private fun setRecyclerView() {
        binding.rvList.layoutManager = LinearLayoutManager(context)
        binding.rvList.adapter = ScoreAdapter{}
    }


    private fun observableStatistics() {
        scoreViewModel.statistics.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_statistics),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_scoreFragment_to_homeFragment)
                }
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("ScoreFragment", "observableStatistics: ${resource.data}")
                    val list = resource.data.orEmpty()
                    val adapter = ScoreAdapter(
                        onDeleteClickListener = {
                            scoreViewModel.deleteScore(it)
                        }
                    )

                    binding.progressCircular.visibility = View.GONE
                    binding.rvList.adapter = adapter
                    adapter.setList(list)
                    if (list.isNotEmpty()) {
                        binding.rvList.visibility = View.VISIBLE
                    } else{
                        binding.tvNoStatistics.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}