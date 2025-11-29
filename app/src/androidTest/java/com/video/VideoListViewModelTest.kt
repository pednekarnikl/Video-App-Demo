package com.video

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.home.domain.model.VideoModel
import com.video.videoappdemo.home.domain.usecase.GetVideoUseCase
import com.video.videoappdemo.home.presentation.UiVideoListItem
import com.video.videoappdemo.home.presentation.VideoListViewModel
import com.video.videoappdemo.home.presentation.VideoState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VideoListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: VideoListViewModel
    private val mockGetVideoUseCase: GetVideoUseCase = mockk()

    private val testVideoModels = listOf(
        VideoModel(
            title = "Test Video 1",
            description = "Description 1",
            thumbnailUrl = "https://example.com/thumb1.jpg",
            videoUrl = "https://example.com/video1.mp4",
            author = "Author 1",
            duration = "10:30",
            uploadTime = "2 hours ago",
            views = "1.5K views"
        ),
        VideoModel(
            title = "Test Video 2",
            description = "Description 2",
            thumbnailUrl = "https://example.com/thumb2.jpg",
            videoUrl = "https://example.com/video2.mp4",
            author = "Author 2",
            duration = "5:15",
            uploadTime = "1 day ago",
            views = "2.3K views"
        )
    )

    private val expectedUiVideoItems = testVideoModels.map { videoModel ->
        UiVideoListItem(
            title = videoModel.title,
            description = videoModel.description,
            thumbnailUrl = videoModel.thumbnailUrl,
            videoUrl = videoModel.videoUrl,
            author = videoModel.author,
            duration = videoModel.duration,
            uploadTime = videoModel.uploadTime,
            views = videoModel.views
        )
    }

    @Before
    fun setUp() {
        viewModel = VideoListViewModel(mockGetVideoUseCase)
    }

    @Test
    fun whenVideosLoadSuccessfully_stateShouldContainVideos() = runTest {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Success(testVideoModels)

        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(emptyList<UiVideoListItem>(), states[0].videos)
        assertEquals(null, states[0].error)
        assertEquals(expectedUiVideoItems, states[1].videos)
        assertEquals(null, states[1].error)

        job.cancel()
    }

    @Test
    fun whenVideosLoadFailsWithNetworkError_stateShouldContainError() = runTest {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Error(DataError.Remote.NO_INTERNET)

        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals(emptyList<UiVideoListItem>(), states[0].videos)
        assertEquals(null, states[0].error)
        assertEquals(emptyList<UiVideoListItem>(), states[1].videos)
        assertEquals("No internet connection. \nCheck your network.", states[1].error)

        job.cancel()
    }

    @Test
    fun whenVideosLoadFailsWithServerError_stateShouldContainServerErrorMessage() = runTest {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Error(DataError.Remote.SERVER)

        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertEquals("Server error. \nThe service is not responding correctly.", states[1].error)

        job.cancel()
    }

    @Test
    fun stateFlowShouldEmitInitialStateImmediately() = runTest {
        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        assertEquals(1, states.size)
        assertEquals(VideoState(), states[0])

        job.cancel()
    }

    @Test
    fun videosShouldBeMappedCorrectlyFromDomainToUiModel() = runTest {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Success(testVideoModels)

        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        val successState = states[1]
        assertEquals(2, successState.videos.size)

        val firstVideo = successState.videos[0]
        assertEquals("Test Video 1", firstVideo.title)
        assertEquals("Author 1", firstVideo.author)
        assertEquals("10:30", firstVideo.duration)
        assertEquals("2 hours ago", firstVideo.uploadTime)
        assertEquals("1.5K views", firstVideo.views)

        job.cancel()
    }

    @Test
    fun whenUseCaseThrowsException_stateShouldContainUnknownError() = runTest {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } throws RuntimeException("Unexpected error")

        val states = mutableListOf<VideoState>()
        val job = launch {
            viewModel.state.collect { states.add(it) }
        }

        advanceUntilIdle()

        // Assert
        assertEquals(2, states.size)
        assertTrue(states[1].error?.contains("Something went wrong") == true)
        assertEquals(emptyList<UiVideoListItem>(), states[1].videos)

        job.cancel()
    }
}

// Test Rule for Coroutines
@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule : org.junit.rules.TestWatcher() {
    private val testDispatcher = kotlinx.coroutines.test.UnconfinedTestDispatcher()

    override fun starting(description: org.junit.runner.Description) {
        kotlinx.coroutines.Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: org.junit.runner.Description) {
        kotlinx.coroutines.Dispatchers.resetMain()
    }
}