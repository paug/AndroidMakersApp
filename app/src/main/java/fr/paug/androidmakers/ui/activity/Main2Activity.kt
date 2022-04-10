package fr.paug.androidmakers.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainLayout()
        }
    }
}


// TODO Replace with real layouts ;)

@Composable
fun AgendaLayout(
    onSessionClick: (sessionId: String) -> Unit
) {
    Button(onClick = { onSessionClick("42") }) {
        Text(text = "Agenda")
    }
}

@Composable
fun VenueLayout() {
    Text("Venue")
}

@Composable
fun AboutLayout() {
    Text("About")
}

@Composable
fun SessionDetailLayout(sessionId: String) {
    Text("Session Detail sessionId=$sessionId")
}
