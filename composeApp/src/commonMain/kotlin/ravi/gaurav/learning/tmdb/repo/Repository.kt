package ravi.gaurav.learning.tmdb.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class Repository {

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            getResult()
                .collect { st ->
                    println(st)
                }
        }
    }

    private suspend fun getResult(): Flow<String> {
        return flow {
            repeat(10) {
                emit("Test")
            }
        }
    }
}