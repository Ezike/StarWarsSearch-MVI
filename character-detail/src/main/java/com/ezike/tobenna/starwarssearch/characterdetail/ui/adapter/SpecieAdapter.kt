package com.ezike.tobenna.starwarssearch.characterdetail.ui.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.character.detail.R
import com.ezike.tobenna.starwarssearch.character.detail.databinding.ItemSpecieBinding
import com.ezike.tobenna.starwarssearch.characterdetail.model.SpecieModel
import com.ezike.tobenna.starwarssearch.core.ext.inflate

class SpecieAdapter : ListAdapter<SpecieModel, SpecieAdapter.SpecieViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        return SpecieViewHolder(ItemSpecieBinding.bind(parent.inflate(R.layout.item_specie)))
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SpecieViewHolder(private val binding: ItemSpecieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specieModel: SpecieModel) {
            val context: Context = binding.root.context
            with(binding) {
                specieName.text = context.getString(R.string.specie_name, specieModel.name)
                specieLanguage.text =
                    context.getString(R.string.specie_language, specieModel.language)
                specieHomeWorld.text = getHomeWorld(specieModel, context)
            }
        }

        private fun getHomeWorld(specieModel: SpecieModel, context: Context): String {
            return if (specieModel.homeWorld.isNotEmpty()) {
                context.getString(R.string.specie_home, specieModel.homeWorld)
            } else {
                context.getString(R.string.specie_home_unavailable)
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<SpecieModel>
            get() = object : DiffUtil.ItemCallback<SpecieModel>() {
                override fun areItemsTheSame(oldItem: SpecieModel, newItem: SpecieModel): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: SpecieModel,
                    newItem: SpecieModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
