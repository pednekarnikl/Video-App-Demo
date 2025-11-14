package com.video.videoappdemo.home.data.mapper

import com.video.videoappdemo.home.data.remote.dto.VideoItem
import com.video.videoappdemo.home.domain.model.VideoModel

fun VideoItem.toVideoModel() = VideoModel(
    title = title,
    description = description,
    thumbnailUrl = thumbnailUrl,
    videoUrl = videoUrl,
    author = author,
    duration = duration,
    uploadTime = uploadTime,
    views = views,
)