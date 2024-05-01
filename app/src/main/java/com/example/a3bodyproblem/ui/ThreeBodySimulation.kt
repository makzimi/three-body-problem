package com.example.a3bodyproblem.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a3bodyproblem.theme.Pink80
import com.example.a3bodyproblem.theme.Purple80
import com.example.a3bodyproblem.theme.PurpleGrey80
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlin.math.max
import kotlin.math.sqrt

@Composable
fun ThreeBodySimulation() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NightSky()

        var bodiesState by remember {
            mutableStateOf(
                BodiesState(
                    listOf(
                        BodyState(100f, 200f, 0f, 0f, 48.0, Purple80),
                        BodyState(300f, 300f, 0.0f, 0f, 56.0, PurpleGrey80),
                        BodyState(200f, 400f, 0f, 0.0f, 72.0, Pink80)
                    ).toPersistentList()
                )
            )
        }

        val timeStep = 1e4f

        LaunchedEffect(Unit) {
            while (true) {

                val forces = Array(bodiesState.bodies.size) { Force() }

                for (i in bodiesState.bodies.indices) {
                    for (j in i + 1 until bodiesState.bodies.size) {
                        val (fx, fy) = computeForce(bodiesState.bodies[i], bodiesState.bodies[j])
                        forces[i] = Force(forces[i].fx + fx, forces[i].fy + fy)
                        forces[j] = Force(forces[j].fx - fx, forces[j].fy - fy)
                    }
                }

                bodiesState = bodiesState.copy(
                    bodies = bodiesState.bodies.mapIndexed { i, body ->
                        val ax = forces[i].fx / body.mass
                        val ay = forces[i].fy / body.mass

                        body.copy(
                            x = body.x + body.vx * timeStep,
                            y = body.y + body.vy * timeStep,
                            vx = body.vx + (ax * timeStep).toFloat(),
                            vy = body.vy + (ay * timeStep).toFloat(),
                        )
                    }.toPersistentList()
                )

                delay(16)
            }
        }

        Bodies(
            bodiesState = bodiesState,
        )
    }
}

private fun computeForce(b1: BodyState, b2: BodyState): Force {
    val gConstant = 0.0000002 // Gravitational constant
    val dx = b2.x - b1.x
    val dy = b2.y - b1.y
    val distance = max(100.0, sqrt((dx * dx + dy * dy).toDouble()))

    val force = gConstant * b1.mass * b2.mass / (distance * distance)
    val fx = force * dx / distance
    val fy = force * dy / distance
    return Force(fx, fy)
}

@Preview
@Composable
fun BodySimulationAppPreview() {
    ThreeBodySimulation()
}
