package au.com.shiftyjelly.pocketcasts.compose.catalog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.dp
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.bars.NavigationButton
import au.com.shiftyjelly.pocketcasts.compose.bars.ThemedTopAppBar
import au.com.shiftyjelly.pocketcasts.compose.buttons.RowButton
import au.com.shiftyjelly.pocketcasts.compose.buttons.RowOutlinedButton
import au.com.shiftyjelly.pocketcasts.compose.components.SettingRow
import au.com.shiftyjelly.pocketcasts.compose.components.SettingRowToggle
import au.com.shiftyjelly.pocketcasts.compose.components.SettingSection
import au.com.shiftyjelly.pocketcasts.compose.preview.PocketCastsPreviewTheme
import au.com.shiftyjelly.pocketcasts.compose.preview.PreviewPocketCastsLightDarkPhone
import au.com.shiftyjelly.pocketcasts.compose.preview.ThemePreviewParameterProvider
import au.com.shiftyjelly.pocketcasts.compose.theme
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import au.com.shiftyjelly.pocketcasts.images.R as IR

/**
 * Screen-scale `@Preview`s assembled from this module's own components.
 *
 * Everything else in `:modules:services:compose` is previewed one component at a time, which is
 * the right unit for a button but leaves two things invisible. First, the theme roles only really
 * argue with each other at screen scale: `secondaryUi01` behind the app bar against `primaryUi01`
 * behind the content, divider contrast down a long list, and the toggle tint against the row
 * background are all pairwise decisions no single-component sticker shows. Second, the catalog's
 * front door needs a hero, and the preview server picks one from the `Screens` section — with no
 * screens at all it settles on a lone button, which is a poor advertisement for a nine-theme
 * design system.
 *
 * These compose only components this module already owns, with fixed content and no ViewModel, so
 * they render deterministically and cannot drift from the real components they are built out of.
 * They are compositions *of* the design system rather than screenshots of the app: the app's own
 * screens live in the feature modules and are not reachable from here. Those screens are published
 * separately as the `pocketcasts-screens` catalog, which points the renderer at a feature module
 * directly — see catalog.screens.spec.json.
 *
 * These live in the `debug` source set, so nothing here reaches a release build.
 */

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsSettingsScreenPreview() {
    SettingsScreen(Theme.ThemeType.LIGHT)
}

// The nine-palette fan-out — the whole reason this design system is worth a sticker sheet. Renders
// the same screen once per user-selectable theme so the paid and accessibility palettes are
// reviewable against the everyday ones instead of one build-and-switch at a time.
@Preview(widthDp = 412, heightDp = 892)
@Composable
private fun PocketCastsSettingsScreenThemePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) themeType: Theme.ThemeType,
) {
    SettingsScreen(themeType)
}

// A tablet width. `SettingRow` keeps its fixed horizontal padding rather than centring a content
// column, so the extra width lands entirely in the gap between label and toggle — worth seeing.
@Preview(name = "Settings — medium width", widthDp = 700, heightDp = 892)
@Composable
private fun PocketCastsSettingsScreenMediumPreview() {
    SettingsScreen(Theme.ThemeType.LIGHT)
}

@PreviewPocketCastsLightDarkPhone
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun PocketCastsAccountScreenPreview() {
    AccountScreen(Theme.ThemeType.LIGHT)
}

@Composable
private fun SettingsScreen(themeType: Theme.ThemeType) {
    AppThemeWithBackground(themeType) {
        Column(Modifier.fillMaxSize()) {
            ThemedTopAppBar(
                title = "Settings",
                navigationButton = NavigationButton.Back,
                onNavigationClick = {},
            )
            Column(Modifier.verticalScroll(rememberScrollState())) {
                SettingSection(heading = "Playback") {
                    SettingRow(
                        primaryText = "Skip forward",
                        secondaryText = "45 seconds",
                        icon = painterResource(IR.drawable.ic_filters_play),
                    )
                    SettingRow(
                        primaryText = "Skip back",
                        secondaryText = "10 seconds",
                        icon = painterResource(IR.drawable.ic_filters_clock),
                    )
                    SettingRow(
                        primaryText = "Play up next on tap",
                        toggle = SettingRowToggle.Switch(checked = true),
                    )
                    SettingRow(
                        primaryText = "Autoplay",
                        secondaryText = "Keep playing similar episodes when you reach the end of your Up Next queue",
                        toggle = SettingRowToggle.Switch(checked = false),
                    )
                }
                SettingSection(heading = "Downloads") {
                    SettingRow(
                        primaryText = "Only on unmetered Wi-Fi",
                        icon = painterResource(IR.drawable.ic_filters_download),
                        toggle = SettingRowToggle.Switch(checked = true),
                    )
                    SettingRow(
                        primaryText = "Warn before using data",
                        toggle = SettingRowToggle.Checkbox(checked = true),
                    )
                    // A disabled row: the whole row drops to the disabled alpha rather than
                    // changing colour, which is the case worth eyeballing on the light palettes.
                    SettingRow(
                        primaryText = "Download all new episodes",
                        secondaryText = "Unavailable while storage is full",
                        toggle = SettingRowToggle.Switch(checked = false, enabled = false),
                        enabled = false,
                    )
                }
                SettingSection(heading = "Appearance", showDivider = false) {
                    SettingRow(
                        primaryText = "Theme",
                        secondaryText = themeType.name.lowercase().replace('_', ' '),
                        icon = painterResource(IR.drawable.ic_filters_star),
                    )
                    SettingRow(
                        primaryText = "Show archived episodes",
                        toggle = SettingRowToggle.Switch(checked = false),
                    )
                }
            }
        }
    }
}

@Composable
private fun AccountScreen(themeType: Theme.ThemeType) {
    AppThemeWithBackground(themeType) {
        Column(Modifier.fillMaxSize()) {
            ThemedTopAppBar(
                title = "Account",
                navigationButton = NavigationButton.Close,
                onNavigationClick = {},
            )
            Column(Modifier.verticalScroll(rememberScrollState())) {
                SettingSection(heading = "Signed in as") {
                    SettingRow(
                        primaryText = "listener@pocketcasts.com",
                        secondaryText = "Pocket Casts Plus — renews 3 March 2027",
                        icon = painterResource(IR.drawable.ic_check),
                    )
                }
                SettingSection(heading = "Subscription", showDivider = false) {
                    SettingRow(
                        primaryText = "Change plan",
                        secondaryText = "Plus, billed yearly",
                    )
                    SettingRow(
                        primaryText = "Cloud storage",
                        secondaryText = "1.2 GB of 10 GB used",
                    )
                }
                RowButton(
                    text = "Upgrade to Patron",
                    onClick = {},
                )
                // The destructive action carries support05 on both the label and the border, which
                // is the pairing with the least headroom on the dark and high-contrast palettes.
                RowOutlinedButton(
                    text = "Sign out",
                    onClick = {},
                    border = BorderStroke(2.dp, MaterialTheme.theme.colors.support05),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = Color.Transparent,
                        contentColor = MaterialTheme.theme.colors.support05,
                    ),
                )
            }
        }
    }
}
