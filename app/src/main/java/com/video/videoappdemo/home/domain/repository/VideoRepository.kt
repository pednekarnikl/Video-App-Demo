package com.video.videoappdemo.home.domain.repository

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.home.data.remote.dto.VideoResponse
import com.video.videoappdemo.core.domain.Result

interface VideoRepository {
    suspend fun getVideosApi(): Result<VideoResponse, DataError.Remote>
}