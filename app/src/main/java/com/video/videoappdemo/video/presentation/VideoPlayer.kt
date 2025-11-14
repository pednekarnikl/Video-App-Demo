package com.video.videoappdemo.video.presentation

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


@Composable
fun VideoPlayer(url: String) {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true
            }
        }
    )
}
