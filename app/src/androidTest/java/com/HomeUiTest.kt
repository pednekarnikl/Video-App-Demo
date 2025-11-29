package com

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.home.domain.model.VideoModel
import com.video.videoappdemo.home.domain.usecase.GetVideoUseCase
import com.video.videoappdemo.home.presentation.HomeScreen
import com.video.videoappdemo.home.presentation.UiVideoListItem
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockGetVideoUseCase = mockk<GetVideoUseCase>()
    private val mockFunction = mockk<(UiVideoListItem) -> Unit>(relaxed = true)

    // Use VideoModel instead of VideoItem if that's what your use case returns
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

    private val testUiVideoItems = testVideoModels.map { videoModel ->
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
        stopKoin()
        startKoin {
            modules(
                module {
                    single { mockGetVideoUseCase }
                }
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun homeScreen_whenLoading_shouldShowLoader() {
        // Arrange - Mock empty state (loading)
        coEvery { mockGetVideoUseCase.execute() } returns Result.Success(emptyList())

        // Act
        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Assert - Should show loader when videos are empty (initial state)
        composeTestRule.onNodeWithTag("circular_loader").assertExists()
        composeTestRule.onNodeWithTag("video_list").assertDoesNotExist()
    }

    @Test
    fun homeScreen_whenVideosLoaded_shouldDisplayVideoList() {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Success(testVideoModels)

        // Act
        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Assert - Wait for videos to load and verify list is displayed
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("video_list").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("video_list").assertIsDisplayed()
        composeTestRule.onNodeWithTag("circular_loader").assertDoesNotExist()

        // Verify video items are displayed
        composeTestRule.onNodeWithText("Test Video 1").assertExists()
        composeTestRule.onNodeWithText("Test Video 2").assertExists()
        composeTestRule.onNodeWithText("Author 1").assertExists()
        composeTestRule.onNodeWithText("10:30").assertExists()
    }

    @Test
    fun homeScreen_whenNetworkError_shouldDisplayErrorMessage() {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns
                Result.Error(DataError.Remote.NO_INTERNET)

        // Act
        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Assert - Should show network error message
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("No internet connection.").fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithText("No internet connection.").assertIsDisplayed()
        composeTestRule.onNodeWithText("Check your network.").assertExists()
        composeTestRule.onNodeWithTag("video_list").assertDoesNotExist()
        composeTestRule.onNodeWithTag("circular_loader").assertDoesNotExist()
    }

    @Test
    fun homeScreen_whenServerError_shouldDisplayServerErrorMessage() {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns
                Result.Error(DataError.Remote.SERVER)

        // Act
        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Assert - Should show server error message
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Server error.").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Server error.").assertIsDisplayed()
        composeTestRule.onNodeWithText("The service is not responding correctly.").assertExists()
    }

    @Test
    fun homeScreen_whenTimeoutError_shouldDisplayTimeoutErrorMessage() {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns
                Result.Error(DataError.Remote.REQUEST_TIMEOUT)

        // Act
        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Assert - Should show timeout error message
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Request timed out.").fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule.onNodeWithText("Request timed out.").assertIsDisplayed()
        composeTestRule.onNodeWithText("The server took too long to respond.").assertExists()
    }

    @Test
    fun homeScreen_whenVideoItemClicked_shouldInvokeCallback() {
        // Arrange
        coEvery { mockGetVideoUseCase.execute() } returns Result.Success(testVideoModels)

        composeTestRule.setContent {
            HomeScreen(function = mockFunction)
        }

        // Wait for videos to load
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Test Video 1").fetchSemanticsNodes().isNotEmpty()
        }

        // Act - Click on first video item
        composeTestRule.onNodeWithText("Test Video 1").performClick()

        // Assert - Verify callback is invoked with correct data
        verify { mockFunction.invoke(testUiVideoItems[0]) }
    }
}

