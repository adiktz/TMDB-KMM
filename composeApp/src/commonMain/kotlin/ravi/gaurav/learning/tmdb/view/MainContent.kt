package ravi.gaurav.learning.tmdb.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
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
    val text by component.text.collectAsState()

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
        OutlinedTextField(
            value = text,
            onValueChange = { component.onEvent(MainEvent.Update(it)) },
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp)
        )

        Button(onClick = {
            component.onEvent(MainEvent.ShowDetails)
        }) {
            Text("Go to next Screen".uppercase())
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