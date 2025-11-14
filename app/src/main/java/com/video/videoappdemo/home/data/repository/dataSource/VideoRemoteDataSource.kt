package com.video.videoappdemo.home.data.repository.dataSource

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.home.data.remote.dto.VideoResponse
import com.video.videoappdemo.core.domain.Result

interface VideoRemoteDataSource {

    suspend fun getVideosApi(): Result<VideoResponse, DataError.Remote>

}