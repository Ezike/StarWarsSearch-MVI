package com.ezike.tobenna.starwarssearch.character_detail.views

import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation.mvi.base.ViewState
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

data class ProfileViewState(
    val character: CharacterDetailModel?
) : ViewState

class ProfileView(
    private val view: ProfileViewLayoutBinding,
    navigateUp: () -> Unit
) : UIComponent<ProfileViewState>() {

    init {
        view.backBtn.setOnClickListener { navigateUp() }
    }

    private fun getCharacterHeight(heightCm: String, heightInches: String?): String {
        return if (heightInches != null) {
            view.root.context.getString(R.string.height, heightCm, heightInches)
        } else {
            view.root.context.getString(R.string.height_unavailable)
        }
    }

    override fun render(state: ProfileViewState) {
        val (character: CharacterDetailModel?) = state
        if (character != null) {
            view.run {
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
