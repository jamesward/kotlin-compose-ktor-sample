package com.jamesward.kotlincomposektorsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveReleases(client)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.close()
    }
}

@Serializable
data class Release(val name: String)

@Composable
fun Releases(releases: List<Release>) {
    Column(Modifier.fillMaxWidth().fillMaxHeight().padding(10.dp), Arrangement.Top) {
        Text("Kotlin Releases:")

        LazyColumn {
            items(releases) {
                Text(it.name, modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Preview
@Composable
fun PreviewReleases() {
    val releases = (0 .. 42).map {
        Release("1.0.$it")
    }
    Releases(releases)
}

@Composable
fun LiveReleases(client: HttpClient) {
    val releases = remember { mutableStateListOf<Release>() }

    LaunchedEffect(releases) {
        releases.clear()
        releases.addAll(
            client.get("https://api.github.com/repos/jetbrains/kotlin/tags").body()
        )
    }

    Releases(releases)
}