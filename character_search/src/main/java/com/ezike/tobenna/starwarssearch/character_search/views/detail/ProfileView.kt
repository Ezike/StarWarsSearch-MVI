package com.ezike.tobenna.starwarssearch.character_search.views.detail

import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState

data class ProfileViewState(val character: CharacterModel?) : ViewState

class ProfileView(private val binding: ProfileViewLayoutBinding) {

    fun render(state: ProfileViewState) {
        if (state.character != null) {
            binding.run {
                profileTitle.text =
                    root.context.getString(R.string.profile_title, state.character.name)
                characterName.text =
                    root.context.getString(R.string.character_name, state.character.name)
                characterBirthYear.text =
                    root.context.getString(R.string.character_birth_year, state.character.birthYear)
                characterHeight.text =
                    getCharacterHeight(state.character.heightCm, state.character.heightInches)
            }
        }
    }

    private fun getCharacterHeight(heightCm: String, heightInches: String?): String {
        return if (heightInches != null) {
            binding.root.context.getString(R.string.height, heightCm, heightInches)
        } else {
            binding.root.context.getString(R.string.height_unavailable)
        }
    }
}
