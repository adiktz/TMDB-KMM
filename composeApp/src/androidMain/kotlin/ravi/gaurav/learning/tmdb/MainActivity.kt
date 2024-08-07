package ravi.gaurav.learning.tmdb

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.retainedComponent
import org.koin.android.ext.android.get
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import ravi.gaurav.learning.tmdb.navigation.RootComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainedComponent { componentContext ->
            loadKoinModules(
                listOf(
                    module { single<ComponentContext> { componentContext } },
                    module { single<RootComponent> { RootComponent(get()) } },
                )
            )
        }

        enableEdgeToEdge()

        setContent {
            App(root = get())
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppAndroidPreview() {
    //  App()
}
