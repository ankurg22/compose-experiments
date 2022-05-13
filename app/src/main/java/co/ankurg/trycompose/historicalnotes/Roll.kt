package co.ankurg.trycompose.historicalnotes

import android.animation.TimeInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.ankurg.trycompose.ui.theme.DARKBACK
import co.ankurg.trycompose.ui.theme.GRAY
import kotlin.math.abs

data class RollState(val height: Dp, val endRotation: Float = 0F, val displacement: Dp = 0.dp)

@Composable
fun RollBar(rolls: Array<RollState>) {
    for (state in rolls) {
        val rotation by animateFloatAsState(
            state.endRotation,
            animationSpec =
            tween(
                durationMillis = 800,
                easing = DecelerateInterpolator().toEasing(),
                delayMillis = 0
            )
        )
        val displacement by animateDpAsState(
            targetValue = if (state.displacement > 0.dp) state.displacement else 0.dp,
            animationSpec = tween(
                durationMillis = 800,
                easing = DecelerateInterpolator().toEasing(),
            )
        )
        Box(
            modifier = Modifier
                .height(state.height + if (state.displacement > 0.dp) 20.dp else 0.dp)
                .offset(y = -displacement)
                .rotate(rotation)
                .background(
                    brush = if (state.displacement > 0.dp) Brush.verticalGradient(
                        colors = listOf(GRAY, Color.Transparent)
                    ) else Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Transparent
                        )
                    )
                )

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(state.height)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                DARKBACK,
                                DARKBACK,
                                Color.White,
                                DARKBACK
                            )
                        )
                    )
                    .shadow(elevation = 0.5.dp)
            )
        }
    }
}

fun getRotationForIndex(
    lastCompletelyScrolledItemIndex: Int,
    rollStates: SnapshotStateMap<Int, RollState>
): Float {
    return if (lastCompletelyScrolledItemIndex == 0) {
        (5..10).random().toFloat()
    } else {
        val negativeMultiplier = (listOf(-1, 1)).random()
        val previousRollRotation = rollStates[lastCompletelyScrolledItemIndex - 1]!!.endRotation
        negativeMultiplier * (0..abs(previousRollRotation).toInt()).random().toFloat()
    }
}

fun TimeInterpolator.toEasing() = Easing { x ->
    getInterpolation(x)
}
