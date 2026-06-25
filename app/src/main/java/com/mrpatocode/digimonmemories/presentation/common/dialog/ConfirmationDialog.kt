package com.mrpatocode.digimonmemories.presentation.common.dialog

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.DialogConfirmationBinding
import com.mrpatocode.digimonmemories.util.AppConstants

class ConfirmationDialog: DialogFragment() {
    private var _binding: DialogConfirmationBinding? = null
    private val binding get() = _binding!!

    private var listener: DialogListener? = null

    interface DialogListener{
        fun onAccept(dialog: String)
        fun onCancel(dialog: String)
    }

    companion object{
        private const val DIALOG_MODE = "current_mode"
        private const val USER_SCORE = "final_score"

        fun newInstance(mode: String, score: String = ""): ConfirmationDialog{
            val fragment = ConfirmationDialog()
            val args = Bundle().apply {
                putString(DIALOG_MODE,mode)
                putString(USER_SCORE,  score)
            }

            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? DialogListener
        if (null == listener)
            throw RuntimeException("The parent Fragment must implement DialogListener")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogConfirmationBinding.inflate(inflater, container, false)

        val mode = arguments?.getString(DIALOG_MODE)?: "exit"
        val score = arguments?.getString(USER_SCORE)?: "0"

        when(mode){
            AppConstants.DIALOG_EXIT ->{
                binding.tvTitle.text = getString(R.string.exit_the_game)
                binding.subTitle.text = getString(R.string.your_statistics_will_be_lost)

                var color = ContextCompat.getColor(requireContext(), R.color.alert_orange)
                binding.btnAccept.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnAccept.text = getString(R.string.accept)

                color = ContextCompat.getColor(requireContext(), R.color.main_blue)
                binding.btnCancel.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnCancel.text = getString(R.string.resume)

            }

            AppConstants.DIALOG_START -> {
                binding.tvTitle.text = getString(R.string.start_the_game)
                binding.subTitle.text = getString(R.string.memorise_and_find)

                var color = ContextCompat.getColor(requireContext(), R.color.main_green)
                binding.btnAccept.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnAccept.text = getString(R.string.accept)

                color = ContextCompat.getColor(requireContext(), R.color.main_blue)
                binding.btnCancel.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnCancel.text = getString(R.string.cancel)
            }

            AppConstants.DIALOG_END -> {
                binding.tvTitle.text = getString(R.string.end_the_game)
                binding.subTitle.text = getString(R.string.final_score) + score

                var color = ContextCompat.getColor(requireContext(), R.color.main_blue)
                binding.btnAccept.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnAccept.text = getString(R.string.accept)

                color = ContextCompat.getColor(requireContext(), R.color.main_green)
                binding.btnCancel.backgroundTintList = ColorStateList.valueOf(color)
                binding.btnCancel.text = getString(R.string.again)
            }
        }

        binding.btnAccept.setOnClickListener {
            listener?.onAccept(mode)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            listener?.onCancel(mode)
            dismiss()
        }

        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}