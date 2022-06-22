package com.ivancho.apptest

import com.ivancho.apptest.utils.Constants.Companion.ARGUMENT_POST_ID

sealed class Screen(val route: String) {
    object Main: Screen(route = "main_screen")
    object Detail: Screen(route = "detail_screen/{$ARGUMENT_POST_ID}") {
        fun passIdPost(postId: Int): String {
            return this.route.replace(
                "{$ARGUMENT_POST_ID}", postId.toString()
            )
        }
    }
}
