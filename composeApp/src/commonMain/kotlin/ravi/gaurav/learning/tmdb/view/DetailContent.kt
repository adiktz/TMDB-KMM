package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ravi.gaurav.learning.tmdb.domain.Movie
import ravi.gaurav.learning.tmdb.navigation.DetailComponent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.OS
import ravi.gaurav.learning.tmdb.util.RatingBar
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.dateString
import ravi.gaurav.learning.tmdb.util.getScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.getSystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.safeHeaderPadding

@Preview
@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier
) {

    val insetsHelper: SystemInsetsHelper = koinInject()
    val movie = remember { component.movie }

    val screenHelper: ScreenDimensionsHelper = koinInject()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (insetsHelper.os == OS.ANDROID && !insetsHelper.isPortraitMode()) {
                        Modifier.navigationBarsPadding()
                    } else {
                        Modifier
                    }
                )
                .verticalScroll(rememberScrollState())
        ) {

            KamelImage(
                resource = asyncPainterResource(Constants.imageBaseUrl + movie.backdropPath),
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
                    .then(
                        if (insetsHelper.isPortraitMode()) {
                            Modifier.height((screenHelper.getScreenHeight() / 3).dp)
                        } else {
                            Modifier.height(screenHelper.getScreenHeight().dp)
                        }
                    ),
                contentScale = ContentScale.Crop,
            )

            SubHeader(
                modifier = Modifier.offset(y = (-50).dp),
                movie = movie
            )
        }

        DetailHeader(
            modifier = Modifier.safeHeaderPadding()
        ) {
            component.goBack()
        }

    }
}

@Composable
fun DetailHeader(
    modifier: Modifier = Modifier,
    goBack: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        FilledTonalIconButton(
            onClick = { goBack() },
            modifier = Modifier
                .alpha(0.7f)

        ) {
            Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
        }
    }
}

@Composable
fun SubHeader(modifier: Modifier = Modifier, movie: Movie) {
    val yearOfRelease = remember { LocalDate.parse(movie.releaseDate).year }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                Brush.verticalGradient(
                    listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                    startY = 5f,
                    tileMode = TileMode.Decal
                )
            ),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 10.dp)
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + movie.posterPath),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(150.dp)
                    .defaultMinSize(minHeight = 200.dp)
                    .border(4.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(15.dp))
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(15.dp), clip = true)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineMedium,
                    softWrap = true,
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = "• $yearOfRelease",
                    modifier = Modifier.padding(10.dp)
                )

                RatingBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp),
                    rating = movie.voteAverage / 2,
                    stars = 5,
                    starsColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}