package au.com.shiftyjelly.pocketcasts.settings.catalog

import androidx.compose.material.AppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import au.com.shiftyjelly.pocketcasts.compose.preview.PocketCastsPreviewTheme
import au.com.shiftyjelly.pocketcasts.compose.preview.PreviewPocketCastsLightDarkPhone
import au.com.shiftyjelly.pocketcasts.models.entity.PodcastEpisode
import au.com.shiftyjelly.pocketcasts.models.entity.UpNextHistoryEntry
import au.com.shiftyjelly.pocketcasts.preferences.model.HeadphoneAction
import au.com.shiftyjelly.pocketcasts.settings.HeadphoneControlsSettingsPage
import au.com.shiftyjelly.pocketcasts.settings.LogsContent
import au.com.shiftyjelly.pocketcasts.settings.history.upnext.UpNextHistoryDetailsView
import au.com.shiftyjelly.pocketcasts.settings.history.upnext.UpNextHistoryDetailsViewModel
import au.com.shiftyjelly.pocketcasts.settings.history.upnext.UpNextHistoryPageView
import au.com.shiftyjelly.pocketcasts.settings.history.upnext.UpNextHistoryPreviewDates
import au.com.shiftyjelly.pocketcasts.settings.history.upnext.UpNextHistoryViewModel
import au.com.shiftyjelly.pocketcasts.settings.status.Service
import au.com.shiftyjelly.pocketcasts.settings.status.ServiceStatus
import au.com.shiftyjelly.pocketcasts.settings.status.ServiceStatusChecker
import au.com.shiftyjelly.pocketcasts.settings.status.StatusPageView
import au.com.shiftyjelly.pocketcasts.settings.status.StatusUiState
import au.com.shiftyjelly.pocketcasts.settings.whatsnew.SyncedTranscriptsHeader
import au.com.shiftyjelly.pocketcasts.settings.whatsnew.WhatsNewPageLoaded
import au.com.shiftyjelly.pocketcasts.settings.whatsnew.WhatsNewViewModel
import java.util.Date
import au.com.shiftyjelly.pocketcasts.localization.R as LR

/**
 * Whole-screen `@Preview`s for the design catalog, one per settings screen this module owns.
 *
 * These are the app's real screens rather than compositions assembled out of the design system:
 * every composable below is the same one the running app renders, called with a hand-built state
 * object in place of its view model. That is only possible because each screen has had its view
 * model hoisted into a thin stateful wrapper — see [HeadphoneControlsSettingsPage] and
 * [StatusPageView], which were the last two still reading a view model (and, in the headphone
 * controls case, a `Fragment`) from inside the screen itself.
 *
 * They are collected here rather than left beside each screen for two reasons. The per-screen
 * previews next to the code fan out across all nine `Theme.ThemeType` palettes through
 * `ThemePreviewParameterProvider`, which is what you want when you are working on that one screen
 * in Android Studio; the catalog wants a light/dark pair per screen at a fixed phone size, and a
 * stable preview-function name it can address by `componentId`. Keeping the two sets apart means
 * neither has to compromise. It also puts every fixture in one file, which is where determinism is
 * easiest to keep an eye on: the catalog re-renders these and diffs the PNGs, so a clock, a random
 * value or a network image would each read as a UI change on an unchanged screen.
 *
 * Not every screen in this module is here. `HelpPage` is a `WebView` wrapped in an app bar, so a
 * render of it is a render of whatever support.pocketcasts.com served that morning, and
 * `NotificationsTestingPage` is an internal debugging tool rather than product surface.
 */

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsHeadphoneControlsScreenPreview() {
    HeadphoneControlsSettingsPage(
        previousAction = HeadphoneAction.SKIP_BACK,
        nextAction = HeadphoneAction.ADD_BOOKMARK,
        confirmationSound = true,
        bottomInset = 0.dp,
        onPreviousActionClick = {},
        onNextActionClick = {},
        onConfirmationSoundChange = {},
        onBackPress = {},
    )
}

// Neither button is bound to "Add bookmark", so the confirmation sound row is not rendered at all.
// The screen is two rows shorter than the state above, which is the whole point of showing it.
@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsHeadphoneControlsSkipOnlyScreenPreview() {
    HeadphoneControlsSettingsPage(
        previousAction = HeadphoneAction.SKIP_BACK,
        nextAction = HeadphoneAction.SKIP_FORWARD,
        confirmationSound = false,
        bottomInset = 0.dp,
        onPreviousActionClick = {},
        onNextActionClick = {},
        onConfirmationSoundChange = {},
        onBackPress = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsUpNextHistoryScreenPreview() {
    UpNextHistoryPageView(
        state = UpNextHistoryViewModel.UiState.Loaded(
            entries = listOf(
                UpNextHistoryEntry(date = Date(UpNextHistoryPreviewDates.FIRST_ENTRY), episodeCount = 5),
                UpNextHistoryEntry(date = Date(UpNextHistoryPreviewDates.SECOND_ENTRY), episodeCount = 3),
            ),
        ),
        bottomInset = 0.dp,
        onBackPress = {},
        onHistoryEntryClick = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsUpNextHistoryErrorScreenPreview() {
    UpNextHistoryPageView(
        state = UpNextHistoryViewModel.UiState.Error,
        bottomInset = 0.dp,
        onBackPress = {},
        onHistoryEntryClick = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsUpNextHistoryDetailsScreenPreview() {
    UpNextHistoryDetailsView(
        state = UpNextHistoryDetailsViewModel.UiState.Loaded(
            episodes = listOf(
                PodcastEpisode(
                    uuid = "6f0f0a4a-1a1f-4a3e-9f2f-9a1c0d4b5e6f",
                    title = "The one about hoisting state out of the view",
                    publishedDate = Date(UpNextHistoryPreviewDates.FIRST_EPISODE_PUBLISHED),
                    duration = 2_754.0,
                ),
                PodcastEpisode(
                    uuid = "b2c3d4e5-6f70-4819-a2b3-c4d5e6f70819",
                    title = "A shorter follow-up",
                    publishedDate = Date(UpNextHistoryPreviewDates.SECOND_EPISODE_PUBLISHED),
                    duration = 1_320.0,
                ),
            ),
            // Artwork is a network load, so leave it off — the renderer has no network and would
            // fall back to a placeholder anyway, only less predictably.
            useEpisodeArtwork = false,
        ),
        date = UpNextHistoryPreviewDates.FIRST_ENTRY,
        bottomInset = 0.dp,
        onRestoreClick = {},
        onBackPress = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsStatusScreenPreview() {
    StatusPageView(
        state = StatusUiState.ListServices(
            services = listOf(
                previewService(LR.string.settings_status_service_internet, LR.string.settings_status_service_internet_summary, ServiceStatus.Success),
                previewService(LR.string.settings_status_service_refresh, LR.string.settings_status_service_refresh_summary, ServiceStatus.Success),
                previewService(LR.string.settings_status_service_account, LR.string.settings_status_service_account_summary_2, ServiceStatus.Success),
                // One failure, because the failed row is the one worth looking at: it swaps the
                // service summary for the help text and paints the message in support05.
                previewService(
                    title = LR.string.settings_status_service_discover,
                    summary = LR.string.settings_status_service_discover_summary,
                    status = ServiceStatus.Failed(userMessage = "Unable to connect", log = "preview"),
                    help = LR.string.settings_status_service_ad_blocker_help_plural,
                    helpArgs = listOf("static.pocketcasts.com, cache.pocketcasts.com and podcasts.pocketcasts.com"),
                ),
                previewService(LR.string.settings_status_service_hosts, LR.string.settings_status_service_hosts_summary, ServiceStatus.Success),
            ),
            running = false,
        ),
        bottomInset = 0.dp,
        onBackPress = {},
        onRun = {},
        onSendReport = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsStatusWelcomeScreenPreview() {
    StatusPageView(
        state = StatusUiState.Welcome,
        bottomInset = 0.dp,
        onBackPress = {},
        onRun = {},
        onSendReport = {},
    )
}

/**
 * `ServiceStatus.Running` is deliberately never used above: it renders a `CircularProgressIndicator`,
 * whose frame depends on when the renderer decides the composition has settled.
 */
private fun previewService(
    title: Int,
    summary: Int,
    status: ServiceStatus,
    help: Int = LR.string.settings_status_service_ad_blocker_help_singular,
    helpArgs: List<String> = listOf("api.pocketcasts.com"),
) = Service(
    title = title,
    summary = summary,
    help = help,
    helpArgs = helpArgs,
    // Only `status` reaches the UI — `check` is what the view model would run, and nothing here runs.
    check = ServiceStatusChecker.Check.Internet,
    status = status,
)

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsLogsScreenPreview() {
    LogsContent(
        logLines = listOf(
            "2024-05-14 09:30:01 [Playback] Starting playback of episode 6f0f0a4a",
            "2024-05-14 09:30:01 [Playback] Buffering",
            "2024-05-14 09:30:03 [Playback] Playing",
            "2024-05-14 09:34:22 [Sync] Up Next changed, queued for sync",
            "2024-05-14 09:34:25 [Sync] Sync complete, 3 episodes updated",
        ),
        includeAppBar = true,
        bottomInset = 0.dp,
        appBarInsets = AppBarDefaults.topAppBarWindowInsets,
        onBackPress = {},
        onCopyToClipboard = {},
        onShareLogs = {},
    )
}

// With nothing to share the app bar's share action drops out, which is the only difference between
// this and the state above — easy to regress, hard to notice.
@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsLogsEmptyScreenPreview() {
    LogsContent(
        logLines = emptyList(),
        includeAppBar = true,
        bottomInset = 0.dp,
        appBarInsets = AppBarDefaults.topAppBarWindowInsets,
        onBackPress = {},
        onCopyToClipboard = {},
        onShareLogs = {},
    )
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsWhatsNewScreenPreview() {
    WhatsNewPageLoaded(
        state = WhatsNewViewModel.UiState.Loaded(
            feature = WhatsNewViewModel.WhatsNewFeature.SyncedTranscripts(isUserEntitled = true),
        ),
        onConfirm = {},
        onClose = {},
        header = { SyncedTranscriptsHeader() },
    )
}

// The unentitled variant swaps the confirm button for the trial upsell, so the sheet is a different
// height and carries a different call to action.
@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsWhatsNewUpsellScreenPreview() {
    WhatsNewPageLoaded(
        state = WhatsNewViewModel.UiState.Loaded(
            feature = WhatsNewViewModel.WhatsNewFeature.SyncedTranscripts(isUserEntitled = false),
        ),
        onConfirm = {},
        onClose = {},
        header = { SyncedTranscriptsHeader() },
    )
}

// A tablet width for the screen with the most horizontal layout to give away: the settings rows
// keep their fixed padding, so the extra width lands between the label and the trailing control.
@Preview(name = "Headphone controls — medium width", widthDp = 700, heightDp = 892)
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsHeadphoneControlsMediumScreenPreview() {
    HeadphoneControlsSettingsPage(
        previousAction = HeadphoneAction.SKIP_BACK,
        nextAction = HeadphoneAction.ADD_BOOKMARK,
        confirmationSound = true,
        bottomInset = 0.dp,
        onPreviousActionClick = {},
        onNextActionClick = {},
        onConfirmationSoundChange = {},
        onBackPress = {},
    )
}
