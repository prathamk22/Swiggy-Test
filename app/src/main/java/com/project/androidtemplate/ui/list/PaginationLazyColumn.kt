package com.project.androidtemplate.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.androidtemplate.ui.list.models.MovieResult
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun PaginatedLazyColumn(
    items: List<MovieResult>,  // Using PersistentList for efficient state management
    loadMoreItems: () -> Unit,  // Function to load more items
    listState: LazyListState,  // Track the scroll state of the LazyColumn
    buffer: Int = 2,  // Buffer to load more items when we get near the end
    isLoading: Boolean,  // Track if items are being loaded
    modifier: Modifier = Modifier,
    onItemClick: (movieId: String) -> Unit
) {
    // Derived state to determine when to load more items
    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            // Get the index of the last visible item
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            // Check if we have scrolled near the end of the list and more items should be loaded
            lastVisibleItemIndex >= (totalItemsCount - buffer) && !isLoading
        }
    }

// Launch a coroutine to load more items when shouldLoadMore becomes true
    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                loadMoreItems()
            }
    }
    // LazyColumn to display the list of items
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),  // Add padding for better visual spacing
        state = listState  // Pass the scroll state
    ) {
        // Render each item in the list using a unique key
        itemsIndexed(items) { _, result ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    )
                    .clickable {
                        onItemClick.invoke(result.movieId)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(model = result.poster, contentDescription = null)
                Text(text = result.title)
            }
        }

//            // Check if we've reached the end of the list
//            if (index == items.lastIndex && !isLoading) {
//                loadMoreItems()
//            }

        // Show a loading indicator at the bottom when items are being loaded
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()  // Display a circular loading indicator
                }
            }
        }
    }
}
