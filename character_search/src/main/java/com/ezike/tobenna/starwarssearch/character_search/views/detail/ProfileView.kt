package com.ezike.tobenna.starwarssearch.character_search.views.detail

import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_search.model.CharacterModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent
import com.ezike.tobenna.starwarssearch.presentation_android.UIRenderer

data class ProfileViewState(val character: CharacterModel?) : ViewState

@Suppress("FunctionName")
fun ProfileView(binding: ProfileViewLayoutBinding): UIComponent<ProfileViewState> {

    fun getCharacterHeight(heightCm: String, heightInches: String?): String {
        return if (heightInches != null) {
            binding.root.context.getString(R.string.height, heightCm, heightInches)
        } else {
            binding.root.context.getString(R.string.height_unavailable)
        }
    }

    return UIRenderer { (character: CharacterModel?) ->
        if (character != null) {
            binding.run {
                profileTitle.text =
                    root.context.getString(R.string.profile_title, character.name)
                characterName.text =
                    root.context.getString(R.string.character_name, character.name)
                characterBirthYear.text =
                    root.context.getString(R.string.character_birth_year, character.birthYear)
                characterHeight.text =
                    getCharacterHeight(character.heightCm, character.heightInches)
            }
        }
    }
}
