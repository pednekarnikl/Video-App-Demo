import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularLoader(
    size: Dp = 40.dp,
    stroke: Dp = 4.dp
) {
    val angle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        angle.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(900, easing = LinearEasing)
            )
        )
    }

    Canvas(
        modifier = Modifier
            .size(size)
            .rotate(angle.value)
    ) {
        drawArc(
            color = Color.Gray,
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(stroke.toPx(), cap = StrokeCap.Round)
        )
    }
}
