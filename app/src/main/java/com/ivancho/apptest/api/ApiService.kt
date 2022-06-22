package com.ivancho.apptest.api

import com.ivancho.apptest.models.Comment
import com.ivancho.apptest.models.Post
import com.ivancho.apptest.models.User
import retrofit2.http.*

interface ApiService {

    @GET("posts")
    suspend fun getAllPosts() : List<Post>

    @GET("comments")
    suspend fun getCommentsByPostId(
        @Query("postId") postId: Int
    ) : List<Comment>

    @GET("users/{userId}")
    suspend fun getUserById(
        @Path("userId") userId: Int
    ) : User

}