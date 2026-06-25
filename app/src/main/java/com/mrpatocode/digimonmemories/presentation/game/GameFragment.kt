package com.mrpatocode.digimonmemories.presentation.game

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.FragmentGameBinding
import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.presentation.common.dialog.ConfirmationDialog
import com.mrpatocode.digimonmemories.util.AppConstants
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameFragment : Fragment(), GameAdapter.OnCardClickListener,
    ConfirmationDialog.DialogListener {
    private lateinit var _binding: FragmentGameBinding
    private val binding get() = _binding

    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var digimonList: List<Digimon>
    private lateinit var adapter: GameAdapter

    private var loadImg = 0

    private var play: Boolean = true
    private var score: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openDialog(AppConstants.DIALOG_START)

        binding.btnExit.setOnClickListener {
            openDialog(AppConstants.DIALOG_EXIT)
        }

        binding.btnHint.setOnClickListener {
            useHint()
            Toast.makeText(requireContext(), getString(R.string.hint_used), Toast.LENGTH_SHORT)
                .show()
            gameViewModel.subtractHints()
        }

        binding.btnChange.setOnClickListener {
            gameViewModel.incrementChanges()
            gameViewModel.disableChange()
            gameViewModel.currentGameLevel()
        }

        observableAllDigimon()
        observableStatistics()
        observableSaveStatistics()
    }

    override fun onCardClick(digimon: Digimon, position: Int) {
        gameViewModel.incrementAttempts()
        binding.vBlock.visibility = View.VISIBLE
        val index = binding.ivDigimon.tag.toString()
        val correctDigimon = digimonList[index.toInt()].name
        val selectDigimon = digimon.name
        lifecycleScope.launch {

            if (correctDigimon == selectDigimon) {
                Toast.makeText(requireContext(), digimon.name, Toast.LENGTH_SHORT).show()
                gameViewModel.incrementSuccess()
            } else {
                Toast.makeText(requireContext(), "Incorrect", Toast.LENGTH_SHORT).show()
                gameViewModel.incrementErrors()

                binding.ivDigimon.isVisible = false
                val orangeColor = ColorStateList.valueOf(color(R.color.alert_orange))
                binding.cvDigimon.backgroundTintList = orangeColor
                binding.ivHeart.backgroundTintList = orangeColor
                delay(1500)
                binding.ivDigimon.isVisible = true
            }

            delay(1000)
            performFlipAnimationItem(position)

            if (play) {
                delay(500)
                gameViewModel.currentGameLevel()
            } else {
                openDialog(AppConstants.DIALOG_END, score.toString())
                gameViewModel.saveStatistics()
            }

            val greenColor = ColorStateList.valueOf(color(R.color.main_green))
            binding.cvDigimon.backgroundTintList = greenColor
            binding.ivHeart.backgroundTintList = greenColor
        }
    }

    private fun observableAllDigimon() {
        gameViewModel.featuredDigimon.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    viewVisible(false)
                }

                is Resource.Success -> {
                    resource.data?.let {
                        loadImg = 0
                        digimonList = it
                        binding.rvCards.layoutManager = GridLayoutManager(context, 2)
                        adapter = GameAdapter(
                            this,
                            onReadyToFly = {
                                Log.d("GameFragment", it.toString())
                                if (it) loadImg++

                                if (loadImg == digimonList.size) {
                                    digimonImage()
                                }
                            }
                        )

                        viewVisible(true)

                        adapter.submitList(it)
                        binding.rvCards.adapter = adapter
                    }
                }

                is Resource.Error -> {
                    play = false
                    viewVisible(false)
                    findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
                    Toast.makeText(
                        requireContext(),
                        "Error: ${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observableStatistics() {
        gameViewModel.success.observe(viewLifecycleOwner) { success -> score = success }

        gameViewModel.hearts.observe(viewLifecycleOwner) { hearts ->
            binding.tvHearts.text = hearts.toString()
            play = (hearts > 0)
        }

        gameViewModel.hints.observe(viewLifecycleOwner) { hints ->
            binding.tvHints.text = hints.toString()
            enableButtonHelp(binding.btnHint, (hints > 0), R.color.main_yellow)
        }

        gameViewModel.stateChange.observe(viewLifecycleOwner) { state ->
            enableButtonHelp(binding.btnChange, state, R.color.main_orange)
        }
    }

    private fun observableSaveStatistics() {
        gameViewModel.stateSaveStatistics.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    gameViewModel.resetStatistics()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_save),
                        Toast.LENGTH_SHORT
                    ).show()

                    findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
                }

                is Resource.Loading -> {
                    Toast.makeText(requireContext(), getString(R.string.saving), Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.saving_success),
                        Toast.LENGTH_SHORT
                    )
                        .show()

                }
            }
        }
    }

    private fun digimonImage() {
        val digimon = digimonList.random()
        binding.ivDigimon.tag = digimonList.indexOf(digimon)

        Glide.with(this)
            .load(digimon.img)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.ivDigimon.background = resource
                    performFlipAnimationAllItem()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    binding.ivDigimon.background = null
                }

            })
    }

    private fun enableButtonHelp(view: View, enable: Boolean, colorId: Int) {
        if (enable) {
            view.isEnabled = true
            view.backgroundTintList = ColorStateList.valueOf(color(colorId))
        } else {
            view.isEnabled = false
            view.backgroundTintList = ColorStateList.valueOf(color(R.color.secondary_gray))
        }
    }

    private fun viewVisible(enable: Boolean) {
        if (enable) {
            binding.progressCircular.isVisible = false
            binding.cvDigimon.isVisible = true
            binding.rvCards.isVisible = true
            binding.btnHint.isVisible = true
            binding.btnChange.isVisible = true
        } else {
            binding.progressCircular.isVisible = true
            binding.cvDigimon.isVisible = false
            binding.rvCards.isVisible = false
            binding.btnHint.isVisible = false
            binding.btnChange.isVisible = false
        }
    }

    private fun performFlipAnimationAllItem() {
        lifecycleScope.launch {
            binding.ivDigimon.isVisible = false
            delay(3500)
            for (x in digimonList) {
                performFlipAnimationItem(digimonList.indexOf(x))
            }

            delay(500)
            binding.ivDigimon.isVisible = true
            binding.vBlock.visibility = View.GONE

        }
    }

    private fun performFlipAnimationItem(position: Int) {
        val viewHolder = binding.rvCards.findViewHolderForAdapterPosition(position)
        (viewHolder as? GameAdapter.GameViewHolder)?.performFlipAnimation(viewHolder.itemView)
            ?: run {
                Toast.makeText(requireContext(), "Cards unavailable", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
            }
    }

    private fun useHint() {
        val index = binding.ivDigimon.tag.toString()
        var digimon = digimonList[index.toInt()]
        digimon = digimonList.filter { it != digimon }.random()
        val position = digimonList.indexOf(digimon)

        lifecycleScope.launch {
            binding.vBlock.visibility = View.VISIBLE
            binding.ivDigimon.isVisible = true
            binding.ivDigimon.isVisible = false
            performFlipAnimationItem(position)
            delay(3000)
            performFlipAnimationItem(position)
            binding.ivDigimon.isVisible = true
            delay(500)
            binding.vBlock.visibility = View.GONE
        }
    }

    private fun color(id: Int): Int = ContextCompat.getColor(requireContext(), id)


    private fun openDialog(dialog: String, score: String = "") {
        val dialog = ConfirmationDialog.newInstance(mode = dialog, score = score)
        dialog.isCancelable = false
        dialog.show(childFragmentManager, AppConstants.DIALOG_TAG_CONFIRMATION)
    }

    override fun onAccept(dialog: String) {
        when (dialog) {
            AppConstants.DIALOG_START -> {
                if (play) {
                    gameViewModel.currentGameLevel()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.good_luck),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            AppConstants.DIALOG_EXIT -> {
                findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
            }

            AppConstants.DIALOG_END -> {
                findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
            }
        }
    }

    override fun onCancel(dialog: String) {
        when (dialog) {
            AppConstants.DIALOG_START -> {
                findNavController().navigate(R.id.action_gameFragment_to_homeFragment)
            }

            AppConstants.DIALOG_END -> {
                gameViewModel.resetStatistics()
                gameViewModel.currentGameLevel()
            }
        }
    }
}