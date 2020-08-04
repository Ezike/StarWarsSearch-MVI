package com.ezike.tobenna.starwarssearch.character_search.views.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ezike.tobenna.starwarssearch.character_search.databinding.SpecieViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.SpecieDetailViewState
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SpecieView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<CharacterDetailViewIntent, SpecieDetailViewState> {

    private var binding: SpecieViewLayoutBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SpecieViewLayoutBinding.inflate(inflater, this, true)
    }

    override fun render(state: SpecieDetailViewState) {
    }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = flowOf()
}
