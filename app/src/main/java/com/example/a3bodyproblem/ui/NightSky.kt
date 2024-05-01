package com.example.a3bodyproblem.ui

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.toPersistentList
import kotlin.random.Random

private const val HUNDRED = 100
private const val MIN_SIZE = 2
private const val MAX_SIZE = 8
private const val MIN_ALPHA = 50
private const val MAX_ALPHA = 100

private fun Random.fraction(): Float {
    return nextInt(0, HUNDRED).toFloat() / 100f
}

@Composable
fun NightSky() {
    val starsState = remember {
        val random = Random
        val list = buildList {
            repeat(1000) {
                add(
                    StarState(
                        relativeX = random.fraction(),
                        relativeY = random.fraction(),
                        size = random.nextInt(MIN_SIZE, MAX_SIZE).toFloat(),
                        alpha = random.nextInt(MIN_ALPHA, MAX_ALPHA).toFloat() / 100f
                    )
                )
            }
        }.toPersistentList()

        StarsState(
            starStates = list
        )
    }

    Stars(
        starsState,
        modifier = Modifier.background(color = Color.Black)
    )
}
