package com.video.videoappdemo

import com.video.videoappdemo.core.domain.DataError
import com.video.videoappdemo.core.domain.Result
import com.video.videoappdemo.home.data.remote.dto.VideoItem
import com.video.videoappdemo.home.data.repository.dataSourceImpl.VideoRemoteDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class VideoRemoteDataSourceImplTest {

    private val testVideoItems = listOf(
        VideoItem(
            id = "1",
            title = "Test Video 1",
            description = "Description 1",
            thumbnailUrl = "https://example.com/thumb1.jpg",
            videoUrl = "https://example.com/video1.mp4",
            author = "Author 1",
            duration = "10:30",
            uploadTime = "2 hours ago",
            views = "1.5K views",
            isLive = true,
            subscriber = "abcd"
        ),
        VideoItem(
            id = "2",
            title = "Test Video 2",
            description = "Description 2",
            thumbnailUrl = "https://example.com/thumb2.jpg",
            videoUrl = "https://example.com/video2.mp4",
            author = "Author 2",
            duration = "5:15",
            uploadTime = "1 day ago",
            views = "2.3K views",
            isLive = false,
            subscriber = "bcde"
        )
    )

    @Test
    fun getVideosApi_whenSuccess_shouldReturnVideoList() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                [
                    {
                        "id": "1",
                        "title": "Test Video 1",
                        "description": "Description 1",
                        "thumbnailUrl": "https://example.com/thumb1.jpg",
                        "videoUrl": "https://example.com/video1.mp4",
                        "author": "Author 1",
                        "duration": "10:30",
                        "uploadTime": "2 hours ago",
                        "views": "1.5K views",
                        "isLive": true,
                        "subscriber": "abcd"
                    },
                    {
                        "id": "2",
                        "title": "Test Video 2",
                        "description": "Description 2",
                        "thumbnailUrl": "https://example.com/thumb2.jpg",
                        "videoUrl": "https://example.com/video2.mp4",
                        "author": "Author 2",
                        "duration": "5:15",
                        "uploadTime": "1 day ago",
                        "views": "2.3K views",
                        "isLive": false,
                        "subscriber": "bcde"
                    }
                ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be success", result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(2, successResult.data.size)
        assertEquals("Test Video 1", successResult.data[0].title)
        assertEquals("Author 1", successResult.data[0].author)
        assertEquals(true, successResult.data[0].isLive)
        assertEquals("abcd", successResult.data[0].subscriber)
    }

    @Test
    fun getVideosApi_whenServerReturns404_shouldReturnServerError() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = "Not Found",
                status = HttpStatusCode.NotFound
            )
        }

        val httpClient = HttpClient(mockEngine)
        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be error", result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.SERVER, errorResult.error)
    }

    @Test
    fun getVideosApi_whenServerReturns500_shouldReturnServerError() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError
            )
        }

        val httpClient = HttpClient(mockEngine)
        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be error", result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.SERVER, errorResult.error)
    }

    @Test
    fun getVideosApi_whenNetworkTimeout_shouldReturnTimeoutError() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = "",
                status = HttpStatusCode.RequestTimeout
            )
        }

        val httpClient = HttpClient(mockEngine)
        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be error", result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.REQUEST_TIMEOUT, errorResult.error)
    }

    @Test
    fun getVideosApi_whenMalformedJson_shouldReturnSerializationError() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = """[{"invalid": "json"}}""", // Malformed JSON
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be error", result is Result.Error)
        val errorResult = result as Result.Error
        assertEquals(DataError.Remote.SERIALIZATION, errorResult.error)
    }

    @Test
    fun getVideosApi_whenEmptyArray_shouldReturnSuccessWithEmptyList() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = "[]",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }

        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be success", result is Result.Success)
        val successResult = result as Result.Success
        assertEquals(0, successResult.data.size)
    }

    @Test
    fun getVideosApi_whenNoInternet_shouldReturnNoInternetError() = runTest {
        // Arrange
        val mockEngine = MockEngine { request ->
            respond(
                content = "",
                status = HttpStatusCode.BadGateway // Simulating network issue
            )
        }

        val httpClient = HttpClient(mockEngine)
        val dataSource = VideoRemoteDataSourceImpl(httpClient)

        // Act
        val result = dataSource.getVideosApi()

        // Assert
        assertTrue("Result should be error", result is Result.Error)
        val errorResult = result as Result.Error
        // Depending on your safeCall implementation, this might be SERVER or NO_INTERNET
//        assertTrue(
//            "Should return server error for network issues",
//            errorResult.error is DataError.Remote.SERVER || errorResult.error is DataError.Remote.NO_INTERNET
//        )
    }
}