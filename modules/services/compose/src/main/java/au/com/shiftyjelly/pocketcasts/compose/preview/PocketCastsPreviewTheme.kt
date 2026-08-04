@file:Suppress("RestrictedApiAndroidX")

package au.com.shiftyjelly.pocketcasts.compose.preview

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewWrapperProvider
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.LocalPreviewThemeType
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewPocketCastsLightDark

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
annotation class PreviewPocketCastsLightDarkBackground

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Light", widthDp = 412, heightDp = 892, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", widthDp = 412, heightDp = 892, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewPocketCastsLightDarkPhone

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Light", widthDp = 400, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", widthDp = 400, heightDp = 800, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewPocketCastsLightDarkLarge

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Light", widthDp = 80, heightDp = 80, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", widthDp = 80, heightDp = 80, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewPocketCastsLightDarkIcon

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
