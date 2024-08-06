package ravi.gaurav.learning.tmdb

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.retainedComponent
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import ravi.gaurav.learning.tmdb.domain.DEFAULT
import ravi.gaurav.learning.tmdb.domain.Movie
import ravi.gaurav.learning.tmdb.navigation.RootComponent
import ravi.gaurav.learning.tmdb.util.Constants
import tmdb.composeapp.generated.resources.Res
import tmdb.composeapp.generated.resources.compose_multiplatform

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = retainedComponent {
            RootComponent(it)
        }
        setContent {
            App(root)
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    //  App()
}
