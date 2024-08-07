package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ravi.gaurav.learning.tmdb.navigation.DetailComponent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.UiDesignDecisionHelper
import ravi.gaurav.learning.tmdb.util.safeHeaderPadding

@Preview
@Composable
fun DetailContent(
    component: DetailComponent,
    modifier: Modifier = Modifier
) {

    val uiDesignDecision: UiDesignDecisionHelper = koinInject()
    val movie = remember { component.movie }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(
                if (uiDesignDecision.shouldAddNavigationBarPadding()) {
                    Modifier.navigationBarsPadding()
                } else {
                    Modifier
                }
            )
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            KamelImage(
                resource = asyncPainterResource(Constants.imageBaseUrl + movie.backdropPath),
                contentDescription = null,
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .safeHeaderPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { component.goBack() }) {
                    Icon(imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null)
                }
                Text("Title: ${movie.title}")
            }
        }
    }
}