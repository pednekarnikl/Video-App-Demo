package com.video.videoappdemo.home.presentation

data class UiVideoListItem(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val videoUrl: String,

    val author:String,
    val duration:String,
    val uploadTime:String,
    val views:String,
)