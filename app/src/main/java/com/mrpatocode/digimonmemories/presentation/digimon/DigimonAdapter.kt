package com.mrpatocode.digimonmemories.presentation.digimon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.DigimonItemBinding
import com.mrpatocode.digimonmemories.domain.model.Digimon

class DigimonAdapter(private val list: List<Digimon>): RecyclerView.Adapter<DigimonAdapter.DigimonViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DigimonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.digimon_item, parent, false)
        return DigimonViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DigimonViewHolder,
        position: Int
    ) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    class DigimonViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = DigimonItemBinding.bind(view)
        private val image = binding.ivDigimon

        fun bind(digimon: Digimon){
            binding.tvName.text = digimon.name
            binding.tvLevel.text = digimon.level

            Glide.with(itemView.context)
                .load(digimon.img)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .into(image)
        }
    }
}