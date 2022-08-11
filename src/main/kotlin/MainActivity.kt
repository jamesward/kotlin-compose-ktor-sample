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
import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {

    private val gitHubApi = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(GitHubApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveReleases(gitHubApi)
        }
    }
}

data class Release(@field:Json(name = "name") val name: String)

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

interface GitHubApi {
    @GET("/repos/jetbrains/kotlin/tags")
    suspend fun tags(): List<Release>
}

@Composable
fun LiveReleases(gitHubApi: GitHubApi) {
    val releases = remember { mutableStateListOf<Release>() }

    LaunchedEffect(releases) {
        releases.clear()
        releases.addAll(gitHubApi.tags())
    }

    Releases(releases)
}