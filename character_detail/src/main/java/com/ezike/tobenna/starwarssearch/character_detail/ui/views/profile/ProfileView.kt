package com.ezike.tobenna.starwarssearch.character_detail.ui.views.profile

import com.ezike.tobenna.starwarssearch.character_detail.databinding.ProfileViewLayoutBinding
import com.ezike.tobenna.starwarssearch.core.string
import com.ezike.tobenna.starwarssearch.presentation_android.UIComponent

class ProfileView(
    private val view: ProfileViewLayoutBinding,
    navigateUp: () -> Unit
) : UIComponent<ProfileViewState>() {

    init {
        view.backBtn.setOnClickListener { navigateUp() }
    }

    override fun render(state: ProfileViewState) {
        view.run {
            profileTitle.string = state.title
            characterName.string = state.name
            characterBirthYear.string = state.birthYear
            characterHeight.string = state.height
        }
    }
}
