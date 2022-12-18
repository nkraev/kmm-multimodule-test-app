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
import androidx.compose.ui.tooling.preview.Preview
import com.example.wbdtestapp.di.photosRepo
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView("Test")
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    val scope = rememberCoroutineScope()
    var searchQuery: String by remember { mutableStateOf("sunset") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = text)
        TextField(value = searchQuery, onValueChange = { searchQuery = it })
        Button(
            enabled = searchQuery.isNotEmpty(),
            content = {
                Text("Click me to send an API request")
            },
            onClick = {
                scope.launch {
                    println(">> Executing query=$searchQuery...")
                    kotlin.runCatching {
                        photosRepo.getPhotos(searchQuery)
                    }.onFailure {
                        it.printStackTrace()
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
