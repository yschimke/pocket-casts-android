package au.com.shiftyjelly.pocketcasts.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.com.shiftyjelly.pocketcasts.compose.AppThemeWithBackground
import au.com.shiftyjelly.pocketcasts.compose.CallOnce
import au.com.shiftyjelly.pocketcasts.compose.extensions.contentWithoutConsumedInsets
import au.com.shiftyjelly.pocketcasts.preferences.Settings
import au.com.shiftyjelly.pocketcasts.preferences.model.HeadphoneAction
import au.com.shiftyjelly.pocketcasts.repositories.playback.PlaybackManager
import au.com.shiftyjelly.pocketcasts.settings.onboarding.OnboardingFlow
import au.com.shiftyjelly.pocketcasts.settings.onboarding.OnboardingLauncher
import au.com.shiftyjelly.pocketcasts.settings.onboarding.OnboardingUpgradeSource
import au.com.shiftyjelly.pocketcasts.utils.extensions.pxToDp
import au.com.shiftyjelly.pocketcasts.views.dialog.OptionsDialog
import au.com.shiftyjelly.pocketcasts.views.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import au.com.shiftyjelly.pocketcasts.localization.R as LR

@AndroidEntryPoint
class HeadphoneControlsSettingsFragment : BaseFragment() {
    @Inject
    lateinit var settings: Settings

    @Inject
    lateinit var playbackManager: PlaybackManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = contentWithoutConsumedInsets {
        val bottomInset = settings.bottomInset.collectAsStateWithLifecycle(initialValue = 0)
        AppThemeWithBackground(theme.activeTheme) {
            HeadphoneControlsSettings(
                bottomInset = bottomInset.value.pxToDp(LocalContext.current).dp,
            )
        }
    }

    /**
     * The stateful half of the headphone controls screen: it owns the view model, reads the three
     * preferences the screen renders, raises the options dialogs and starts the upsell flow. The
     * screen itself is [HeadphoneControlsSettingsPage], which is a top-level composable and takes
     * only values and callbacks.
     */
    @Composable
    private fun HeadphoneControlsSettings(
        bottomInset: Dp,
        viewModel: HeadphoneControlsSettingsPageViewModel = hiltViewModel(),
    ) {
        val state by viewModel.state.collectAsState()
        val previousAction = settings.headphoneControlsPreviousAction.flow.collectAsState().value
        val nextAction = settings.headphoneControlsNextAction.flow.collectAsState().value
        val confirmationSound = settings.headphoneControlsPlayBookmarkConfirmationSound.flow.collectAsState().value

        CallOnce {
            viewModel.onShown()
        }

        HeadphoneControlsSettingsPage(
            previousAction = previousAction,
            nextAction = nextAction,
            confirmationSound = confirmationSound,
            bottomInset = bottomInset,
            onPreviousActionClick = {
                viewModel.onOptionsDialogShown()
                showActionOptionsDialog(
                    titleId = LR.string.settings_headphone_controls_action_previous,
                    saved = previousAction,
                    // The previous button lists skip-back first, the next button skip-forward.
                    firstAction = HeadphoneAction.SKIP_BACK,
                    secondAction = HeadphoneAction.SKIP_FORWARD,
                    addBookmarkIconId = state.addBookmarkIconId,
                    iconColor = state.addBookmarkIconColor.toArgb(),
                    tag = "action_previous_options",
                    onSave = viewModel::onPreviousActionSave,
                )
            },
            onNextActionClick = {
                viewModel.onOptionsDialogShown()
                showActionOptionsDialog(
                    titleId = LR.string.settings_headphone_controls_action_next,
                    saved = nextAction,
                    firstAction = HeadphoneAction.SKIP_FORWARD,
                    secondAction = HeadphoneAction.SKIP_BACK,
                    addBookmarkIconId = state.addBookmarkIconId,
                    iconColor = state.addBookmarkIconColor.toArgb(),
                    tag = "action_next_options",
                    onSave = viewModel::onNextActionSave,
                )
            },
            onConfirmationSoundChange = { newValue ->
                settings.headphoneControlsPlayBookmarkConfirmationSound.set(newValue, updateModifiedAt = true)
                if (newValue) {
                    playbackManager.playBookmarkTone()
                }
                viewModel.onConfirmationSoundChanged(newValue)
            },
            onBackPress = {
                activity?.onBackPressedDispatcher?.onBackPressed()
            },
        )

        LaunchedEffect(state) {
            state.startUpsellFromSource?.let { startUpsellFlow() }
        }
    }

    private fun showActionOptionsDialog(
        @StringRes titleId: Int,
        saved: HeadphoneAction,
        firstAction: HeadphoneAction,
        secondAction: HeadphoneAction,
        @DrawableRes addBookmarkIconId: Int?,
        iconColor: Int,
        tag: String,
        onSave: (HeadphoneAction) -> Unit,
    ) {
        OptionsDialog()
            .setTitle(getString(titleId))
            .setIconColor(iconColor)
            .addCheckedOption(
                titleId = headphoneActionToStringRes(firstAction),
                checked = saved == firstAction,
            ) {
                onSave(firstAction)
            }
            .addCheckedOption(
                titleId = headphoneActionToStringRes(secondAction),
                checked = saved == secondAction,
            ) {
                onSave(secondAction)
            }
            .addCheckedOption(
                imageId = addBookmarkIconId,
                titleId = headphoneActionToStringRes(HeadphoneAction.ADD_BOOKMARK),
                checked = saved == HeadphoneAction.ADD_BOOKMARK,
            ) {
                onSave(HeadphoneAction.ADD_BOOKMARK)
            }
            .show(childFragmentManager, tag)
    }

    private fun startUpsellFlow() {
        val onboardingFlow = OnboardingFlow.Upsell(
            source = OnboardingUpgradeSource.HEADPHONE_CONTROLS_SETTINGS,
        )
        OnboardingLauncher.openOnboardingFlow(requireActivity(), onboardingFlow)
    }
}
