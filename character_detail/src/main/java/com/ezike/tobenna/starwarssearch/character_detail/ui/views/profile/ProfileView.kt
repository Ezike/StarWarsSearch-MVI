package com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile

import com.ezike.tobenna.starwarssearch.character_detail.R
import com.ezike.tobenna.starwarssearch.character_detail.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.character_detail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class ProfileView(
    private val view: ProfileViewLayoutBinding,
    navigateUp: () -> Unit
) : UIComponent<ProfileViewState>() {

    init {
        view.backBtn.setOnClickListener { navigateUp() }
    }

    override fun render(state: ProfileViewState) {
        val (character: CharacterDetailModel?) = state
        if (character != null) {
            val context = view.root.context
            view.run {
                profileTitle.text = context.getString(
                    R.string.profile_title, character.name
                )
                characterName.text = context.getString(
                    R.string.character_name, character.name
                )
                characterBirthYear.text = context.getString(
                    R.string.character_birth_year, character.birthYear
                )
                characterHeight.text = getCharacterHeight(
                    heightCm = character.heightCm,
                    heightInches = character.heightInches
                )
            }
        }
    }

    private fun getCharacterHeight(
        heightCm: String,
        heightInches: String?
    ): String {
        return if (heightInches != null) {
            view.root.context.getString(R.string.height, heightCm, heightInches)
        } else {
            view.root.context.getString(R.string.height_unavailable)
        }
    }
}
