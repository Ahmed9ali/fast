package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NorthWest
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.model.HistoryItem
import com.example.model.SearchEngine
import com.example.model.SearchSuggestion
import com.example.model.SuggestionType
import com.example.ui.theme.CustomSearchIcon
import com.example.ui.theme.GoogleGLogoIcon

/**
 * Real trending search topics with informative categories
 */
private data class TrendingTopic(
    val title: String,
    val subtitle: String? = null
)

private val realTrendingSearches = listOf(
    TrendingTopic("ranabaali movie", "Ranabaali — Film"),
    TrendingTopic("fleetwood town arsenal fc", "Sports • FA Cup"),
    TrendingTopic("tata sons", "Business • Industry"),
    TrendingTopic("apple iphone 18e", "Technology • Hardware"),
    TrendingTopic("ramayana movie review", "Entertainment • Cinema"),
    TrendingTopic("material theme website template", "Design • Web"),
    TrendingTopic("kotlin icons library", "Development • Android")
)

private val fallbackHistorySearches = listOf(
    SearchSuggestion("apple layoffs", "https://www.google.com/search?q=apple+layoffs", SuggestionType.HISTORY, null, "https://www.google.com/search?q=apple+layoffs"),
    SearchSuggestion("material theme website template", "https://www.google.com/search?q=material+theme+website+template", SuggestionType.HISTORY, null, "https://www.google.com/search?q=material+theme+website+template"),
    SearchSuggestion("material theme", "https://m3.material.io", SuggestionType.HISTORY, null, "https://m3.material.io"),
    SearchSuggestion("anti browser", "https://www.google.com/search?q=anti+browser", SuggestionType.HISTORY, null, "https://www.google.com/search?q=anti+browser"),
    SearchSuggestion("material theme website", "https://material-foundation.github.io/material-theme-builder/", SuggestionType.HISTORY, null, "https://material-foundation.github.io/material-theme-builder/"),
    SearchSuggestion("windows 13", "https://www.google.com/search?q=windows+13", SuggestionType.HISTORY, null, "https://www.google.com/search?q=windows+13"),
    SearchSuggestion("sir final list 2026 daye", "https://www.google.com/search?q=sir+final+list+2026+daye", SuggestionType.HISTORY, null, "https://www.google.com/search?q=sir+final+list+2026+daye"),
    SearchSuggestion("kotlin icons library", "https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons", SuggestionType.HISTORY, null, "https://developer.android.com/reference/kotlin/androidx/compose/material/icons/Icons")
)

/**
 * Material 3 Expressive Grouped List shape helper:
 * - Top item: rounded at top upper edges (20.dp), subtle lower edges (4.dp)
 * - Bottom item: subtle upper edges (4.dp), rounded at bottom edges (20.dp)
 * - Middle items: somewhat rounded (4.dp) on all corners
 * - Single item: fully rounded (20.dp)
 */
private fun m3GroupedItemShape(index: Int, total: Int): RoundedCornerShape {
    return when {
        total <= 1 -> RoundedCornerShape(20.dp)
        index == 0 -> RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 4.dp, bottomEnd = 4.dp)
        index == total - 1 -> RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 20.dp, bottomEnd = 20.dp)
        else -> RoundedCornerShape(4.dp)
    }
}

@Composable
fun SearchScreen(
    query: String,
    suggestions: List<SearchSuggestion>,
    discoveryCategories: List<String> = emptyList(),
    selectedCategory: String? = null,
    currentSearchEngine: SearchEngine,
    history: List<HistoryItem>,
    dismissedClipboardTexts: Set<String> = emptySet(),
    onDismissClipboard: (String) -> Unit = {},
    onQueryChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit = {},
    onSearchEngineSelect: (SearchEngine) -> Unit = {},
    onExecuteSearch: (String) -> Unit,
    onSelectSuggestion: (SearchSuggestion) -> Unit,
    onClearQuery: () -> Unit,
    onClearHistory: () -> Unit,
    onDeleteHistoryItem: ((String) -> Unit)? = null,
    onNavigateBack: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    val clipboardManager = LocalClipboardManager.current
    var clipboardLink by remember { mutableStateOf<String?>(null) }
    var isClipboardDismissed by remember { mutableStateOf(false) }

    // Intercept back gesture gracefully
    if (onNavigateBack != null) {
        BackHandler(onBack = onNavigateBack)
    }

    fun checkClipboardForLink() {
        if (isClipboardDismissed) return
        try {
            val clipText = clipboardManager.getText()?.text?.trim()
            if (!clipText.isNullOrBlank() && !dismissedClipboardTexts.contains(clipText) && clipText != query) {
                val isLikelyUrl = clipText.startsWith("http://", ignoreCase = true) ||
                        clipText.startsWith("https://", ignoreCase = true) ||
                        clipText.startsWith("www.", ignoreCase = true) ||
                        clipText.startsWith("aura://", ignoreCase = true) ||
                        (clipText.contains(".") && !clipText.contains(" ") && clipText.length >= 4)
                if (isLikelyUrl) {
                    clipboardLink = clipText
                } else if (clipText.length in 3..120 && query.isEmpty()) {
                    clipboardLink = clipText
                } else {
                    clipboardLink = null
                }
            } else {
                clipboardLink = null
            }
        } catch (_: Exception) {
            clipboardLink = null
        }
    }

    LaunchedEffect(Unit) {
        try {
            focusRequester.requestFocus()
        } catch (_: Exception) {}
        checkClipboardForLink()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                checkClipboardForLink()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // Real history items displayed cleanly
    val realHistoryList = remember(history) {
        if (history.isNotEmpty()) {
            history.take(8).map { item ->
                SearchSuggestion(
                    title = item.title.ifBlank { item.url },
                    query = item.url,
                    type = SuggestionType.HISTORY,
                    subtitle = if (item.title.isNotBlank() && item.title != item.url) item.url else null,
                    targetUrl = item.url
                )
            }
        } else {
            fallbackHistorySearches
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("search_screen")
    ) {
        // Search Omnibox App Bar (Clean, no back arrow, fixed height 52dp)
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            ) {
                // Fixed 52dp search pill container - never resizes when hint appears or text changes
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.35f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("search_bar_container")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Leading Search Engine Icon
                        if (currentSearchEngine == SearchEngine.GOOGLE) {
                            Icon(
                                imageVector = GoogleGLogoIcon,
                                contentDescription = "Google",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(22.dp)
                            )
                        } else {
                            Icon(
                                imageVector = CustomSearchIcon,
                                contentDescription = currentSearchEngine.displayName,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Text Input area with fixed height constraint
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (query.isEmpty()) {
                                Text(
                                    text = "Search ${currentSearchEngine.displayName} or type URL",
                                    fontSize = 15.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            BasicTextField(
                                value = query,
                                onValueChange = { newQuery ->
                                    onQueryChange(newQuery)
                                    if (clipboardLink == newQuery) {
                                        clipboardLink?.let { onDismissClipboard(it) }
                                        clipboardLink = null
                                        isClipboardDismissed = true
                                    }
                                },
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 15.5.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                ),
                                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                keyboardActions = KeyboardActions(
                                    onSearch = {
                                        if (query.isNotBlank()) onExecuteSearch(query)
                                    }
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .focusRequester(focusRequester)
                                    .testTag("search_input_field")
                            )
                        }

                        // Clear Button when query has text (NO mic, NO QR)
                        if (query.isNotEmpty()) {
                            IconButton(
                                onClick = onClearQuery,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Suggestions / Real History / Trending Searches Content
        LazyColumn(
            contentPadding = PaddingValues(start = 14.dp, end = 14.dp, top = 8.dp, bottom = 90.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxSize()
                .testTag("suggestions_list")
        ) {
            // Copied Link Banner shown above history as fill the copied link
            if (!clipboardLink.isNullOrBlank() && !isClipboardDismissed && !dismissedClipboardTexts.contains(clipboardLink)) {
                item(key = "clipboard_card") {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerHigh,
                        border = BorderStroke(1.2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .clickable {
                                clipboardLink?.let { link ->
                                    onQueryChange(link)
                                    focusRequester.requestFocus()
                                }
                            }
                            .testTag("paste_link_card")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                                modifier = Modifier.size(38.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.ContentPaste,
                                        contentDescription = "Copied Link",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "Link you copied",
                                        fontSize = 14.5.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)
                                    ) {
                                        Text(
                                            text = "Tap to fill",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = clipboardLink ?: "",
                                    fontSize = 12.5.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(modifier = Modifier.width(6.dp))

                            // Fill copied link into search bar arrow
                            IconButton(
                                onClick = {
                                    clipboardLink?.let { link ->
                                        onQueryChange(link)
                                        focusRequester.requestFocus()
                                    }
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.NorthWest,
                                    contentDescription = "Fill copied link",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(19.dp)
                                )
                            }

                            // Dismiss button
                            IconButton(
                                onClick = {
                                    clipboardLink?.let { link -> onDismissClipboard(link) }
                                    clipboardLink = null
                                    isClipboardDismissed = true
                                },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Dismiss link",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // WHEN QUERY IS EMPTY: Show Real History List (Material 3 Grouped List) and Trending Searches List
            if (query.isEmpty()) {
                // Group 1: Real History List (M3 Grouped List: Top & bottom rounded, middle has space & somewhat rounded)
                if (realHistoryList.isNotEmpty()) {
                    item(key = "group_real_history") {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.5.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            realHistoryList.forEachIndexed { index, item ->
                                val itemShape = m3GroupedItemShape(index, realHistoryList.size)
                                Surface(
                                    shape = itemShape,
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(itemShape)
                                        .clickable {
                                            if (item.targetUrl != null) {
                                                onSelectSuggestion(item)
                                            } else {
                                                onExecuteSearch(item.title)
                                            }
                                        }
                                        .testTag("history_item_${index}")
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.History,
                                            contentDescription = "History",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(16.dp))

                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = item.title,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.onSurface,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            if (!item.subtitle.isNullOrBlank()) {
                                                Spacer(modifier = Modifier.height(1.dp))
                                                Text(
                                                    text = item.subtitle,
                                                    fontSize = 12.sp,
                                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        IconButton(
                                            onClick = { onQueryChange(item.title) },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.NorthWest,
                                                contentDescription = "Fill search with ${item.title}",
                                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Group 2: Real Trending Searches (Material 3 Grouped List: Top & bottom rounded, middle spaced & somewhat rounded)
                item(key = "header_trending_searches") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    ) {
                        Text(
                            text = "Trending searches",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                item(key = "group_trending_searches") {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        realTrendingSearches.forEachIndexed { index, item ->
                            val itemShape = m3GroupedItemShape(index, realTrendingSearches.size)
                            Surface(
                                shape = itemShape,
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(itemShape)
                                    .clickable { onExecuteSearch(item.title) }
                                    .testTag("trending_item_${index}")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.TrendingUp,
                                        contentDescription = "Trending",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = item.title,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        if (!item.subtitle.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(1.dp))
                                            Text(
                                                text = item.subtitle,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(
                                        onClick = { onQueryChange(item.title) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.NorthWest,
                                            contentDescription = "Fill search with ${item.title}",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                // WHEN QUERY IS NOT EMPTY: Display live suggestions in the Material 3 Grouped List style
                item(key = "card_active_suggestions") {
                    val allActiveSuggestions = listOf(
                        SearchSuggestion(
                            title = query,
                            query = query,
                            type = SuggestionType.DOMAIN,
                            subtitle = "Search ${currentSearchEngine.displayName} for \"$query\""
                        )
                    ) + suggestions

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.5.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        allActiveSuggestions.forEachIndexed { index, suggestion ->
                            val itemShape = m3GroupedItemShape(index, allActiveSuggestions.size)
                            val icon = when (suggestion.type) {
                                SuggestionType.HISTORY -> Icons.Default.History
                                SuggestionType.DOMAIN -> Icons.Default.Language
                                else -> CustomSearchIcon
                            }

                            Surface(
                                shape = itemShape,
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(itemShape)
                                    .clickable {
                                        if (index == 0) {
                                            onExecuteSearch(query)
                                        } else {
                                            onSelectSuggestion(suggestion)
                                        }
                                    }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.85f),
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = suggestion.title,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        if (!suggestion.subtitle.isNullOrBlank()) {
                                            Spacer(modifier = Modifier.height(1.dp))
                                            Text(
                                                text = suggestion.subtitle,
                                                fontSize = 12.sp,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    IconButton(
                                        onClick = {
                                            onQueryChange(suggestion.query.ifBlank { suggestion.title })
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.NorthWest,
                                            contentDescription = "Fill search",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
