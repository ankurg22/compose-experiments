package co.ankurg.trycompose.historicalnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.ankurg.trycompose.ui.theme.AppTheme

class HistoricalNotesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                NoteScreen()
            }
        }
    }
}

@Composable
fun NoteScreen() {
    Surface {
        val rollStates = remember { mutableStateMapOf<Int, RollState>() }
        var scrolledItemIndex by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            val listState = rememberLazyListState()
            println(listState.firstVisibleItemScrollOffset)
            Text(
                text = "Historical Letters",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 64.dp)
            )
            val firstVisibleItemIndex = listState.firstVisibleItemIndex
            val firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset

            rollStates[firstVisibleItemIndex] = RollState((firstVisibleItemScrollOffset / 80).dp)

            if (firstVisibleItemIndex != scrolledItemIndex &&
                scrolledItemIndex <= firstVisibleItemIndex
            ) {
                val endRotation = getRotationForIndex(scrolledItemIndex, rollStates)
                rollStates[scrolledItemIndex] = rollStates[scrolledItemIndex]!!.copy(
                    endRotation = endRotation,
                    displacement = 20.dp
                )

            }
            scrolledItemIndex = firstVisibleItemIndex

            RollBar(rollStates.values.toTypedArray())

            LazyColumn(
                content = {
                    this.items(notes) { note ->
                        NoteCard(note = note)
                    }
                },
                contentPadding = PaddingValues(bottom = 1000.dp),
                state = listState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    AppTheme {
        NoteScreen()
    }
}
