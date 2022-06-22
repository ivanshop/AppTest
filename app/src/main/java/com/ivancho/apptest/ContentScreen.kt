package com.ivancho.apptest

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.ivancho.apptest.viewmodels.MainViewModel

@Composable
fun AllPostsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
        items(viewModel.allPosts.sortedByDescending { it.isFavorite }){ post ->
            PostComponent(post, navController, viewModel)
        }
    }
}

@Composable
fun FavoritePostsScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()) {
        items(viewModel.allPosts.filter { it.isFavorite }){ post ->
            PostComponent(post, navController, viewModel)
        }
    }
}

