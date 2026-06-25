package com.mrpatocode.digimonmemories.presentation.game

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.mrpatocode.digimonmemories.R
import com.mrpatocode.digimonmemories.databinding.CardItemBinding
import com.mrpatocode.digimonmemories.domain.model.Digimon

class GameAdapter(
    private val onCardClickListener: OnCardClickListener,
    val onReadyToFly: (ready: Boolean) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    private lateinit var digimonList: List<Digimon>

    interface OnCardClickListener{
        fun onCardClick(digimon: Digimon, position: Int)
    }

    fun submitList(list: List<Digimon>) {
        digimonList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: GameViewHolder,
        position: Int
    ) {
        holder.bind(digimonList[position])
    }

    override fun getItemCount(): Int = digimonList.size

    inner class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardItemBinding.bind(view)
        private val image = binding.ivItem

        init {
            view.setOnClickListener {
                val currentPosition = bindingAdapterPosition
                if (currentPosition != RecyclerView.NO_POSITION){
                    performFlipAnimation(view)
                    onCardClickListener.onCardClick(digimonList[currentPosition], currentPosition)
                }
            }
        }

        fun performFlipAnimation(view: View) {
            view.animate().scaleX(0f).setDuration(150).withEndAction {
                if (image.tag == "click") {
                    image.isVisible = false
                    image.tag = ""
                } else {
                    image.isVisible = true
                    image.tag = "click"
                }
                view.animate().scaleX(1f).setDuration(150).withEndAction {
                }.start()
            }.start()
        }

        fun bind(digimon: Digimon) {
            Glide.with(itemView.context)
                .load(digimon.img)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .override(300, 300)
                .centerCrop()
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        image.background = resource
                        performFlipAnimation(itemView)
                        onReadyToFly.invoke(true)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        image.background = null
                    }

                })
        }
    }
}