package com.example.a3bodyproblem.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class StarState(
    val relativeX: Float,
    val relativeY: Float,
    val size: Float,
    val alpha: Float,
)

@Immutable
data class StarsState(
    val starStates: ImmutableList<StarState>
)

@Composable
fun Stars(
    starsState: StarsState,
    modifier: Modifier,
    ) {
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val w = size.width
        val h = size.height

        starsState.starStates.forEach { starState ->
            drawRect(
                color = Color.White,
                size = Size(starState.size, starState.size),
                alpha = starState.alpha,
                topLeft = Offset(
                    x = starState.relativeX * w,
                    y = starState.relativeY * h,
                )
            )
        }
    }
}
