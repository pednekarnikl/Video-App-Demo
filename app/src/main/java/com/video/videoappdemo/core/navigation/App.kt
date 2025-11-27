package com.video.videoappdemo.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.video.videoappdemo.home.presentation.HomeScreen
import com.video.videoappdemo.ui.theme.VideoAppDemoTheme
import com.video.videoappdemo.video.presentation.VideoPlayerScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    VideoAppDemoTheme {
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("home") {
                HomeScreen { videoItem ->
                    // Use query parameters with URL encoding
                    val encodedUrl = URLEncoder.encode(videoItem.videoUrl, StandardCharsets.UTF_8.name())
                    val encodedTitle = URLEncoder.encode(videoItem.title, StandardCharsets.UTF_8.name())
                    val encodedChannel = URLEncoder.encode("Channel Name", StandardCharsets.UTF_8.name())
                    val encodedViews = URLEncoder.encode(videoItem.views, StandardCharsets.UTF_8.name())
                    val encodedDate = URLEncoder.encode(videoItem.uploadTime, StandardCharsets.UTF_8.name())

                    navController.navigate(
                        "videoPlayer?" +
                                "videoUrl=$encodedUrl&" +
                                "title=$encodedTitle&" +
                                "channelName=$encodedChannel&" +
                                "viewCount=$encodedViews&" +
                                "uploadDate=$encodedDate"
                    )
                }
            }

            composable(
                "videoPlayer?videoUrl={videoUrl}&title={title}&channelName={channelName}&viewCount={viewCount}&uploadDate={uploadDate}",
                arguments = listOf(
                    navArgument("videoUrl") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("title") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("channelName") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("viewCount") {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument("uploadDate") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                // Decode the parameters
                val videoUrl = backStackEntry.arguments?.getString("videoUrl")?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""

                val title = backStackEntry.arguments?.getString("title")?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""

                val channelName = backStackEntry.arguments?.getString("channelName")?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""

                val viewCount = backStackEntry.arguments?.getString("viewCount")?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""

                val uploadDate = backStackEntry.arguments?.getString("uploadDate")?.let {
                    java.net.URLDecoder.decode(it, StandardCharsets.UTF_8.name())
                } ?: ""

                VideoPlayerScreen(
                    videoUrl = videoUrl,
                    videoTitle = title,
                    channelName = channelName,
                    viewCount = viewCount,
                    uploadDate = uploadDate
                )
            }
        }
    }
}