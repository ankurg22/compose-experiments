package co.ankurg.trycompose.historicalnotes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.ankurg.trycompose.ui.theme.ALMOST_WHITE
import co.ankurg.trycompose.ui.theme.BACK

data class Note(val date: String, val title: String, val note: String)

val notes = getNoteList()

@Composable
fun NoteCard(note: Note) {
    Spacer(modifier = Modifier.height(128.dp))
    Card(backgroundColor = ALMOST_WHITE) {
        Column(modifier = Modifier.padding(32.dp)) {
            Text(
                text = note.date,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))
            Divider(color = BACK)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = note.note,
                style = MaterialTheme.typography.body1,
                lineHeight = 24.sp,
                color = Color.Gray
            )
        }
    }
}

fun getNoteList(): ArrayList<Note> {
    val listOfNotes = arrayListOf<Note>()
    for (i in 0..4) {
        val note = Note(
            "December 25, 2021",
            "Merry Christmas",
            LoremIpsum(70).values.joinToString(separator = " ")
        )
        listOfNotes.add(note)
    }
    return listOfNotes
}

@Preview(showBackground = true)
@Composable
fun NotePreview() {
    NoteCard(notes[0])
}
