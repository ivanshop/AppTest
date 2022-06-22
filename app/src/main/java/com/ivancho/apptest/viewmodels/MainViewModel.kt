package com.ivancho.apptest.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User
import com.ivancho.apptest.repository.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository):
    ViewModel() {

    var allPosts: List<Post> by mutableStateOf(listOf())
    var favoritePosts: MutableList<Post> by mutableStateOf(ArrayList())
    var user: User by mutableStateOf(User(0,"", "", "", "", ""))
    var commentsPost: List<Comment> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")

    fun getAllPosts() {
        viewModelScope.launch {
            try {
                val response: List<Post> = repository.getAllPosts()
                favoritePosts.forEach { favorite ->
                    response.first { post ->
                        post.postId == favorite.postId
                    }.isFavorite = true
                }
                allPosts = response.toMutableList()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getCommentsByPostId(postId: Int) {
        viewModelScope.launch {
            try {
                val response: List<Comment> = repository.getCommentsByPostId(postId)
                commentsPost = response
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            val response: User = repository.getUserById(userId)
            user = response
        }
    }
}