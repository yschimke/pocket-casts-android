package au.com.shiftyjelly.pocketcasts.wear.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import au.com.shiftyjelly.pocketcasts.wear.theme.WearAppTheme
import ee.schimke.composeai.preview.ThemeCatalog

/*
 * The `@ThemeCatalog` provider for the watch app.
 *
 * Wear is a genuinely separate design system from the phone app rather than the same one at a
 * smaller size, which is why it gets its own catalog rather than a breakpoint in the mobile one.
 * The phone app resolves one of nine user-selectable `Theme.ThemeType` palettes into ~100 semantic
 * roles behind `LocalColors`; `WearAppTheme` instead builds a single Wear Compose `Colors` from the
 * fixed `WearColors` constants over a black background, with no user choice and no `LocalColors` at
 * all. Its type ramp is the Wear one (`display1`…`caption3`), not the phone's, and its components
 * (`Chip`, `ScalingLazyColumn`, curved `TimeText`) have no mobile counterpart.
 *
 * So there is exactly one palette here, and it is dark: the background is `Color.Black`, which is
 * what OLED watch faces want. The spec tags the system `modes: ["dark"]` and
 * `display.surface: "dark"` to match — leave that off and the preview server stages these on white,
 * where the light-on-black content disappears.
 *
 * This lives in the `debug` source set, so nothing here reaches a release build.
 */

@ThemeCatalog(name = "Wear", group = "Pocket Casts")
class PocketCastsWearThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = WearAppTheme(content)
}
