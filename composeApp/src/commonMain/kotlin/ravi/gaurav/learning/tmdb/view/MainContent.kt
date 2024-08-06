package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.essenty.lifecycle.coroutines.repeatOnLifecycle
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.ui.tooling.preview.Preview
import ravi.gaurav.learning.tmdb.domain.DEFAULT
import ravi.gaurav.learning.tmdb.domain.Movie
import ravi.gaurav.learning.tmdb.navigation.MainComponent
import ravi.gaurav.learning.tmdb.navigation.MainEvent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.RatingBar


@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier
) {

    val movies by component.movies.collectAsState()

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    ObserveErrorWhenStarted(
        component = component,
        flow = component.channel
    ) {
        println("Error ==> $it")
        component.onEvent(MainEvent.Update(it))
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(200.dp),
            state = lazyStaggeredGridState,
            contentPadding = PaddingValues(5.dp),
            verticalItemSpacing = 5.dp,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            itemsIndexed(
                movies
            ) { index: Int, movie: Movie ->

                MovieItem(movie = movie) { component.onEvent(MainEvent.ShowDetails(movie)) }

                // Trigger next page load when the user scrolls near the end
                if (index >= movies.size - 4) {
                    LaunchedEffect(index) {
                        component.loadMorePopularMovies()
                    }
                }
            }

        }
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
    movie: Movie = Movie.DEFAULT,
    onClick: () -> Unit = {}
) {

    Card(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            KamelImage(
                resource = asyncPainterResource(Constants.posterBaseUrl + movie.posterPath),
                contentDescription = null,
                modifier = modifier.fillMaxWidth(),
                //    .height(270.dp),
                 //   .heightIn(260.dp, 300.dp),
                contentScale = ContentScale.FillWidth,
            )
            Text(text = movie.title,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                maxLines = 1,
                modifier = modifier.padding(10.dp)
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis)
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