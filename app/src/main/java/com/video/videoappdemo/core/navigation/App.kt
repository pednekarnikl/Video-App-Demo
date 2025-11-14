package com.video.videoappdemo.core.navigation


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.video.videoappdemo.home.presentation.HomeScreen
import com.video.videoappdemo.ui.theme.VideoAppDemoTheme
import com.video.videoappdemo.video.presentation.VideoPlayerScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class VideoPlayer(val videoUrl: String)

@Composable
@Preview
fun App() {
    val navController: NavHostController = rememberNavController()

    VideoAppDemoTheme {
        NavHost(
            navController = navController,
            startDestination = HomeScreen::class,
            modifier = Modifier.fillMaxSize()
        ) {

            composable<HomeScreen> {
                HomeScreen {
                    navController.navigate(VideoPlayer(it))
                }
            }

            composable<VideoPlayer> { backStackEntry ->
                val videoUrl = backStackEntry.toRoute<VideoPlayer>().videoUrl
                VideoPlayerScreen(videoUrl)
            }

        }
    }
}
