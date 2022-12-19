package com.example.wbdtestapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.Coil
import com.example.wbdtestapp.android.composables.DetailsScreen
import com.example.wbdtestapp.android.composables.SearchResultsScreen
import com.example.wbdtestapp.android.imageloader.ImageLoaderHelper
import com.example.wbdtestapp.android.viewmodel.SearchResultsViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Coil.setImageLoader(ImageLoaderHelper.get(this))

        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                NavHost(navController = navController, startDestination = "search") {
                    composable("search") {
                        SearchResultsScreen(viewModel = viewModel(factory = SearchResultsViewModel.Factory),
                            onNavigateToDetails = { imageId ->
                                navController.navigate("details/${imageId}")
                            })
                    }
                    composable(
                        "details/{imageId}",
                        arguments = listOf(navArgument("imageId") { type = NavType.LongType })
                    ) { navBackStackEntry -> DetailsScreen(navBackStackEntry.arguments?.getLong("imageId")) }/*...*/
                }
            }
        }
    }
}