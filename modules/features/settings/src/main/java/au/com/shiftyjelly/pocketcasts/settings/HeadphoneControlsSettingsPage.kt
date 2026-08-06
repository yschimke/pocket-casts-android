package au.com.shiftyjelly.pocketcasts.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import au.com.shiftyjelly.pocketcasts.compose.bars.ThemedTopAppBar
import au.com.shiftyjelly.pocketcasts.compose.components.SettingRow
import au.com.shiftyjelly.pocketcasts.compose.components.SettingRowToggle
import au.com.shiftyjelly.pocketcasts.compose.components.TextP50
import au.com.shiftyjelly.pocketcasts.compose.theme
import au.com.shiftyjelly.pocketcasts.preferences.model.HeadphoneAction
import au.com.shiftyjelly.pocketcasts.images.R as IR
import au.com.shiftyjelly.pocketcasts.localization.R as LR

/**
 * The headphone controls settings screen, as a plain function of its inputs.
 *
 * This used to be a set of member composables on [HeadphoneControlsSettingsFragment], which put the
 * screen out of reach of anything that is not a running app: the rows read the `Fragment`'s
 * `childFragmentManager` and `getString` directly, so the whole screen could only be composed with a
 * host fragment attached. That is also why the preview that lived beside them was a member function
 * of the fragment rather than a top-level one.
 *
 * Everything stateful now lives in the fragment's own wrapper — the view model, the preference
 * writes, the `OptionsDialog` the two action rows raise, and the upsell flow — and reaches this
 * screen as values and callbacks. The view model's `UiState` is deliberately absent from the
 * parameter list: nothing on this screen renders from it, it only supplies the icon the options
 * dialog draws next to "Add bookmark", so it belongs on the caller's side of the boundary.
 */
@Composable
internal fun HeadphoneControlsSettingsPage(
    previousAction: HeadphoneAction,
    nextAction: HeadphoneAction,
    confirmationSound: Boolean,
    bottomInset: Dp,
    onPreviousActionClick: () -> Unit,
    onNextActionClick: () -> Unit,
    onConfirmationSoundChange: (Boolean) -> Unit,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        ThemedTopAppBar(
            title = stringResource(LR.string.settings_title_headphone_controls),
            bottomShadow = true,
            onNavigationClick = onBackPress,
        )
        LazyColumn(
            contentPadding = PaddingValues(bottom = bottomInset),
        ) {
            item {
                TextP50(
                    text = stringResource(LR.string.settings_headphone_controls_summary),
                    color = MaterialTheme.theme.colors.primaryText02,
                    modifier = Modifier.padding(16.dp),
                )
            }
            item {
                SettingRow(
                    primaryText = stringResource(LR.string.settings_headphone_controls_action_previous),
                    secondaryText = stringResource(headphoneActionToStringRes(previousAction)),
                    icon = painterResource(IR.drawable.ic_skip_back),
                    modifier = Modifier
                        .clickable(onClick = onPreviousActionClick)
                        .padding(vertical = 6.dp),
                )
            }
            item {
                SettingRow(
                    primaryText = stringResource(LR.string.settings_headphone_controls_action_next),
                    secondaryText = stringResource(headphoneActionToStringRes(nextAction)),
                    icon = painterResource(IR.drawable.ic_skip_forward),
                    modifier = Modifier.clickable(onClick = onNextActionClick),
                )
            }
            // The confirmation sound only means anything once one of the buttons adds a bookmark.
            if (previousAction == HeadphoneAction.ADD_BOOKMARK || nextAction == HeadphoneAction.ADD_BOOKMARK) {
                item {
                    SettingRow(
                        primaryText = stringResource(LR.string.settings_headphone_controls_confirmation_sound),
                        secondaryText = stringResource(LR.string.settings_headphone_controls_confirmation_sound_summary),
                        toggle = SettingRowToggle.Switch(checked = confirmationSound),
                        modifier = Modifier.toggleable(value = confirmationSound, role = Role.Switch) {
                            onConfirmationSoundChange(!confirmationSound)
                        },
                    )
                }
            }
        }
    }
}

@StringRes
internal fun headphoneActionToStringRes(action: HeadphoneAction) = when (action) {
    HeadphoneAction.ADD_BOOKMARK -> LR.string.settings_headphone_controls_choice_add_bookmark
    HeadphoneAction.SKIP_BACK -> LR.string.settings_headphone_controls_choice_skip_back
    HeadphoneAction.SKIP_FORWARD -> LR.string.settings_headphone_controls_choice_skip_forward
    HeadphoneAction.NEXT_CHAPTER -> LR.string.settings_headphone_controls_choice_next_chapter
    HeadphoneAction.PREVIOUS_CHAPTER -> LR.string.settings_headphone_controls_choice_previous_chapter
}
