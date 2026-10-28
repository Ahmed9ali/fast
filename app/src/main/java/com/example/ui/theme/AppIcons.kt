package com.example.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Material Symbols Outlined icons custom configured:
 * - Drone2: opsz=24, wght=300, FILL=0, GRAD=-25, ROND=50
 * - Search: opsz=24, wght=300, FILL=0, GRAD=-25, ROND=50
 */

public val Drone2Icon: ImageVector
    get() {
        if (_drone2Icon != null) {
            return _drone2Icon!!
        }
        _drone2Icon = ImageVector.Builder(
            name = "drone_2",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.Companion.NonZero,
            ) {
                moveTo(6.73f, 21.3f)
                quadToRelative(-1.68f, 0f, -2.86f, -1.19f)
                reflectiveQuadTo(2.7f, 17.24f)
                quadToRelative(0f, -1.68f, 1.17f, -2.85f)
                reflectiveQuadTo(6.73f, 13.21f)
                quadToRelative(0.59f, 0f, 1.12f, 0.16f)
                reflectiveQuadToRelative(0.99f, 0.45f)
                quadTo(9.47f, 12.92f, 9.5f, 11.96f)
                reflectiveQuadTo(8.94f, 10.12f)
                quadTo(8.47f, 10.43f, 7.92f, 10.6f)
                reflectiveQuadTo(6.76f, 10.77f)
                quadToRelative(-1.69f, 0f, -2.87f, -1.18f)
                reflectiveQuadTo(2.71f, 6.73f)
                reflectiveQuadTo(3.89f, 3.87f)
                reflectiveQuadTo(6.75f, 2.69f)
                reflectiveQuadTo(9.62f, 3.87f)
                reflectiveQuadTo(10.8f, 6.73f)
                quadToRelative(0f, 0.62f, -0.18f, 1.17f)
                reflectiveQuadTo(10.13f, 8.93f)
                quadToRelative(0.89f, 0.58f, 1.84f, 0.56f)
                reflectiveQuadTo(13.83f, 8.85f)
                quadToRelative(-0.3f, -0.46f, -0.46f, -1f)
                reflectiveQuadTo(13.21f, 6.73f)
                quadToRelative(0f, -1.68f, 1.17f, -2.85f)
                reflectiveQuadTo(17.25f, 2.71f)
                quadToRelative(1.68f, 0f, 2.87f, 1.17f)
                reflectiveQuadTo(21.3f, 6.73f)
                reflectiveQuadTo(20.11f, 9.6f)
                reflectiveQuadToRelative(-2.87f, 1.18f)
                quadToRelative(-0.64f, 0f, -1.21f, -0.19f)
                reflectiveQuadTo(14.97f, 10.06f)
                quadToRelative(-0.61f, 0.9f, -0.58f, 1.9f)
                reflectiveQuadToRelative(0.67f, 1.91f)
                quadToRelative(0.47f, -0.31f, 1.02f, -0.49f)
                reflectiveQuadToRelative(1.16f, -0.18f)
                quadToRelative(1.68f, 0f, 2.87f, 1.17f)
                reflectiveQuadToRelative(1.19f, 2.86f)
                reflectiveQuadToRelative(-1.19f, 2.87f)
                reflectiveQuadTo(17.25f, 21.3f)
                quadToRelative(-1.69f, 0f, -2.86f, -1.19f)
                reflectiveQuadTo(13.21f, 17.24f)
                quadToRelative(0f, -0.61f, 0.18f, -1.17f)
                reflectiveQuadToRelative(0.49f, -1.03f)
                quadTo(12.95f, 14.41f, 11.96f, 14.38f)
                reflectiveQuadToRelative(-1.89f, 0.58f)
                quadToRelative(0.35f, 0.49f, 0.54f, 1.06f)
                reflectiveQuadToRelative(0.19f, 1.21f)
                quadToRelative(0f, 1.7f, -1.19f, 2.88f)
                reflectiveQuadTo(6.73f, 21.3f)
                close()
                moveTo(17.24f, 9.39f)
                quadToRelative(1.1f, 0f, 1.88f, -0.78f)
                reflectiveQuadTo(19.9f, 6.73f)
                reflectiveQuadTo(19.12f, 4.87f)
                reflectiveQuadTo(17.25f, 4.11f)
                reflectiveQuadTo(15.38f, 4.87f)
                reflectiveQuadTo(14.61f, 6.73f)
                quadToRelative(0f, 0.34f, 0.08f, 0.65f)
                reflectiveQuadToRelative(0.23f, 0.58f)
                lineTo(16.61f, 6.26f)
                quadTo(16.82f, 6.05f, 17.09f, 6.05f)
                reflectiveQuadToRelative(0.49f, 0.21f)
                quadToRelative(0.22f, 0.22f, 0.22f, 0.49f)
                reflectiveQuadTo(17.58f, 7.23f)
                lineTo(15.85f, 8.97f)
                quadToRelative(0.29f, 0.2f, 0.65f, 0.31f)
                reflectiveQuadToRelative(0.74f, 0.11f)
                close()
                moveTo(6.75f, 9.37f)
                quadToRelative(0.36f, 0f, 0.68f, -0.1f)
                reflectiveQuadTo(8.03f, 9f)
                lineTo(6.26f, 7.23f)
                quadTo(6.05f, 7.02f, 6.04f, 6.75f)
                reflectiveQuadTo(6.26f, 6.26f)
                reflectiveQuadTo(6.75f, 6.04f)
                reflectiveQuadTo(7.24f, 6.26f)
                lineTo(9.03f, 8.04f)
                quadTo(9.21f, 7.75f, 9.31f, 7.42f)
                reflectiveQuadTo(9.4f, 6.73f)
                quadTo(9.4f, 5.62f, 8.63f, 4.86f)
                reflectiveQuadTo(6.75f, 4.09f)
                reflectiveQuadTo(4.88f, 4.86f)
                reflectiveQuadTo(4.11f, 6.73f)
                reflectiveQuadTo(4.88f, 8.6f)
                reflectiveQuadTo(6.75f, 9.37f)
                close()
                moveTo(17.24f, 19.9f)
                quadToRelative(1.11f, 0f, 1.88f, -0.78f)
                reflectiveQuadTo(19.9f, 17.24f)
                reflectiveQuadTo(19.12f, 15.37f)
                reflectiveQuadTo(17.24f, 14.61f)
                quadToRelative(-0.36f, 0f, -0.68f, 0.09f)
                reflectiveQuadToRelative(-0.6f, 0.26f)
                lineToRelative(1.81f, 1.8f)
                quadToRelative(0.21f, 0.21f, 0.21f, 0.49f)
                reflectiveQuadToRelative(-0.2f, 0.49f)
                quadToRelative(-0.23f, 0.22f, -0.51f, 0.22f)
                reflectiveQuadToRelative(-0.5f, -0.22f)
                lineTo(14.98f, 15.95f)
                quadToRelative(-0.16f, 0.28f, -0.27f, 0.6f)
                reflectiveQuadToRelative(-0.1f, 0.68f)
                quadToRelative(0f, 1.11f, 0.76f, 1.88f)
                reflectiveQuadToRelative(1.87f, 0.78f)
                close()
                moveToRelative(-10.51f, 0f)
                quadToRelative(1.12f, 0f, 1.9f, -0.78f)
                reflectiveQuadTo(9.4f, 17.24f)
                quadTo(9.4f, 16.85f, 9.29f, 16.5f)
                reflectiveQuadTo(8.98f, 15.85f)
                lineTo(7.07f, 17.74f)
                quadToRelative(-0.22f, 0.22f, -0.5f, 0.22f)
                reflectiveQuadTo(6.07f, 17.74f)
                reflectiveQuadTo(5.85f, 17.26f)
                reflectiveQuadTo(6.07f, 16.77f)
                lineTo(7.93f, 14.92f)
                quadTo(7.67f, 14.78f, 7.36f, 14.69f)
                reflectiveQuadTo(6.73f, 14.61f)
                quadToRelative(-1.11f, 0f, -1.87f, 0.76f)
                reflectiveQuadTo(4.1f, 17.24f)
                reflectiveQuadToRelative(0.76f, 1.88f)
                reflectiveQuadTo(6.73f, 19.9f)
                close()
                moveTo(12f, 12.88f)
                quadToRelative(0.37f, 0f, 0.63f, -0.25f)
                reflectiveQuadToRelative(0.26f, -0.62f)
                reflectiveQuadTo(12.63f, 11.38f)
                reflectiveQuadTo(12f, 11.12f)
                reflectiveQuadToRelative(-0.62f, 0.26f)
                reflectiveQuadToRelative(-0.25f, 0.63f)
                reflectiveQuadToRelative(0.25f, 0.62f)
                reflectiveQuadTo(12f, 12.88f)
                close()
            }
        }.build()
        return _drone2Icon!!
    }

private var _drone2Icon: ImageVector? = null

public val CustomSearchIcon: ImageVector
    get() {
        if (_customSearchIcon != null) {
            return _customSearchIcon!!
        }
        _customSearchIcon = ImageVector.Builder(
            name = "search",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Bevel,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.Companion.NonZero,
            ) {
                moveTo(19.44f, 20.4f)
                lineToRelative(-6.2f, -6.2f)
                quadToRelative(-0.74f, 0.62f, -1.71f, 0.96f)
                reflectiveQuadTo(9.56f, 15.49f)
                quadToRelative(-2.51f, 0f, -4.24f, -1.74f)
                reflectiveQuadTo(3.58f, 9.52f)
                reflectiveQuadTo(5.31f, 5.28f)
                reflectiveQuadTo(9.55f, 3.55f)
                reflectiveQuadToRelative(4.24f, 1.74f)
                reflectiveQuadToRelative(1.74f, 4.24f)
                quadToRelative(0f, 1.05f, -0.35f, 2.02f)
                reflectiveQuadToRelative(-0.94f, 1.68f)
                lineToRelative(6.2f, 6.19f)
                lineTo(19.44f, 20.4f)
                close()
                moveTo(9.55f, 14.1f)
                quadToRelative(1.92f, 0f, 3.25f, -1.33f)
                reflectiveQuadTo(14.13f, 9.52f)
                reflectiveQuadTo(12.8f, 6.27f)
                reflectiveQuadTo(9.55f, 4.95f)
                reflectiveQuadTo(6.3f, 6.27f)
                reflectiveQuadTo(4.98f, 9.52f)
                reflectiveQuadTo(6.3f, 12.77f)
                reflectiveQuadTo(9.55f, 14.1f)
                close()
            }
        }.build()
        return _customSearchIcon!!
    }

private var _customSearchIcon: ImageVector? = null

public val GoogleGLogoIcon: ImageVector
    get() {
        if (_googleGLogoIcon != null) {
            return _googleGLogoIcon!!
        }
        _googleGLogoIcon = ImageVector.Builder(
            name = "google_g_logo",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f,
        ).apply {
            // Blue path
            path(fill = SolidColor(Color(0xFF4285F4))) {
                moveTo(22.56f, 12.25f)
                curveToRelative(0f, -0.78f, -0.07f, -1.53f, -0.2f, -2.25f)
                horizontalLineTo(12f)
                verticalLineToRelative(4.26f)
                horizontalLineToRelative(5.92f)
                curveToRelative(-0.26f, 1.37f, -1.04f, 2.53f, -2.21f, 3.31f)
                verticalLineToRelative(2.77f)
                horizontalLineToRelative(3.57f)
                curveToRelative(2.08f, -1.92f, 3.28f, -4.74f, 3.28f, -8.09f)
                close()
            }
            // Green path
            path(fill = SolidColor(Color(0xFF34A853))) {
                moveTo(12f, 23f)
                curveToRelative(2.97f, 0f, 5.46f, -0.98f, 7.28f, -2.66f)
                lineToRelative(-3.57f, -2.77f)
                curveToRelative(-0.98f, 0.66f, -2.23f, 1.06f, -3.71f, 1.06f)
                curveToRelative(-2.86f, 0f, -5.29f, -1.93f, -6.16f, -4.53f)
                horizontalLineTo(2.18f)
                verticalLineToRelative(2.84f)
                curveTo(3.99f, 20.53f, 7.7f, 23f, 12f, 23f)
                close()
            }
            // Yellow path
            path(fill = SolidColor(Color(0xFFFBBC05))) {
                moveTo(5.84f, 14.09f)
                curveToRelative(-0.22f, -0.66f, -0.35f, -1.36f, -0.35f, -2.09f)
                reflectiveCurveToRelative(0.13f, -1.43f, 0.35f, -2.09f)
                verticalLineTo(7.07f)
                horizontalLineTo(2.18f)
                curveTo(1.43f, 8.55f, 1f, 10.22f, 1f, 12f)
                reflectiveCurveToRelative(0.43f, 3.45f, 1.18f, 4.93f)
                lineToRelative(2.85f, -2.22f)
                lineToRelative(0.81f, -0.62f)
                close()
            }
            // Red path
            path(fill = SolidColor(Color(0xFFEA4335))) {
                moveTo(12f, 5.38f)
                curveToRelative(1.62f, 0f, 3.06f, 0.56f, 4.21f, 1.64f)
                lineToRelative(3.15f, -3.15f)
                curveTo(17.45f, 2.09f, 14.97f, 1f, 12f, 1f)
                curveTo(7.7f, 1f, 3.99f, 3.47f, 2.18f, 7.07f)
                lineToRelative(3.66f, 2.84f)
                curveToRelative(0.87f, -2.6f, 3.3f, -4.53f, 6.16f, -4.53f)
                close()
            }
        }.build()
        return _googleGLogoIcon!!
    }

private var _googleGLogoIcon: ImageVector? = null

