package au.com.shiftyjelly.pocketcasts.compose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import au.com.shiftyjelly.pocketcasts.compose.preview.ProvidePreviewTheme
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import ee.schimke.composeai.preview.ThemeCatalog

/*
 * `@ThemeCatalog` providers for the seven non-default palettes a Pocket Casts user can pick.
 *
 * Pocket Casts is not a stock Material app. `AppTheme` resolves a `Theme.ThemeType` to a
 * `ThemeColors` set of ~100 semantic roles (`primaryInteractive01`, `support05`, `filter01`, …)
 * published through `LocalColors`, and only *derives* a Material 2 `Colors` from a handful of them
 * for the components that still read `MaterialTheme.colors`. So the palette that decides what the
 * app looks like lives in `LocalColors`, not in the Material scheme — wrapping `AppTheme` is the
 * only way a specimen sheet reflects what the app really resolves.
 *
 * Nine themes is the point rather than an accident. Generic Day/Night controls provide Light and
 * Dark through `uiMode`; these seven providers add Extra Dark, Classic Light, the paid Electric /
 * Indigo / Rosé themes, and the two accessibility Contrast themes. Keeping Light and Dark out of
 * this named axis also prevents duplicate, indistinguishable controls in the catalog.
 *
 * `ProvidePreviewTheme` also makes nested `AppTheme` calls respect the selected catalog provider;
 * otherwise a preview's own fixed or parameterized wrapper would shadow this outer wrapper.
 *
 * These live in the `debug` source set, so nothing here reaches a release build.
 */

@ThemeCatalog(name = "Extra Dark", group = "Pocket Casts")
class PocketCastsExtraDarkThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.EXTRA_DARK, content)
}

@ThemeCatalog(name = "Classic Light", group = "Pocket Casts")
class PocketCastsClassicLightThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.CLASSIC_LIGHT, content)
}

@ThemeCatalog(name = "Electric", group = "Pocket Casts")
class PocketCastsElectricThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.ELECTRIC, content)
}

@ThemeCatalog(name = "Indigo", group = "Pocket Casts")
class PocketCastsIndigoThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.INDIGO, content)
}

@ThemeCatalog(name = "Rosé", group = "Pocket Casts")
class PocketCastsRoseThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.ROSE, content)
}

@ThemeCatalog(name = "Light Contrast", group = "Pocket Casts")
class PocketCastsLightContrastThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.LIGHT_CONTRAST, content)
}

@ThemeCatalog(name = "Dark Contrast", group = "Pocket Casts")
class PocketCastsDarkContrastThemeCatalog : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) = ProvidePreviewTheme(Theme.ThemeType.DARK_CONTRAST, content)
}
