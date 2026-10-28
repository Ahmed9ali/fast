package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Material 3 Expressive Loading Indicator with 2 to 4 spinning geometric shapes.
 * Supports continuous orbital spinning & morphing in the refreshing state,
 * and proportional pull-progress response during pull-to-refresh gestures.
 */
@Composable
fun MaterialExpressiveShapeLoader(
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    pullProgress: Float = 0f,
    shapeCount: Int = 4
) {
    val count = shapeCount.coerceIn(2, 4)
    val isDark = isSystemInDarkTheme()

    // Expressive dynamic colors matching Material Design 3 guidelines
    val primary = MaterialTheme.colorScheme.primary
    val tertiary = MaterialTheme.colorScheme.tertiary
    val secondary = MaterialTheme.colorScheme.secondary
    val accent = if (isDark) Color(0xFFFBBF24) else Color(0xFFD97706) // Vibrant amber/gold accent

    val shapeColors = listOf(primary, tertiary, secondary, accent).take(count)

    // Infinite transitions for active refreshing state
    val infiniteTransition = rememberInfiniteTransition(label = "expressive_refresh_shapes")

    // Global orbital rotation
    val continuousOrbitAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit_spin"
    )

    // Self-spin of individual shapes around their own centers
    val continuousSelfAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shape_self_spin"
    )

    // Breathing pulse / wave scale across shapes
    val pulseProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_scale"
    )

    Canvas(modifier = modifier.testTag("expressive_shape_loader_canvas")) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val maxOrbitRadius = size.width * 0.28f
        val baseShapeSize = size.width * 0.25f

        val effectiveOrbitAngle: Float
        val effectiveOrbitRadius: Float
        val globalScale: Float

        if (isRefreshing) {
            effectiveOrbitAngle = continuousOrbitAngle
            effectiveOrbitRadius = maxOrbitRadius
            globalScale = 1.0f
        } else {
            val clampedPull = pullProgress.coerceIn(0f, 1f)
            effectiveOrbitAngle = clampedPull * 270f
            effectiveOrbitRadius = maxOrbitRadius * (0.4f + 0.6f * clampedPull)
            globalScale = 0.35f + 0.65f * clampedPull
        }

        val stepAngle = 360f / count

        for (i in 0 until count) {
            val angleDeg = effectiveOrbitAngle + (i * stepAngle)
            val angleRad = Math.toRadians(angleDeg.toDouble())

            val shapeCenter = Offset(
                x = (center.x + effectiveOrbitRadius * cos(angleRad)).toFloat(),
                y = (center.y + effectiveOrbitRadius * sin(angleRad)).toFloat()
            )

            // Calculate individualized pulse scale
            val shapeScale: Float = if (isRefreshing) {
                val phase = pulseProgress + (i * (2 * PI / count).toFloat())
                (1f + 0.22f * sin(phase)) * globalScale
            } else {
                globalScale
            }

            val shapeSize = baseShapeSize * shapeScale
            val shapeColor = shapeColors[i % shapeColors.size]

            // Individual shape self rotation angle (alternating directions for expressive dynamic motion)
            val selfAngle = if (isRefreshing) {
                if (i % 2 == 0) continuousSelfAngle * 1.2f else -continuousSelfAngle * 1.5f
            } else {
                pullProgress * 180f * (if (i % 2 == 0) 1f else -1f)
            }

            when (i % 4) {
                0 -> {
                    // Shape 1: Smooth Filled Circle
                    drawCircle(
                        color = shapeColor,
                        radius = shapeSize / 2f,
                        center = shapeCenter
                    )
                }
                1 -> {
                    // Shape 2: Rounded Square (Squircle)
                    rotate(degrees = selfAngle, pivot = shapeCenter) {
                        drawRoundRect(
                            color = shapeColor,
                            topLeft = Offset(shapeCenter.x - shapeSize / 2f, shapeCenter.y - shapeSize / 2f),
                            size = Size(shapeSize, shapeSize),
                            cornerRadius = CornerRadius(shapeSize * 0.28f, shapeSize * 0.28f)
                        )
                    }
                }
                2 -> {
                    // Shape 3: Rounded Equilateral Triangle
                    rotate(degrees = selfAngle, pivot = shapeCenter) {
                        drawRoundedTriangle(
                            color = shapeColor,
                            center = shapeCenter,
                            radius = shapeSize * 0.58f
                        )
                    }
                }
                3 -> {
                    // Shape 4: Rounded Diamond / 4-Point Star
                    rotate(degrees = selfAngle + 45f, pivot = shapeCenter) {
                        drawRoundRect(
                            color = shapeColor,
                            topLeft = Offset(shapeCenter.x - shapeSize / 2f, shapeCenter.y - shapeSize / 2f),
                            size = Size(shapeSize, shapeSize),
                            cornerRadius = CornerRadius(shapeSize * 0.32f, shapeSize * 0.32f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Draws an equilateral rounded triangle with soft, elegant curves.
 */
private fun DrawScope.drawRoundedTriangle(
    color: Color,
    center: Offset,
    radius: Float
) {
    val path = Path()
    // Vertices at angles -90°, 30°, 150°
    val v0 = Offset(center.x, center.y - radius)
    val v1 = Offset(
        (center.x + radius * cos(Math.toRadians(30.0))).toFloat(),
        (center.y + radius * sin(Math.toRadians(30.0))).toFloat()
    )
    val v2 = Offset(
        (center.x + radius * cos(Math.toRadians(150.0))).toFloat(),
        (center.y + radius * sin(Math.toRadians(150.0))).toFloat()
    )

    // Blend ratio for rounded vertices
    val t = 0.22f
    val p01 = lerp(v0, v1, t)
    val p02 = lerp(v0, v2, t)

    val p10 = lerp(v1, v0, t)
    val p12 = lerp(v1, v2, t)

    val p21 = lerp(v2, v1, t)
    val p20 = lerp(v2, v0, t)

    path.moveTo(p02.x, p02.y)
    path.quadraticTo(v0.x, v0.y, p01.x, p01.y)
    path.lineTo(p10.x, p10.y)
    path.quadraticTo(v1.x, v1.y, p12.x, p12.y)
    path.lineTo(p21.x, p21.y)
    path.quadraticTo(v2.x, v2.y, p20.x, p20.y)
    path.close()

    drawPath(path = path, color = color)
}

private fun lerp(start: Offset, stop: Offset, fraction: Float): Offset {
    return Offset(
        x = start.x + (stop.x - start.x) * fraction,
        y = start.y + (stop.y - start.y) * fraction
    )
}

/**
 * Floating Material 3 surface card enclosing the multi-shape expressive loader.
 * Positioned above the webview during pull-down and locked during refresh.
 */
@Composable
fun ExpressivePullRefreshIndicator(
    isRefreshing: Boolean,
    pullProgress: Float,
    modifier: Modifier = Modifier,
    shapeCount: Int = 4,
    size: Dp = 50.dp
) {
    Surface(
        modifier = modifier
            .size(size)
            .testTag("pull_to_refresh_indicator"),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.45f)
        ),
        shadowElevation = 8.dp,
        tonalElevation = 6.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            MaterialExpressiveShapeLoader(
                isRefreshing = isRefreshing,
                pullProgress = pullProgress,
                shapeCount = shapeCount,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}
