package com.ivancho.apptest.repository

import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User
import retrofit2.Response


class Repository {
    suspend fun getAllPosts(): List<Post> {
        return RetrofitInstance.api.getAllPosts()
    }

    suspend fun getPostById(idPost: Int): Response<Post> {
        return RetrofitInstance.api.getPostById(idPost)
    }

    suspend fun getPostsByUserId(userId: Int): Response<List<Post>> {
        return RetrofitInstance.api.getPostsByUserId(userId)
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        return RetrofitInstance.api.getCommentsByPostId(postId)
    }

    suspend fun getUserById(userId: Int): User {
        return RetrofitInstance.api.getUserById(userId)
    }

    suspend fun addPost(post: Post): Response<Post> {
        return RetrofitInstance.api.AddPost(post)
    }
}