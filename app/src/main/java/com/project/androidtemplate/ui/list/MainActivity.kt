package com.project.androidtemplate.ui.list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.androidtemplate.ui.detail.DetailActivity
import com.project.androidtemplate.ui.list.models.MovieListState
import com.project.androidtemplate.ui.theme.AndroidTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val listViewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidTemplateTheme {
                val state by listViewModel.uiState.collectAsState()
                var movieSearch: String by remember {
                    mutableStateOf("")
                }
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val lazyListState = rememberLazyListState()
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = movieSearch,
                        onValueChange = {
                            movieSearch = it
                            listViewModel.updateSearchKey(it)
                        })
                    when (state) {
                        is MovieListState.Error -> {

                        }

                        MovieListState.Idle -> {

                        }

                        MovieListState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MovieListState.MovieList -> {
                            val listState = (state as MovieListState.MovieList)
                            PaginatedLazyColumn(
                                items = listState.list,
                                loadMoreItems = {
                                    Log.e("TAG", "onCreate: load more is called")
                                    listViewModel.loadMore()
                                },
                                listState = lazyListState,
                                isLoading = listState.isLoadingMore,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                openDetailsActivity(it)
                            }
                        }

                        is MovieListState.NoResultFound -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = (state as MovieListState.NoResultFound).message)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openDetailsActivity(movieId: String) {
        startActivity(DetailActivity.getInstance(this, movieId))
    }
}
