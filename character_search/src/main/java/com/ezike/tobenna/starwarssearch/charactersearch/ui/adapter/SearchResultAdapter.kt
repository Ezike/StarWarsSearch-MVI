package com.ezike.tobenna.starwarssearch.charactersearch.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.charactersearch.R
import com.ezike.tobenna.starwarssearch.charactersearch.databinding.SearchResultBinding
import com.ezike.tobenna.starwarssearch.charactersearch.model.CharacterModel
import com.ezike.tobenna.starwarssearch.charactersearch.ui.adapter.SearchResultAdapter.SearchResultViewHolder
import com.ezike.tobenna.starwarssearch.core.ext.inflate

typealias SearchResultClickListener = (CharacterModel) -> Unit

class SearchResultAdapter(private val onClick: SearchResultClickListener) :
    ListAdapter<CharacterModel, SearchResultViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(SearchResultBinding.bind(parent.inflate(R.layout.search_result)))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    class SearchResultViewHolder(private val binding: SearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel, onClick: SearchResultClickListener) {
            binding.character.text = character.name
            binding.character.setOnClickListener {
                onClick(character)
            }
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<CharacterModel>
            get() = object : DiffUtil.ItemCallback<CharacterModel>() {
                override fun areItemsTheSame(
                    oldItem: CharacterModel,
                    newItem: CharacterModel
                ): Boolean {
                    return oldItem.url == newItem.url
                }

                override fun areContentsTheSame(
                    oldItem: CharacterModel,
                    newItem: CharacterModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
