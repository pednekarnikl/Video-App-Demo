package com.video.videoappdemo.home.data.repository.dataSourceImpl

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.core.network.safeCall
import com.video.videoappdemo.home.data.remote.dto.VideoResponse
import com.video.videoappdemo.home.data.repository.dataSource.VideoRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://gist.githubusercontent.com/poudyalanil/ca84582cbeb4fc123a13290a586da925/raw/videos.json"

class VideoRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : VideoRemoteDataSource {

    override suspend fun getVideosApi(): Result<VideoResponse, DataError.Remote> {
        return safeCall {
            httpClient.get(BASE_URL)
        }
    }
}