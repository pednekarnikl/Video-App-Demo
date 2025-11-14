package com.video.videoappdemo.home.data.repository

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.home.data.remote.dto.VideoResponse
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.home.data.repository.dataSource.VideoRemoteDataSource
import com.video.videoappdemo.home.domain.repository.VideoRepository


class VideoRepositoryImpl(
    private val videoRemoteDataSource: VideoRemoteDataSource
): VideoRepository {

    override suspend fun getVideosApi(): Result<VideoResponse, DataError.Remote>
    = videoRemoteDataSource.getVideosApi()

}