package com.example.wbdtestapp.android.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.wbdtestapp.android.uimodel.UIPhoto
import com.example.wbdtestapp.android.viewmodel.SearchResultsViewModel
import com.example.wbdtestapp.android.viewmodel.SearchState


@Composable
fun SearchResultsScreen(viewModel: SearchResultsViewModel, onNavigateToDetails: (Long) -> Unit) {
    var searchVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val state by viewModel.state.observeAsState()
    val hostState = remember { SnackbarHostState() }

    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = hostState),
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
                        onValueChange = {
                            query = it
                            viewModel.search(it)
                        },
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
            println(">> Received state: $state")
            when (state) {
                is SearchState.Empty -> Text("Please start searching above...")
                is SearchState.Loading -> CircularProgressIndicator()
                null, is SearchState.Error -> LaunchedEffect(Unit) {
                    val errorMessage = (state as? SearchState.Error)?.errorMessage
                        ?: "Unknown error, state is null"
                    hostState.showSnackbar(
                        errorMessage, duration = SnackbarDuration.Long
                    )
                }

                is SearchState.SearchReady -> {
                    val searchReady = (state as? SearchState.SearchReady) ?: return@Column
                    println(">> [Android] Received photos: ${searchReady.photos}")
                    SearchResults(photos = searchReady.photos)
                }
            }
        }
    }
}

@Composable
fun SearchResults(photos: List<UIPhoto>) {
    LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3), content = {
        items(photos, key = { it.id }) { photo ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.aspectRatio(1f),
            ) {
                AsyncImage(
                    model = photo.url,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(108.dp),
                    contentDescription = "Photo from Flickr",
                    contentScale = ContentScale.Crop,
                )
                Text(text = photo.title)
            }
        }
    })
}

@Preview
@Composable
fun SearchResultsScreenPreview() {
    SearchResults(
        photos = listOf(
            UIPhoto(1, "", "Hello"),
            UIPhoto(2, "", "World"),
            UIPhoto(3, "", "Oh"),
            UIPhoto(4, "", "Wow"),
        )
    )
}