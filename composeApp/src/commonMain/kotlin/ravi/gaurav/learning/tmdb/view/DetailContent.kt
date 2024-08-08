package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ravi.gaurav.learning.tmdb.domain.MovieDetails
import ravi.gaurav.learning.tmdb.domain.RecommendationsResult
import ravi.gaurav.learning.tmdb.navigation.DetailComponent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.OS
import ravi.gaurav.learning.tmdb.util.RatingBar
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.safeCutOutPadding
import ravi.gaurav.learning.tmdb.util.safeHeaderPadding

@Preview
@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier
) {

    val insetsHelper: SystemInsetsHelper = koinInject()
    val movieId = remember { component.movieId }
    val details by component.details.collectAsState()

    val screenHelper: ScreenDimensionsHelper = koinInject()

    LaunchedEffect(Unit) {
        component.getMovieDetails(movieId)
    }

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

            details?.let { details ->
                KamelImage(
                    resource = asyncPainterResource(Constants.imageBaseUrl + details.backdropPath),
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
            }

            details?.let { details ->
                Column(
                    modifier = Modifier.offset(y = (-50).dp)
                        .safeCutOutPadding()
                ) {

                    SubHeader(
                        modifier = Modifier,
                        details = details
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    details.overview?.let {
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(10.dp),
                        )
                        Text(
                            text = it,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }

                    details.credits?.cast?.takeIf { it.isNotEmpty() }
                        ?.filter { it.profilePath != null }?.let { casts ->
                            Spacer(modifier = Modifier.height(20.dp))
                            Column(
                                modifier = Modifier.padding(top = 10.dp)
                            ) {

                                Text(
                                    text = "Cast",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(10.dp),
                                )

                                LazyHorizontalGrid(
                                    modifier = Modifier.height(
                                        if (casts.size > 8) 300.dp else 150.dp
                                    ),
                                    rows = if (casts.size > 8) GridCells.Fixed(2) else GridCells.Fixed(
                                        1
                                    ),
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(casts.filter { it.profilePath != null }) { cast ->
                                        Column(
                                            modifier = Modifier.width(120.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            KamelImage(
                                                resource = asyncPainterResource(Constants.posterBaseUrl + cast.profilePath),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier.width(100.dp).height(100.dp)
                                                    .clip(CircleShape)
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                            Text(
                                                text = cast.name ?: "No Name",
                                                style = MaterialTheme.typography.titleSmall,
                                                maxLines = 1,
                                                textAlign = TextAlign.Center,
                                                overflow = TextOverflow.Ellipsis,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    details.similar?.results?.takeIf { it.isNotEmpty() }?.let {
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.padding(top = 10.dp)
                        ) {
                            Text(
                                text = "Similar Movies",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp),
                            )

                            LazyRow(
                                modifier = Modifier.height(300.dp),
                                contentPadding = PaddingValues(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {

                                items(it) { similar ->
                                    SimilarMovieItem(movie = similar) {
                                        component.onMovieSelected(similar)
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    DetailHeader(
        modifier = Modifier.safeHeaderPadding()
    ) {
        component.goBack()
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
fun SubHeader(modifier: Modifier = Modifier, details: MovieDetails) {
    val yearOfRelease: String = remember {
        details.releaseDate?.let {
            LocalDate.parse(it).year.toString()
        } ?: "----"
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + details.posterPath),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(150.dp)
                    .defaultMinSize(minHeight = 200.dp)
                    .border(
                        4.dp,
                        MaterialTheme.colorScheme.onBackground,
                        RoundedCornerShape(15.dp)
                    )
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(15.dp), clip = true)
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = details.title ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    softWrap = true,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                details.tagline?.takeIf { it.isNotEmpty() }?.let {
                    Text(
                        text = "• $it",
                        modifier = Modifier.padding(horizontal = 10.dp),
                    )
                }

                Text(
                    text = "• $yearOfRelease",
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

                RatingBar(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .padding(bottom = 10.dp),
                    rating = details.voteAverage?.div(2) ?: 0.0,
                    stars = 5,
                    starsColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun SimilarMovieItem(
    modifier: Modifier = Modifier,
    movie: RecommendationsResult,
    onClick: () -> Unit = {}
) {

    Card(
        onClick = { onClick() },
        modifier = modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .width(IntrinsicSize.Min)
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + movie.posterPath),
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = movie.title ?: "",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = modifier.padding(10.dp)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            RatingBar(
                modifier = modifier.fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp),
                rating = movie.voteAverage?.div(2) ?: 0.0,
                stars = 5,
                starsColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}