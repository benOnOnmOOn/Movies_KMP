package com.bz.movies.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import com.bz.movies.presentation.screens.common.MovieItem
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun MovieContent(movieItem: MovieItem, isLast: Boolean, onMovieClicked: (MovieItem) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(4.dp)
            .clickable { onMovieClicked(movieItem) }
    ) {
        AsyncImage(
            modifier =
            Modifier
                .height(80.dp)
                .width(80.dp)
                .padding(4.dp),
            model = movieItem.posterUrl,
            contentDescription = movieItem.title,
            imageLoader = ImageLoader(LocalPlatformContext.current)
        )
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .weight(1f)
        ) {
            Text(text = movieItem.title)
            Text(text = movieItem.releaseDate)
            Text(text = "${movieItem.rating}/100")
        }

        Image(
            painterResource(Res.drawable.ic_star),
            modifier =
            Modifier
                .height(40.dp)
                .width(40.dp)
                .padding(4.dp),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (!isLast) {
        HorizontalDivider(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
    }
}
