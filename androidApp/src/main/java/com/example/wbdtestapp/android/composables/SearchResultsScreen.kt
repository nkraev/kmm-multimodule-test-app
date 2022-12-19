package com.example.wbdtestapp.android.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SearchResultsScreen(onNavigateToDetails: (Long) -> Unit) {
    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Scaffold(
        topBar = {
            TopAppBar(actions = {
                IconButton(onClick = {
                    searchVisible = !searchVisible
                }) {
                    Icon(
                        Icons.Filled.Search, contentDescription = "Button used to search for images"
                    )
                }
            }, title = {
                if (searchVisible) {
                    TextField(
                        value = query,
                        placeholder = { Text("Enter search term...") },
                        onValueChange = { query = it },
                        modifier = Modifier.focusRequester(focusRequester),
                        singleLine = true,
                    )
                    LaunchedEffect(Unit) {
                        focusRequester.requestFocus()
                    }
                } else Text("Search")
            })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
                
            })
        }
    }
}

@Preview
@Composable
fun SearchResultsScreenPreview() {
    SearchResultsScreen(onNavigateToDetails = {})
}