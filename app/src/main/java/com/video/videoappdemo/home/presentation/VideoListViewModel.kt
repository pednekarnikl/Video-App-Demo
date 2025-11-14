package com.video.videoappdemo.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.home.domain.usecase.GetVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import com.video.videoappdemo.core.domain.Result
import kotlinx.coroutines.delay

class VideoListViewModel(
    private val getVideoUseCase: GetVideoUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(VideoState())
    val state = _state
        .onStart {
            //todo remove this after crash fix
            delay(1000)
            getAllVideos()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = VideoState()
        )

    private suspend fun getAllVideos() {
        when (val videosResponse = getVideoUseCase.execute()) {
            is Result.Success -> {
                _state.update {
                    VideoState(
                        videos = videosResponse.data.map { videoItem ->
                            UiVideoListItem(
                                title = videoItem.title,
                                description = videoItem.description,
                                thumbnailUrl = videoItem.thumbnailUrl,
                                videoUrl = videoItem.videoUrl,
                                author = videoItem.author,
                                duration = videoItem.duration,
                                uploadTime = videoItem.uploadTime,
                                views = videoItem.views
                            )
                        }
                    )
                }
            }

            is Result.Error -> {
                _state.update {
                    it.copy(
                        videos = emptyList(),
                        error = videosResponse.error.getErrorString(),
                    )
                }
            }
        }
    }
}

public fun com.video.videoappdemo.core.domain.Error.getErrorString(): String = when (this) {
    DataError.Remote.REQUEST_TIMEOUT -> "Request timed out. \nThe server took too long to respond."
    DataError.Remote.TOO_MANY_REQUESTS -> "Too many requests. \nTry again later."
    DataError.Remote.NO_INTERNET -> "No internet connection. \nCheck your network."
    DataError.Remote.SERVER -> "Server error. \nThe service is not responding correctly."
    DataError.Remote.SERIALIZATION -> "Invalid response format. \nFailed to process server data."
    DataError.Remote.UNKNOWN -> "Something went wrong. \nPlease try again later."
    else -> "Something went wrong. \nPlease try again later."
}