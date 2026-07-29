package au.com.shiftyjelly.pocketcasts.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.preview.ThemePreviewParameterProvider
import au.com.shiftyjelly.pocketcasts.compose.theme
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR

@Composable
fun ExplicitIcon(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.theme.colors.primaryText02,
    size: Dp = with(LocalDensity.current) { 16.sp.toDp() },
) {
    Icon(
        painter = painterResource(IR.drawable.explicit),
        contentDescription = stringResource(LR.string.explicit),
        tint = tint,
        modifier = modifier.size(size),
    )
}

@Preview
@Composable
private fun ExplicitIconThemePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) themeType: Theme.ThemeType,
) {
    AppThemeWithBackground(themeType) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            ExplicitIcon()
            ExplicitIcon(tint = MaterialTheme.theme.colors.primaryText01)
            ExplicitIcon(tint = MaterialTheme.theme.colors.support05)
        }
    }
}

// The default size tracks the 16sp text size, so it grows with the user's font scale. These are the
// sizes it is actually asked to draw at — a row badge, a title badge and the episode-header badge.
@Preview(name = "Sizes")
@Composable
private fun ExplicitIconSizePreview() {
    AppThemeWithBackground(Theme.ThemeType.LIGHT) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            ExplicitIcon(size = 12.dp)
            ExplicitIcon()
            ExplicitIcon(size = 24.dp)
            ExplicitIcon(size = 40.dp)
        }
    }
}
