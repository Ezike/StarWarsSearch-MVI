package com.ezike.tobenna.starwarssearch.characterdetail.ui.views.profile

import com.ezike.tobenna.starwarssearch.character.detail.R
import com.ezike.tobenna.starwarssearch.characterdetail.model.CharacterDetailModel
import com.ezike.tobenna.starwarssearch.core.AppString
import com.ezike.tobenna.starwarssearch.core.ParamString
import com.ezike.tobenna.starwarssearch.core.StringResource
import java.math.BigDecimal
import java.math.RoundingMode

object ProfileViewStateFactory {

    val initialState = ProfileViewState(
        title = StringResource(res = R.string.empty),
        name = StringResource(res = R.string.empty),
        birthYear = StringResource(res = R.string.empty),
        height = StringResource(res = R.string.empty)
    )

    fun create(
        model: CharacterDetailModel
    ): ProfileViewState {
        val title = ParamString(res = R.string.profile_title, model.name)
        val name = ParamString(res = R.string.character_name, model.name)
        val birthYear = ParamString(res = R.string.character_birth_year, model.birthYear)
        val characterHeight = getCharacterHeight(heightCm = model.heightCm)

        return ProfileViewState(
            title = title,
            name = name,
            birthYear = birthYear,
            height = characterHeight
        )
    }

    private fun getCharacterHeight(
        heightCm: String
    ): AppString {
        val heightInches = getHeightInches(heightCm = heightCm)
        return if (heightInches != null) {
            ParamString(
                res = R.string.height,
                heightCm,
                heightInches
            )
        } else {
            StringResource(res = R.string.height_unavailable)
        }
    }

    private fun getHeightInches(
        heightCm: String
    ): String? = try {
        BigDecimal(heightCm.toDouble() * 0.393701)
            .setScale(1, RoundingMode.HALF_EVEN)
            .toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
