package com.example.a3bodyproblem.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class BodyState(
    val x: Float,
    val y: Float,
    val vx: Float,
    val vy: Float,
    val mass: Double,
    val color: Color,
)

@Immutable
data class BodiesState(
    val bodies: ImmutableList<BodyState>
)

@Composable
fun Bodies(
    bodiesState: BodiesState,
    modifier: Modifier = Modifier,
) {
    var scale by remember {
        mutableFloatStateOf(1.0f)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, _, zoom, _ -> scale *= zoom }
            }
    ) {
        bodiesState.bodies.forEach { body ->
            Body(body, scale)
        }
    }
}



@Composable
fun BoxWithConstraintsScope.Body(
    bodyState: BodyState,
    scale: Float,
    modifier: Modifier = Modifier,
) {
    val xInDp = (maxWidth - scale * maxWidth) / 2 + (bodyState.x.dp * scale)
    val yInDp = (maxHeight - scale * maxHeight) / 2 + (bodyState.y.dp * scale)

    Canvas(
        modifier = modifier
            .size((bodyState.mass * scale).dp)
            .offset(xInDp, yInDp)
    ) {
        drawCircle(color = bodyState.color)
    }
}
