package com.ivancho.apptest.models

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("id") var postId: Int,
    @SerializedName("userId") var userId: Int,
        @SerializedName("title") var name: String,
    @SerializedName("body") var body: String,
    var isFavorite: Boolean
)