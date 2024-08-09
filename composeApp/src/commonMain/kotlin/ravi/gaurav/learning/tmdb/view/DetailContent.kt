package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
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
import kotlin.math.absoluteValue

@Preview
@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier
) {

    val insetsHelper: SystemInsetsHelper = koinInject()
    val details by component.details.collectAsState()

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

                Column(
                    modifier = Modifier.offset(y = (-50).dp)
                        .safeCutOutPadding()
                ) {

                    SubHeader(
                        modifier = Modifier,
                        details = details
                    )

                    details.overview?.let {

                        SectionSeparater()

                        Text(
                            text = "Overview".uppercase(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        Text(
                            text = it,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    }

                    details.images?.backdrops?.takeIf { it.isNotEmpty() }?.let { backdrops ->
                        val aspectRatio = backdrops.filter { it.aspectRatio != null }
                            .maxOfOrNull { it.aspectRatio!! } ?: 1.77

                        SectionSeparater()

                        Text(
                            text = "Gallery".uppercase(),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        BackdropPager(
                            backdrops = backdrops.filter { it.filePath != null }
                                .map { it.filePath!! },
                            aspectRatio = aspectRatio
                        )
                    }

                    details.credits?.cast?.takeIf { it.isNotEmpty() }
                        ?.filter { it.profilePath != null }?.let { casts ->

                            SectionSeparater()

                            Column {
                                Text(
                                    text = "Cast".uppercase(),
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                )

                                LazyHorizontalGrid(
                                    modifier = Modifier.height(140.dp),
                                    rows = GridCells.Adaptive(140.dp), //if (casts.size > 8) GridCells.Fixed(2) else GridCells.Fixed(1),
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

                    details.recommendations?.results?.takeIf { it.isNotEmpty() }?.let { movies ->

                        SectionSeparater()

                        Recommendation("Recommendations".uppercase(), movies) { movie ->
                            component.onMovieSelected(movie)
                        }
                    }

                    details.similar?.results?.takeIf { it.isNotEmpty() }?.let { movies ->

                        SectionSeparater()

                        Recommendation("Similar Movies".uppercase(), movies) { movie ->
                            component.onMovieSelected(movie)
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
        modifier = Modifier.wrapContentHeight()
    ) {
        Column(
            modifier = modifier
                .wrapContentHeight()
                .width(IntrinsicSize.Min)
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + movie.posterPath),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = movie.title ?: "",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = Modifier.padding(10.dp)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis
            )
            RatingBar(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 10.dp),
                rating = movie.voteAverage?.div(2) ?: 0.0,
                stars = 5,
                starsColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Composable
private fun Recommendation(
    headerTitle: String,
    movies: List<RecommendationsResult>,
    onClick: (RecommendationsResult) -> Unit = {}
) {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = headerTitle,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp),
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            modifier = Modifier.height(280.dp),
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(movies) { similar ->
                SimilarMovieItem(movie = similar) {
                    onClick(similar)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BackdropPager(
    backdrops: List<String>,
    aspectRatio: Double = 1.77,
    modifier: Modifier = Modifier
) {
    val insetsHelper: SystemInsetsHelper = koinInject()
    val screenHelper: ScreenDimensionsHelper = koinInject()

    val pagerState = rememberPagerState(pageCount = {
        backdrops.size
    })

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(pagerState.pageCount)
    )


    val defaultPadding = if (insetsHelper.isPortraitMode()) {
        20
    } else {
        40
    }

    val defaultPagePadding = if (insetsHelper.isPortraitMode()) {
        5
    } else {
        10
    }

    val cardWidth = if (insetsHelper.os == OS.ANDROID || insetsHelper.os == OS.IOS) {
        (screenHelper.getScreenWidth() - defaultPadding - defaultPagePadding)
    } else {
        ((screenHelper.getScreenWidth() - defaultPadding - defaultPagePadding) / 2)
    }

    val cardHeight = minOf(((cardWidth) * (1 / aspectRatio)).dp, screenHelper.getScreenHeight().dp)

    val pageSize = if (insetsHelper.os == OS.ANDROID || insetsHelper.os == OS.IOS) {
        PageSize.Fill
    } else {
        PageSize.Fixed(cardWidth.dp - defaultPadding.dp - defaultPagePadding.dp)
    }

    val contentPadding = PaddingValues(horizontal = defaultPadding.dp)

    HorizontalPager(
        state = pagerState,
        contentPadding = contentPadding,
        flingBehavior = fling,
        pageSize = pageSize,
        modifier = modifier.height(cardHeight)
    ) { page ->
        val pageOffset = (
                (pagerState.currentPage - page) + pagerState
                    .currentPageOffsetFraction
                ).absoluteValue

        val animatedCardHeight = if (insetsHelper.os == OS.ANDROID || insetsHelper.os == OS.IOS) {
            lerp(cardHeight, cardHeight * 0.9f, pageOffset)
        } else {
            cardHeight
        }

        Card(
            modifier = Modifier
                .width(cardWidth.dp)
                .padding(PaddingValues(horizontal = defaultPagePadding.dp))
                .height(animatedCardHeight)
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.imageBaseUrl + backdrops[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(cardWidth.dp + defaultPagePadding.dp)
                    .height(cardHeight)
                    .then(
                        if (insetsHelper.os == OS.ANDROID || insetsHelper.os == OS.IOS) {
                            Modifier.graphicsLayer {
                                val scale = lerp(
                                    start = 1f,
                                    stop = 3f,
                                    fraction = pageOffset
                                )
                                scaleX *= scale
                                scaleY *= scale
                            }
                        } else {
                            Modifier
                        }
                    )
            )
        }
    }
}