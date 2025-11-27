package com.video.videoappdemo.video.presentation

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    url: String,
    videoTitle: String = "Video Title",
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {
                val mediaItem = MediaItem.fromUri(url)
                setMediaItem(mediaItem)

                addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(playbackState: Int) {
                        when (playbackState) {
                            Player.STATE_BUFFERING -> isLoading = true
                            Player.STATE_READY -> {
                                isLoading = false
                                hasError = false
                            }

                            Player.STATE_ENDED -> {
                                isLoading = false
                            }

                            Player.STATE_IDLE -> isLoading = false
                        }
                    }

                    override fun onPlayerError(error: PlaybackException) {
                        hasError = true
                        errorMessage = when {
                            error.errorCode == PlaybackException.ERROR_CODE_IO_NETWORK_CONNECTION_FAILED ->
                                "Network error. Please check your connection."

                            error.errorCode == PlaybackException.ERROR_CODE_IO_BAD_HTTP_STATUS ->
                                "Video not available."

                            else -> "Playback error: ${error.message ?: "Unknown error"}"
                        }
                        isLoading = false
                    }
                })

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // Video Player with 16:9 aspect ratio
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(Color.Black)
            ) {
                AndroidView(
                    factory = { context ->
                        PlayerView(context).apply {
                            player = exoPlayer
                            useController = true
                            setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                        }
                    },
                    modifier = Modifier.matchParentSize()
                )

                // Loading Indicator
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Error Message
                if (hasError) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "⚠️",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        androidx.compose.material3.Button(
                            onClick = {
                                hasError = false
                                exoPlayer.prepare()
                                exoPlayer.playWhenReady = true
                            }
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // Video Title at the Bottom
        Text(
            text = videoTitle,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
