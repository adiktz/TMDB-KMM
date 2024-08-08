package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import ravi.gaurav.learning.tmdb.navigation.DetailComponent
import ravi.gaurav.learning.tmdb.util.Constants
import ravi.gaurav.learning.tmdb.util.OS
import ravi.gaurav.learning.tmdb.util.ScreenDimensionsHelper
import ravi.gaurav.learning.tmdb.util.SystemInsetsHelper
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