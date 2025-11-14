package com.video.videoappdemo.home.domain.model

data class VideoModel(
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val videoUrl: String,

    val author:String,
    val duration:String,
    val uploadTime:String,
    val views:String,

)