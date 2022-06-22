package com.ivancho.apptest.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User
import com.ivancho.apptest.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository):
    ViewModel() {

    val post: MutableLiveData<Response<Post>> = MutableLiveData()
    var allPosts: MutableList<Post> by mutableStateOf(ArrayList())
    var user: User by mutableStateOf(User(0,"", "", "", "", ""))
    val postsByUser: MutableLiveData<Response<List<Post>>> = MutableLiveData()
    var commentsPost: List<Comment> by mutableStateOf(listOf())

    var errorMessage: String by mutableStateOf("")

    fun getAllPosts() {
        viewModelScope.launch {
            try {
                val response: List<Post> = repository.getAllPosts()
                allPosts = response.toMutableList()
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun getPostById(idPost: Int) {
        viewModelScope.launch {
            val response: Response<Post> = repository.getPostById(idPost)
            post.value = response
        }
    }

    fun getPostsByUserId(userId: Int) {
        viewModelScope.launch {
            val response: Response<List<Post>> = repository.getPostsByUserId(userId)
            postsByUser.value = response
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

    fun addPost(postToAdd: Post) {
        viewModelScope.launch {
            val response: Response<Post> = repository.addPost(postToAdd)
            post.value = response
        }
    }
}