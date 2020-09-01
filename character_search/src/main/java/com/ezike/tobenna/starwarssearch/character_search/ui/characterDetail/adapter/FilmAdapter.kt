package com.ezike.tobenna.starwarssearch.character_search.ui.characterDetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.ItemFilmBinding
import com.ezike.tobenna.starwarssearch.character_search.model.FilmModel
import com.ezike.tobenna.starwarssearch.core.ext.inflate

class FilmAdapter : ListAdapter<FilmModel, FilmAdapter.FilmViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(ItemFilmBinding.bind(parent.inflate(R.layout.item_film)))
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FilmViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filmModel: FilmModel) {
            binding.filmTitle.text = filmModel.title
            binding.filmOpeningCrawl.text = filmModel.openingCrawl
        }
    }

    companion object {
        val diffUtilCallback: DiffUtil.ItemCallback<FilmModel>
            get() = object : DiffUtil.ItemCallback<FilmModel>() {
                override fun areItemsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                    return oldItem.title == newItem.title
                }

                override fun areContentsTheSame(oldItem: FilmModel, newItem: FilmModel): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
