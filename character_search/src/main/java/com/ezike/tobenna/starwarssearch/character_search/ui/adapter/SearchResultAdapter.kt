package com.ezike.tobenna.starwarssearch.character_search.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.SearchResultBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.character_search.ui.adapter.SearchResultAdapter.SearchResultViewHolder
import com.ezike.tobenna.starwarssearch.core.ext.inflate
import com.ezike.tobenna.starwarssearch.core.ext.safeOffer
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce

typealias SearchResultClickListener = (CharacterModel) -> Unit

class SearchResultAdapter @Inject constructor() :
    ListAdapter<CharacterModel, SearchResultViewHolder>(diffUtilCallback) {

    var clickListener: SearchResultClickListener? = null

    val clicks: Flow<CharacterModel>
        get() = callbackFlow {
            val listener: SearchResultClickListener = { character: CharacterModel ->
                safeOffer(character)
                Unit
            }
            clickListener = listener
            awaitClose { clickListener = null }
        }.conflate().debounce(200)

    fun reset() {
        submitList(listOf())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        return SearchResultViewHolder(SearchResultBinding.bind(parent.inflate(R.layout.search_result)))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class SearchResultViewHolder(private val binding: SearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModel, clickListener: SearchResultClickListener?) {
            binding.character.text = character.name
            binding.character.setOnClickListener {
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
