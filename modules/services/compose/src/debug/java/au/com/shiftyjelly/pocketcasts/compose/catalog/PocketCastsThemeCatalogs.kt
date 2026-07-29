package au.com.shiftyjelly.pocketcasts.compose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import ee.schimke.composeai.preview.ThemeCatalog

/*
 * `@ThemeCatalog` providers for the nine palettes a Pocket Casts user can actually pick.
 *
 * Pocket Casts is not a stock Material app. `AppTheme` resolves a `Theme.ThemeType` to a
 * `ThemeColors` set of ~100 semantic roles (`primaryInteractive01`, `support05`, `filter01`, …)
 * published through `LocalColors`, and only *derives* a Material 2 `Colors` from a handful of them
 * for the components that still read `MaterialTheme.colors`. So the palette that decides what the
 * app looks like lives in `LocalColors`, not in the Material scheme — wrapping `AppTheme` is the
 * only way a specimen sheet reflects what the app really resolves.
 *
 * Nine themes is the point rather than an accident. Light/Dark/Extra Dark are the everyday trio,
 * Classic Light is the pre-7.0 palette kept for continuity, Electric / Indigo / Rosé are the paid
 * themes, and the two Contrast themes are the accessibility palettes. A plain `@Preview` reaches
 * exactly one of them at a time via `uiMode`; a reviewer checking that a new component is legible
 * on Rosé and on Dark Contrast otherwise has to build the app and switch themes by hand. Declaring
 * all nine as theme catalogs renders each one's resolved colours, typography and shapes as its own
 * sheet, so the whole set is reviewable side by side.
 *
 * `AppThemeWithBackground` rather than bare `AppTheme` is deliberate: it adds the `Surface` that
 * supplies `background`/`onBackground`, without which every sticker would render its content on a
 * transparent stage and the light-on-light themes would be unreadable.
 *
 * These live in the `debug` source set, so nothing here reaches a release build.
 */

@ThemeCatalog(name = "Light", group = "Pocket Casts")
class PocketCastsLightThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.LIGHT, content)
}

@ThemeCatalog(name = "Dark", group = "Pocket Casts")
class PocketCastsDarkThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.DARK, content)
}

@ThemeCatalog(name = "Extra Dark", group = "Pocket Casts")
class PocketCastsExtraDarkThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.EXTRA_DARK, content)
}

@ThemeCatalog(name = "Classic Light", group = "Pocket Casts")
class PocketCastsClassicLightThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.CLASSIC_LIGHT, content)
}

@ThemeCatalog(name = "Electric", group = "Pocket Casts")
class PocketCastsElectricThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.ELECTRIC, content)
}

@ThemeCatalog(name = "Indigo", group = "Pocket Casts")
class PocketCastsIndigoThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.INDIGO, content)
}

@ThemeCatalog(name = "Rosé", group = "Pocket Casts")
class PocketCastsRoseThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.ROSE, content)
}

@ThemeCatalog(name = "Light Contrast", group = "Pocket Casts")
class PocketCastsLightContrastThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.LIGHT_CONTRAST, content)
}

@ThemeCatalog(name = "Dark Contrast", group = "Pocket Casts")
class PocketCastsDarkContrastThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = AppThemeWithBackground(Theme.ThemeType.DARK_CONTRAST, content)
}
