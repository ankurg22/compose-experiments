package co.ankurg.trycompose.perspectiveui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ankurg.trycompose.R
import co.ankurg.trycompose.ui.theme.AppTheme

class PerspectiveUI : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFFE6AC6B)),
                    contentAlignment = Alignment.Center
                ) {
                    PerspectiveScreen()
                }
            }
        }
    }
}

@Composable
fun PerspectiveScreen() {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        )
    )
    var registered by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    val offset by animateFloatAsState(targetValue = sliderPosition)
    val sensorManager =
        LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    if (!registered) sensorManager?.registerListener(
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                registered = true
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    val values = event.values
                    val x = values[0]
                    val y = values[1]
                    val z = values[2]

                    sliderPosition = x * 10F
                    println("x: $x y: $y z: $z")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_UI
    )

    Column {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.sun2),
                contentDescription = "Sun",
                modifier = Modifier
//                    .offset((20 * yAxis.roundToInt()).dp, (-20 * xAxis.roundToInt()).dp)
                    .offset(
                        if (sliderPosition >= 0) {
                            (offset * 2).dp
                        } else {
                            (offset * 2).dp
                        },
                        if (sliderPosition > 0) {
                            (offset * 2).dp
                        } else {
                            -(offset * 2).dp
                        }
                    )
                    .align(Alignment.TopCenter)
                    .size(64.dp)
                    .rotate(angle)
            )
        }

        Spacer(modifier = Modifier.height(92.dp))

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .width(300.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(
                        (-2.5 * (offset.toInt() / 10)).dp,
//                        (-2 * range[sliderPosition.toInt()]).dp,
                        (24).dp
                    )
                    .height(56.dp)
                    .width((200).dp)
                    .advancedShadow(
                        color = Color(0xFF863911),
                        alpha = 0.9F,
                        cornersRadius = 160.dp,
                        shadowBlurRadius = 20.dp
                    )
            )
            Box(
                modifier = Modifier
                    .offset(
                        (offset / 10).dp,
//                        range[sliderPosition.toInt()].dp,
                        (0).dp
                    )
                    .height(60.dp)
                    .width((200).dp)
                    .background(color = Color.White, shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .width((200).dp)
                    .background(
                        color = Color(0xFFDBB884),
                        shape = CircleShape
                    )
                    .shadow(0.dp, shape = CircleShape)
                    .wrapContentSize(align = Alignment.Center)
            ) {
                Text(
                    text = "Submit",
                    color = Color(0xFF391401),
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    AppTheme {
        PerspectiveScreen()
    }
}
