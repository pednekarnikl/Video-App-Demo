package com.video.videoappdemo.home.domain.usecase

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.core.domain.map
import com.video.videoappdemo.home.data.mapper.toVideoModel
import com.video.videoappdemo.home.domain.model.VideoModel
import com.video.videoappdemo.home.domain.repository.VideoRepository

class GetVideoUseCase(
    private val videoRepository: VideoRepository,
) {

    suspend fun execute(): Result<List<VideoModel>, DataError.Remote> {
        return videoRepository.getVideosApi().map { dto ->
            dto.map { it.toVideoModel() }
        }
    }

}