@file:Suppress("RestrictedApiAndroidX")

package au.com.shiftyjelly.pocketcasts.compose.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.LocalPreviewThemeType
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme

/** Applies the preview configuration's day/night mode to Pocket Casts themed content. */
class PocketCastsPreviewTheme : PreviewWrapperProvider {
    @Composable
    override fun Wrap(content: @Composable () -> Unit) {
        val themeType = if (isSystemInDarkTheme()) Theme.ThemeType.DARK else Theme.ThemeType.LIGHT
        ProvidePreviewTheme(themeType, content)
    }
}

/**
 * Installs a preview-only theme override so nested [AppThemeWithBackground] calls do not shadow the
 * catalog's selected theme.
 */
@Composable
internal fun ProvidePreviewTheme(
    themeType: Theme.ThemeType,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalPreviewThemeType provides themeType) {
        AppThemeWithBackground(themeType, content)
    }
}
