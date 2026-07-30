package au.com.shiftyjelly.pocketcasts.wear.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.wear.tooling.preview.devices.WearDevices
import au.com.shiftyjelly.pocketcasts.compose.CallOnce
import au.com.shiftyjelly.pocketcasts.models.to.RefreshState
import au.com.shiftyjelly.pocketcasts.repositories.playback.UpNextQueue
import au.com.shiftyjelly.pocketcasts.wear.theme.WearAppTheme
import au.com.shiftyjelly.pocketcasts.wear.ui.component.PullToRefresh
import au.com.shiftyjelly.pocketcasts.wear.ui.component.WatchListChip
import au.com.shiftyjelly.pocketcasts.wear.ui.downloads.DownloadsScreen
import au.com.shiftyjelly.pocketcasts.wear.ui.playlists.PlaylistsScreen
import au.com.shiftyjelly.pocketcasts.wear.ui.podcasts.PodcastsScreen
import au.com.shiftyjelly.pocketcasts.wear.ui.settings.SettingsScreen
import au.com.shiftyjelly.pocketcasts.wear.ui.starred.StarredScreen
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.ScalingLazyColumnState
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR

object WatchListScreen {
    const val ROUTE = "watch_list_screen"
}

@Composable
fun WatchListScreen(
    columnState: ScalingLazyColumnState,
    navigateToRoute: (String) -> Unit,
    toNowPlaying: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WatchListScreenViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    CallOnce {
        viewModel.onShown()
    }

    Content(
        columnState = columnState,
        upNextState = state.upNextQueue,
        refreshState = state.refreshState,
        onRefresh = { viewModel.refreshPodcasts() },
        onNowPlayingClick = {
            viewModel.onNowPlayingClicked()
            toNowPlaying()
        },
        onPodcastsClick = {
            viewModel.onPodcastsClicked()
            navigateToRoute(PodcastsScreen.ROUTE_HOME_FOLDER)
        },
        onDownloadsClick = {
            viewModel.onDownloadsClicked()
            navigateToRoute(DownloadsScreen.ROUTE)
        },
        onPlaylistsClick = {
            viewModel.onPlaylistsClicked()
            navigateToRoute(PlaylistsScreen.ROUTE)
        },
        onFilesClick = {
            viewModel.onFilesClicked()
            navigateToRoute(FilesScreen.ROUTE)
        },
        onStarredClick = {
            viewModel.onStarredClicked()
            navigateToRoute(StarredScreen.ROUTE)
        },
        onSettingsClick = {
            viewModel.onSettingsClicked()
            navigateToRoute(SettingsScreen.ROUTE)
        },
        modifier = modifier,
    )
}

@Composable
private fun Content(
    columnState: ScalingLazyColumnState,
    upNextState: UpNextQueue.State?,
    refreshState: RefreshState,
    onRefresh: () -> Unit,
    onNowPlayingClick: () -> Unit,
    onPodcastsClick: () -> Unit,
    onDownloadsClick: () -> Unit,
    onPlaylistsClick: () -> Unit,
    onFilesClick: () -> Unit,
    onStarredClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefresh(
        state = refreshState,
        onRefresh = onRefresh,
        modifier = modifier,
    ) {
        ScalingLazyColumn(
            columnState = columnState,
            modifier = Modifier.fillMaxWidth(),
        ) {
            item {
                // Need this to position the first chip correctly when the screen loads
                Spacer(Modifier)
            }

            if (upNextState is UpNextQueue.State.Loaded) {
                item {
                    NowPlayingChip(onClick = onNowPlayingClick)
                }
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.podcasts),
                    iconRes = IR.drawable.ic_podcasts,
                    onClick = onPodcastsClick,
                )
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.downloads),
                    iconRes = IR.drawable.ic_download,
                    onClick = onDownloadsClick,
                )
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.playlists),
                    iconRes = IR.drawable.ic_playlists,
                    onClick = onPlaylistsClick,
                )
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.profile_navigation_files),
                    iconRes = IR.drawable.ic_file,
                    onClick = onFilesClick,
                )
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.profile_navigation_starred),
                    iconRes = IR.drawable.ic_starred,
                    onClick = onStarredClick,
                )
            }

            item {
                WatchListChip(
                    title = stringResource(LR.string.settings),
                    iconRes = IR.drawable.ic_profile_settings,
                    onClick = onSettingsClick,
                )
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND)
@Composable
private fun WatchListPreview() {
    WearAppTheme {
        Content(
            columnState = ScalingLazyColumnState(),
            upNextState = UpNextQueue.State.Empty,
            refreshState = RefreshState.Never,
            onRefresh = {},
            onNowPlayingClick = {},
            onPodcastsClick = {},
            onDownloadsClick = {},
            onPlaylistsClick = {},
            onFilesClick = {},
            onStarredClick = {},
            onSettingsClick = {},
        )
    }
}
