package com.charlyco.circuitcompanion.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.circuitcompanion.R

val listOfFeatureIcons = listOf(
    R.drawable.rlc to R.string.rlc_cct_button,
    R.drawable.stardelta to R.string.star_delta_transform,
    R.drawable.ohm_law to R.string.ohms_law
).map { DrawableStringPair(it.first, it.second) }

data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int,
)