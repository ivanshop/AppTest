package com.ivancho.apptest

import android.os.Bundle
import android.text.style.BackgroundColorSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.*
import com.ivancho.apptest.repository.Repository
import kotlinx.coroutines.launch
import com.ivancho.apptest.ui.theme.AppTestTheme
import com.ivancho.apptest.viewmodels.MainViewModel
import com.ivancho.apptest.viewmodels.MainViewModelFactory

lateinit var viewModel: MainViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var navController: NavHostController
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getAllPosts()

        setContent {
            AppTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    SetupNavGraph(navController = navController, viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(navController: NavController, viewModel: MainViewModel) {
    val tabs = listOf("ALL", "FAVORITES")
    val pagerState = rememberPagerState()
    Scaffold(
        topBar = { TopBar() },
        floatingActionButton = { DeleteFAB() },
    ) { contentPadding ->
        Column(modifier = Modifier.padding(contentPadding)) {
            Tabs(tabs = tabs, pagerState = pagerState)
            TabsContent(tabs = tabs, pagerState = pagerState, navController, viewModel)
        }
    }
}

@Composable
fun DeleteFAB() {
    FloatingActionButton(onClick = {
        viewModel.allPosts.clear()
    }) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Remove",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), viewModel = viewModel)
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            Text(text = stringResource(R.string.app_name), fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                viewModel.getAllPosts()
            }) {
                Icon(imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh")
            }
        },
        contentColor = Color.White,
        elevation = 0.dp
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar()
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<String>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }) {
        tabs.forEachIndexed { index, _->
            Tab(
                text = {
                    Text(
                        tabs[index],
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    val tabs = listOf("ALL", "FAVORITES")
    val pagerState = rememberPagerState()
    Tabs(tabs = tabs, pagerState = pagerState)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    tabs: List<String>,
    pagerState: PagerState,
    navController: NavController,
    viewModel: MainViewModel
) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        when(page) {
            0 -> AllPostsScreen(navController, viewModel)
            1 -> FavoritePostsScreen(navController, viewModel)
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabsContentPreview() {
    val tabs = listOf("ALL", "FAVORITES")
    val pagerState = rememberPagerState()
    TabsContent(
        tabs = tabs,
        pagerState = pagerState,
        navController = rememberNavController(),
        viewModel = viewModel
    )
}