package com.example.myapplication.core.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import kotlinx.coroutines.launch

@Composable
fun BottomBar(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()
    val items = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Profile
        )
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            val selected = pagerState.currentPage == index
            NavigationBarItem(
                selected = selected,
                label = { Text(text = stringResource(item.labelRes)) },
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                icon = {
                    Icon(
                        contentDescription = null,
                        imageVector = if (selected) {
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        }
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surfaceContainer,
                )
            )
        }
    }
}

@Composable
@ComponentPreview
private fun BottomBarPreview() {
    Column {
        AppThemePreview {
            BottomBar(pagerState = rememberPagerState { 1 })
        }
        Spacer(size = 24.dp)
        AppThemePreview(isDarkTheme = true) {
            BottomBar(pagerState = rememberPagerState { 1 })
        }
    }

}

private sealed class BottomNavItem(
    @StringRes
    val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Home : BottomNavItem(
        labelRes = R.string.bottom_bar_home,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Search : BottomNavItem(
        labelRes = R.string.bottom_bar_search,
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search,
    )

    data object Profile : BottomNavItem(
        labelRes = R.string.bottom_bar_profile,
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    )
}
