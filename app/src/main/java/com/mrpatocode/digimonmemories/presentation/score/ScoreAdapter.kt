package com.mrpatocode.digimonmemories.presentation.score

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.ScoreItemBinding
import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.util.formatToReadableDate

class ScoreAdapter(
    val onDeleteClickListener: (Score) -> Unit
) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    private var list: List<Score> = emptyList()

    fun setList(list: List<Score>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.score_item, parent, false)
        return ScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ScoreItemBinding.bind(itemView)
        private val btnDelete = binding.btnDeleteScore
        private var score: Score? = null

        fun bind(score: Score) {
            this.score = score
            binding.tvScore.text = score.success.toString()
            binding.tvErrors.text = score.errors.toString()
            binding.tvAttempts.text = score.attempts.toString()
            binding.tvLevel.text = score.level.toString()
            binding.tvDate.text = score.date.formatToReadableDate()
        }

        init {
            btnDelete.setOnClickListener {
                score?.let {
                    onDeleteClickListener.invoke(it)
                }
            }
        }
    }
}