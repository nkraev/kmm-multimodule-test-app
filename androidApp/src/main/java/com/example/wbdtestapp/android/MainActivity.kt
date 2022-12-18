package com.example.wbdtestapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wbdtestapp.di.Dependencies
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val dependencies by lazy { Dependencies(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    GreetingView("Test", dependencies)
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String, dependencies: Dependencies) {
    val scope = rememberCoroutineScope()
    var searchQuery: String by remember { mutableStateOf("sunset") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text)
        TextField(value = searchQuery, onValueChange = { searchQuery = it })
        Button(enabled = searchQuery.isNotEmpty(), content = {
            Text("Click me to send an API request")
        }, onClick = {
            scope.launch {
                println(">> Executing query=$searchQuery...")
                dependencies.photosRepo.getPhotos(searchQuery).collectLatest { photos ->
                    println(">> Received in UI: $photos")
                }
            }
        })
    }
}