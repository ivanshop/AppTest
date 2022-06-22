package com.ivancho.apptest

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ivancho.apptest.utils.Constants.Companion.ARGUMENT_POST_ID
import com.ivancho.apptest.viewmodels.MainViewModel


@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {MainScreen(navController, viewModel) }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(ARGUMENT_POST_ID){
                type = NavType.IntType
            })
        ) {
            val postId = it.arguments?.getInt(ARGUMENT_POST_ID)
            requireNotNull(postId)
            DetailPost(navController, postId, viewModel)
        }
    }
}