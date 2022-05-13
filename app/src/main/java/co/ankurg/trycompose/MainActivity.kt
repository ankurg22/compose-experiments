package co.ankurg.trycompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import co.ankurg.trycompose.historicalnotes.HistoricalNotesActivity
import co.ankurg.trycompose.perspectiveui.PerspectiveUI
import co.ankurg.trycompose.ui.theme.AppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            val intent = Intent(context, HistoricalNotesActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Historical Notes", color = Color.Black)
        }
        Button(onClick = {
            val intent = Intent(context, PerspectiveUI::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Perspective UI", color = Color.Black)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    AppTheme {
        MainScreen()
    }
}