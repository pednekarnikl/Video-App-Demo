package com.video.videoappdemo.video.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun VideoPlayerScreen(
    videoUrl : String,
    videoTitle : String,
    channelName : String,
    viewCount : String,
    uploadDate : String,

) {

    Box (contentAlignment = Alignment.Center,
        modifier = Modifier.background(Color.Black)){
        VideoPlayer(
            url = videoUrl,
            videoTitle = videoTitle,

            onBackClick = {},

        )

    }
}