package com.ivancho.apptest

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.viewmodels.MainViewModel

@Composable
fun PostItem(title: String, body: String) {
    Column() {
        Text(
            text = title,
            color = MaterialTheme.colors.primary,
            style = MaterialTheme.typography.h5,
            maxLines = 1)
        Text(
            text = body,
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.subtitle2,
            maxLines = 1
        )
    }
}


@Composable
fun PostComponent(post: Post, navController: NavController, viewModel: MainViewModel) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(route = Screen.Detail.passIdPost(post.postId))
            }
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(8.dp)
    ) {
        Icon(
            Icons.Default.Circle,"New",
            Modifier
                .size(30.dp)
                .padding(8.dp))
        Column(modifier = Modifier
            .weight(1.0f, true)) {
            PostItem(post.name, post.body)
        }
        if (post.isFavorite) {
            Icon(
                Icons.Default.Star,"Like",
                Modifier
                    .size(40.dp)
                    .padding(8.dp, 0.dp)
            )
        }
    }
}

