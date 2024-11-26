package com.project.androidtemplate.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.androidtemplate.ui.detail.model.MovieDetailState
import com.project.androidtemplate.ui.theme.AndroidTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : ComponentActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTemplateTheme {
                val state by detailViewModel.uiState.collectAsState()
                LaunchedEffect(key1 = Unit) {
                    detailViewModel.loadMovieDetails(intent.getStringExtra(MOVIE_ID))
                }
                when (state) {
                    is MovieDetailState.Details -> {
                        val movieUiDetail = (state as MovieDetailState.Details).detail
                        Column(
                            modifier = Modifier.fillMaxSize(),

                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(model = movieUiDetail.poster, contentDescription = null)
                            val modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
                            Text(modifier = modifier, text = "Title: ${movieUiDetail.movieTitle}")
                            movieUiDetail.director?.let {
                                Text(modifier = modifier, text = "Director: $it")
                            }
                            movieUiDetail.released?.let {
                                Text(modifier = modifier, text = "Released: $it")
                            }
                            movieUiDetail.year?.let {
                                Text(modifier = modifier, text = "Year: $it")
                            }
                            movieUiDetail.writer?.let {
                                Text(modifier = modifier, text = "Writer: $it")
                            }
                            movieUiDetail.genre?.let {
                                Text(modifier = modifier, text = "Genre: $it")
                            }
                        }
                    }

                    is MovieDetailState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = (state as MovieDetailState.Error).message)

                        }
                    }

                    MovieDetailState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val MOVIE_ID = "movieId"
        fun getInstance(context: Context, movieId: String): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            }
        }
    }

}