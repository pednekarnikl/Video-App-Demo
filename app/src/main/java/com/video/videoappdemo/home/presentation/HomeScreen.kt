package com.video.videoappdemo.home.presentation

import CircularLoader
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.video.videoappdemo.home.presentation.components.AutoCarousel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(function: (UiVideoListItem) -> Unit) {

    val coinsListViewModel = koinViewModel<VideoListViewModel>()
    /**
     * ```
     * collectAsStateWithLifecycle - is lifecycle aware Flow - emission is not wasted on background
     * - emission restarts when app return from background
     * ```
     * */
    val state by coinsListViewModel.state.collectAsStateWithLifecycle()

    when {
        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text((state.error) ?: "")
            }
        }

        state.videos.isEmpty() -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularLoader()
            }
        }

        else -> LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize(),
        ) {

            items(state.videos) {
                UIVideoItem(
                    it,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ) {
                        function(it)
                    }
                )
            }
        }
    }
}