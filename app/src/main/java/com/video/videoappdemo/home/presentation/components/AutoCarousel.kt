package com.video.videoappdemo.home.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.video.videoappdemo.home.presentation.UiVideoListItem
import kotlinx.coroutines.delay

@Composable
fun AutoCarousel(
    modifier: Modifier,
    images: List<String>,
    autoScrollDelay: Long = 3000L,
    callback : (String)->Unit

) {
    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(autoScrollDelay)
            val next = (pagerState.currentPage + 1) % images.size
            pagerState.animateScrollToPage(next)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        ) { pg ->



            Card(
                onClick = { callback.invoke(images[pg]) }
            ) {
                AsyncImage(
                    model = images[pg],
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Spacer(modifier = Modifier.height(8.dp))

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(images.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(if (pagerState.currentPage == index) Color.White else Color.Gray)
                )
            }
        }
    }
}
