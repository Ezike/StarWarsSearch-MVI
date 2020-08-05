package com.ezike.tobenna.starwarssearch.character_search.ui.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.SearchHistoryBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.core.ext.inflate
import javax.inject.Inject

typealias RecentSearchClickListener = (CharacterModel) -> Unit

class SearchHistoryAdapter @Inject constructor() :
    ListAdapter<CharacterModel, SearchHistoryAdapter.SearchHistoryViewHolder>(diffUtilCallback) {

    var clickListener: RecentSearchClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        return SearchHistoryViewHolder(SearchHistoryBinding.bind(parent.inflate(R.layout.search_history)))
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    fun reset() {
        submitList(emptyList())
    }

    class SearchHistoryViewHolder(private val binding: SearchHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel, clickListener: RecentSearchClickListener?) {
            binding.name.text = character.name
            binding.name.setOnClickListener {
                clickListener?.invoke(character)
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
