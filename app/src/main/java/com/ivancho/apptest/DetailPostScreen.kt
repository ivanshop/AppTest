package com.ivancho.apptest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User
import com.ivancho.apptest.viewmodels.MainViewModel


@Composable
fun DetailPost(
    navController: NavController,
    postId: Int,
    viewModel: MainViewModel
) {
    viewModel.getCommentsByPostId(postId)
    if (viewModel.commentsPost.isNotEmpty()) {
        val post = viewModel.allPosts.first{it.postId == postId}
        viewModel.getUserById(post.userId)
        Scaffold(
            topBar = { TopBarDetail(navController, post) },
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                Column(modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)) {
                    Description(post)
                    Spacer(modifier = Modifier.height(16.dp))
                    User(viewModel.user)
                    Spacer(modifier = Modifier.height(16.dp))
                    Comments(viewModel.commentsPost)
                }
            }
        }
    }
}

@Composable
fun Comments(comments: List<Comment>) {
    Column() {
        Text(
            text = "COMMENTS",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        comments.forEach {
            Text(text = it.body)
            Divider(
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun User(user: User) {
    Column() {
        Text(
            text = "User",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(user.name, fontSize = 16.sp)
        Text(user.email, fontSize = 16.sp)
        Text(user.phone, fontSize = 16.sp)
        Text(user.website, fontSize = 16.sp)
    }
}

@Composable
fun Description(post: Post) {
    Column() {
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            text = post.body,
            fontSize = 18.sp
        )
    }
}

@Composable
fun TopBarDetail(navController: NavController, post: Post) {

    var isFavorite by remember { mutableStateOf(post.isFavorite) }
    TopAppBar(
        title = {
            Text(text = "Detail post", color = Color.White)
        },
        actions = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Like"
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                isFavorite = !isFavorite
                if (isFavorite) {
                    viewModel.favoritePosts.add(post)
                } else {
                    viewModel.favoritePosts.remove(post)
                }
                viewModel.allPosts?.find { it.postId == post.postId }?.isFavorite = isFavorite
            }) {
                if (isFavorite) {
                    Icon(imageVector = Icons.Default.Star,
                        tint = Color.Yellow,
                        contentDescription = "Like")
                } else {
                    Icon(imageVector = Icons.Outlined.StarOutline,
                        contentDescription = "Like")
                }
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 0.dp
    )
}
