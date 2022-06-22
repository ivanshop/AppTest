package com.ivancho.apptest.repository

import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User


class Repository {
    suspend fun getAllPosts(): List<Post> {
        return RetrofitInstance.api.getAllPosts()
    }

    suspend fun getCommentsByPostId(postId: Int): List<Comment> {
        return RetrofitInstance.api.getCommentsByPostId(postId)
    }

    suspend fun getUserById(userId: Int): User {
        return RetrofitInstance.api.getUserById(userId)
    }
}