package au.com.shiftyjelly.pocketcasts.compose.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.preview.PocketCastsPreviewTheme
import au.com.shiftyjelly.pocketcasts.compose.preview.ThemePreviewParameterProvider
import au.com.shiftyjelly.pocketcasts.compose.theme
import au.com.shiftyjelly.pocketcasts.ui.theme.Theme
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR

@Composable
fun IconButtonSmall(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(IconButtonDefaults.Size)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = IconButtonDefaults.Ripple,
            ),
        contentAlignment = Alignment.Center,
    ) {
        val contentAlpha = if (enabled) LocalContentAlpha.current else ContentAlpha.disabled
        CompositionLocalProvider(LocalContentAlpha provides contentAlpha, content = content)
    }
}

private object IconButtonDefaults {
    val Size: DpSize = DpSize(32.dp, 32.dp)
    val Ripple = ripple(bounded = false, radius = 20.dp)
}

@Preview
@PreviewWrapper(PocketCastsPreviewTheme::class)
@Composable
private fun IconButtonSmallThemePreview(
    @PreviewParameter(ThemePreviewParameterProvider::class) themeType: Theme.ThemeType,
) {
    AppThemeWithBackground(themeType) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            IconButtonSmall(onClick = {}) {
                Icon(
                    painter = painterResource(IR.drawable.ic_close),
                    contentDescription = stringResource(LR.string.close),
                    tint = MaterialTheme.theme.colors.primaryIcon01,
                )
            }
            IconButtonSmall(onClick = {}) {
                Icon(
                    painter = painterResource(IR.drawable.ic_check),
                    contentDescription = stringResource(LR.string.done),
                    tint = MaterialTheme.theme.colors.primaryInteractive01,
                )
            }
        }
    }
}

// Disabled swaps in ContentAlpha.disabled rather than a different colour, so the only way to
// confirm the affordance still reads is to see the two states next to each other.
@Preview(name = "Enabled and disabled")
@Composable
private fun IconButtonSmallEnabledPreview() {
    AppThemeWithBackground(Theme.ThemeType.LIGHT) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp),
        ) {
            IconButtonSmall(onClick = {}) {
                Icon(
                    painter = painterResource(IR.drawable.ic_close),
                    contentDescription = stringResource(LR.string.close),
                    tint = MaterialTheme.theme.colors.primaryIcon01,
                )
            }
            IconButtonSmall(onClick = {}, enabled = false) {
                Icon(
                    painter = painterResource(IR.drawable.ic_close),
                    contentDescription = stringResource(LR.string.close),
                    tint = MaterialTheme.theme.colors.primaryIcon01,
                )
            }
        }
    }
}
