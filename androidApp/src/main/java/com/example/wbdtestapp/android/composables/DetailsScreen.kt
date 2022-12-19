package com.example.wbdtestapp.android.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.wbdtestapp.android.uimodel.UIPhoto
import com.example.wbdtestapp.android.viewmodel.DetailsState
import com.example.wbdtestapp.android.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(imageId: Long?, viewModel: DetailsViewModel, navigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                IconButton(onClick = { navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Go back")
                }
            }, title = {
                Text("Image Details")
            })
        },
    ) { paddingValues ->
        GuardForNoImageId(imageId = imageId, paddingValues = paddingValues, viewModel = viewModel)


    }
}

@Composable
fun GuardForNoImageId(imageId: Long?, paddingValues: PaddingValues, viewModel: DetailsViewModel) {
    when (imageId) {
        null -> Text(text = "Can't load image, imageId unknown")
        else -> FullScreen(imageId = imageId, viewModel, paddingValues)
    }
}

@Composable
fun FullScreen(imageId: Long, viewModel: DetailsViewModel, paddingValues: PaddingValues) {
    val state by viewModel.state.observeAsState(DetailsState.Loading)

    LaunchedEffect(Unit) {
        viewModel.getUrl(imageId)
    }

    when (state) {
        is DetailsState.Loading -> CircularProgressIndicator()
        is DetailsState.ImageReady -> FullScreenImage(
            photo = (state as DetailsState.ImageReady).photo, paddingValues
        )
    }
}

@Composable
fun FullScreenImage(photo: UIPhoto, paddingValues: PaddingValues) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        AsyncImage(
            model = photo.url,
            contentDescription = "Full screen image from Flickr selected on the previous page",
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = photo.title)
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    FullScreen(
        imageId = 52571795397,
        viewModel = viewModel(),
        paddingValues = PaddingValues(16.dp)
    )
}