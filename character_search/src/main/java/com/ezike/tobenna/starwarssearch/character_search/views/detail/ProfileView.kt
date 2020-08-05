package com.ezike.tobenna.starwarssearch.character_search.views.detail

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewIntent
import com.ezike.tobenna.starwarssearch.character_search.presentation.detail.mvi.CharacterDetailViewState.ProfileLoaded
import com.ezike.tobenna.starwarssearch.presentation.mvi.MVIView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class ProfileView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet), MVIView<CharacterDetailViewIntent, ProfileLoaded> {

    private var binding: ProfileViewLayoutBinding

    init {
        isSaveEnabled = true
        val inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = ProfileViewLayoutBinding.inflate(inflater, this, true)
    }

    override fun render(state: ProfileLoaded) {
        with(binding) {
            profileTitle.text = context.getString(R.string.profile_title, state.character.name)
            characterName.text =
                context.getString(R.string.character_name, state.character.name)
            characterBirthYear.text =
                context.getString(R.string.character_birth_year, state.character.birthYear)
            characterHeight.text = getCharacterHeight(state)
        }
    }

    private fun getCharacterHeight(state: ProfileLoaded): String {
        return try {
            context.getString(
                R.string.height,
                state.character.heightCm,
                state.character.heightInches
            )
        } catch (e: Exception) {
            e.printStackTrace()
            context.getString(R.string.height_unavailable)
        }
    }

    override val intents: Flow<CharacterDetailViewIntent>
        get() = emptyFlow()
}
