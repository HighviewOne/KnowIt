package com.knowit.ui.theme

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.knowit.model.Category

val Category.color: Color
    get() = when (this) {
        Category.SCIENCE -> ScienceColor
        Category.HISTORY -> HistoryColor
        Category.GEOGRAPHY -> GeographyColor
        Category.POP_CULTURE -> PopCultureColor
        Category.TECH -> TechColor
    }

/** Full-screen background gradient shared by every screen. */
fun Modifier.screenBackground(): Modifier =
    background(Brush.verticalGradient(listOf(KnowItBackground, KnowItSurface)))
