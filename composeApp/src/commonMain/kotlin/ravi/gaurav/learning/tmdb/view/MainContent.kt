package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.coroutines.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import ravi.gaurav.learning.tmdb.navigation.MainComponent
import ravi.gaurav.learning.tmdb.navigation.MainEvent


@Composable
fun MainContent(
    component: MainComponent,
    modifier: Modifier = Modifier
) {

    val movies by component.movies.collectAsState()

    ObserveErrorWhenStarted(
        component = component,
        flow = component.channel
    ) {
        println("Error ==> $it")
        component.onEvent(MainEvent.Update(it))
    }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp)
        ) {
            item(
                span = {
                    GridItemSpan(maxLineSpan)
                }
            ) {
                Text(
                    "Popular Movies",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = modifier.fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Center
                )
            }
            items(movies) { movie ->
                Text(
                    movie.title,
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp)
                        .padding(10.dp)
                        .clickable { component.onEvent(MainEvent.ShowDetails(movie)) }
                )

            }
        }
    }
}

@Composable
fun ObserveErrorWhenStarted(
    component: MainComponent,
    flow: Flow<String>,
    action:(String) -> Unit
) {
    LaunchedEffect(flow) {
        component.lifecycle.repeatOnLifecycle {
            flow.collect {
                action(it)
            }
        }
    }
}