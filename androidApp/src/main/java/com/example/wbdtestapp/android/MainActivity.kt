package com.example.wbdtestapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wbdtestapp.android.composables.DetailsScreen
import com.example.wbdtestapp.android.composables.SearchResultsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                NavHost(navController = navController, startDestination = "search") {
                    composable("search") {
                        SearchResultsScreen(onNavigateToDetails = { imageId ->
                            navController.navigate("details/${imageId}")
                        })
                    }
                    composable(
                        "details/{imageId}", arguments = listOf(navArgument("imageId") { type = NavType.LongType })
                    ) { navBackStackEntry -> DetailsScreen(navBackStackEntry.arguments?.getLong("imageId")) }/*...*/
                }
            }
        }
    }
}