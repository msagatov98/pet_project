package com.example.myapplication.feature.onboarding.presentation.screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.core.android.ui.presentation.component.ScreenPreview
import com.example.myapplication.core.android.ui.presentation.component.AppThemePreview
import com.example.myapplication.core.android.ui.presentation.component.HandleEffect
import com.example.myapplication.core.android.ui.presentation.component.Spacer
import com.example.myapplication.core.android.ui.presentation.component.isLast
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import com.example.myapplication.feature.onboarding.presentation.navigation.OnBoardingNavigator
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.onBoardingScreen(
    navController: NavController,
) {
    composable<Screen.OnBoarding> {
        val viewModel: OnBoardingViewModel = koinViewModel()
        val navigator = remember { OnBoardingNavigator(navController) }
        val state by viewModel.uiState.collectAsStateWithLifecycle()

        HandleEffect(viewModel.effect) { effect ->
            when (effect) {
                Effect.NavigateToHome -> navigator.navigateToHome()
            }
        }

        OnBoardingScreen(
            state = state,
            onEvent = viewModel::action,
        )
    }
}


@Composable
private fun OnBoardingScreen(
    state: UiState,
    onEvent: (Action) -> Unit,
) {
    val pagerState = rememberPagerState { state.data.size }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 24.dp + 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = state.data[it].imageResId),
                    contentDescription = null,
                )
                Spacer(size = 12.dp)
                Text(
                    text = state.data[it].title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(size = 12.dp)
                Text(
                    textAlign = TextAlign.Center,
                    text = state.data[it].description,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
        ) {
            TextButton(onClick = { onEvent(Action.SkipOnBoarding) }) {
                Text(text = "Skip")
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color by animateColorAsState(
                        label = "",
                        targetValue = if (pagerState.currentPage == iteration) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onPrimary
                        }
                    )
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }

            TextButton(
                onClick = {
                    if (pagerState.isLast) {
                        onEvent(Action.SkipOnBoarding)
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            ) {
                Text(text = if (pagerState.isLast) "Start" else "Next")
            }
        }
    }
}

@ScreenPreview
@Composable
private fun OnBoardingScreenPreview() {
    AppThemePreview {
        OnBoardingScreen(
            state = UiState(),
            onEvent = { },
        )
    }
}