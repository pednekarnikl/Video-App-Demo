package com.video.videoappdemo.home.data.remote.dto

import kotlinx.serialization.Serializable

typealias VideoResponse = List<VideoItem>

@Serializable
data class VideoItem(
    val author: String,
    val description: String,
    val duration: String,
    val id: String,
    val isLive: Boolean,
    val subscriber: String,
    val thumbnailUrl: String,
    val title: String,
    val uploadTime: String,
    val videoUrl: String,
    val views: String
)
