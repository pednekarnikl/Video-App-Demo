package com.video.videoappdemo.home.presentation

import androidx.compose.runtime.Stable

@Stable
data class VideoState(
    val error: String? = null,
    val videos: List<UiVideoListItem> = emptyList(),
)