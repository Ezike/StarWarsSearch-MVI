package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.ItemSpecieBinding
import com.ezike.tobenna.starwarssearch.character_search.model.SpecieModel
import com.ezike.tobenna.starwarssearch.core.ext.inflate
import javax.inject.Inject

class SpecieAdapter @Inject constructor() :
    ListAdapter<SpecieModel, SpecieAdapter.SpecieViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        return SpecieViewHolder(ItemSpecieBinding.bind(parent.inflate(R.layout.item_specie)))
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun reset() {
        submitList(emptyList())
    }

    class SpecieViewHolder(private val binding: ItemSpecieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(specieModel: SpecieModel) {
            val context: Context = binding.root.context
            with(binding) {
                specieName.text = context.getString(R.string.specie_name, specieModel.name)
                specieLanguage.text =
                    context.getString(R.string.specie_language, specieModel.language)
                specieHomeWorld.text =
                    context.getString(R.string.specie_home, specieModel.homeWorld)
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
