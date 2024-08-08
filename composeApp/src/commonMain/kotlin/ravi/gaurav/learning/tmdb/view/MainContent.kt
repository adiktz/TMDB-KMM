package ravi.gaurav.learning.tmdb.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.essenty.lifecycle.coroutines.repeatOnLifecycle
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ravi.gaurav.learning.tmdb.domain.DEFAULT
import ravi.gaurav.learning.tmdb.domain.Movie
import ravi.gaurav.learning.tmdb.navigation.MainComponent
import ravi.gaurav.learning.tmdb.navigation.MainEvent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.OS
import ravi.gaurav.learning.tmdb.util.RatingBar
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
import ravi.gaurav.learning.tmdb.util.safeHeaderPadding


@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier
) {

    val movies by component.movies.collectAsState()
    val isLoading by component.isLoading.collectAsState()
    val insetsHelper: SystemInsetsHelper = koinInject()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    ObserveErrorWhenStarted(
        component = component,
        flow = component.channel
    ) {
        println("Error ==> $it")
        component.onEvent(MainEvent.Update(it))
    }

    /*LaunchedEffect(movies) {
        if (movies.isEmpty()) {
            component.loadMorePopularMovies()
        }
    }*/

    val staggeredGridCell = if (insetsHelper.isPortraitMode()
        && (insetsHelper.os == OS.ANDROID || insetsHelper.os == OS.IOS)
    ) {
        StaggeredGridCells.Fixed(2)
    } else {
        StaggeredGridCells.Adaptive(200.dp)
    }

    Column(
        modifier = modifier.fillMaxSize()
            .then(
                if (!insetsHelper.isPortraitMode() && insetsHelper.os == OS.ANDROID) {
                    Modifier.navigationBarsPadding()
                } else {
                    Modifier
                }
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedVisibility(movies.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        AnimatedVisibility(movies.isNotEmpty()) {
            LazyVerticalStaggeredGrid(
                modifier = modifier,
                columns = staggeredGridCell,
                state = lazyStaggeredGridState,
                contentPadding = PaddingValues(5.dp),
                verticalItemSpacing = 5.dp,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                item(
                    span = StaggeredGridItemSpan.FullLine
                ) {
                    Header(
                        "Popular Movies",
                        modifier = Modifier
                            .fillMaxWidth()
                            .safeHeaderPadding()
                    )
                }

                itemsIndexed(
                    movies
                ) { index: Int, movie: Movie ->

                    MovieItem(movie = movie) { component.onEvent(MainEvent.ShowDetails(movie.id)) }

                    // Trigger next page load when the user scrolls near the end
                    if (index >= movies.size - 1) {
                        LaunchedEffect(index) {
                            component.loadMorePopularMovies()
                        }
                    }
                }

                if (isLoading) {
                    item(
                        span = StaggeredGridItemSpan.FullLine
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(20.dp)
                                .navigationBarsPadding(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                item {
                    Spacer(
                        Modifier.windowInsetsBottomHeight(
                            WindowInsets.systemBars
                        )
                    )
                }

            }
        }
    }
}

@Composable
private fun Header(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Left
        )
    }
}

@Composable
fun ObserveErrorWhenStarted(
    component: MainComponent,
    flow: Flow<String>,
    action: (String) -> Unit
) {
    LaunchedEffect(flow) {
        component.lifecycle.repeatOnLifecycle {
            flow.collect {
                action(it)
            }
        }
    }
}

@Preview
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit = {}
) {

    Card(
        onClick = { onClick() },
        modifier = modifier.wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + movie.posterPath),
                contentDescription = null,
                modifier = modifier.fillMaxWidth()
                    .defaultMinSize(minHeight = 200.dp),
                contentScale = ContentScale.FillWidth,
            )
            Text(
                text = movie.title,
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
                rating = movie.voteAverage / 2,
                stars = 5,
                starsColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}