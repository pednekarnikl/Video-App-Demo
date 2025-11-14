package com.video.videoappdemo.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun UIVideoItem(
    uiVideoListItem: UiVideoListItem,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            Modifier.background(
                Color.DarkGray.copy(alpha = 0.3f)
            )
        ) {
            AsyncImage(
                model = uiVideoListItem.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(color = Color.Black.copy(.6f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = uiVideoListItem.duration,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    modifier = Modifier

                )
            }


        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = uiVideoListItem.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = uiVideoListItem.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${uiVideoListItem.author} . ${uiVideoListItem.views} views . ${uiVideoListItem.uploadTime}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray.copy(alpha = 0.6f)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
