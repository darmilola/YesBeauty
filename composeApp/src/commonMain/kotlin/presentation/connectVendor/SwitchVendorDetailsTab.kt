package presentation.connectVendor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.CustomerMovementEnum
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.Splashscreen.SplashScreen
import presentation.components.IndeterminateCircularProgressBar
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.profile.ProfilePresenter
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel

class SwitchVendorDetailsTab(private val mainViewModel: MainViewModel) : Tab, KoinComponent {
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    private val profilePresenter: ProfilePresenter by inject()
    override val options: TabOptions
        @Composable
        get() {
            val title = "Switch Vendor"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {
        if (actionUIStateViewModel == null) {
            actionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        val handler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            actionUIStateViewModel!!)

        val switchVendorId = mainViewModel.switchVendorId.value
        val switchVendorReason = mainViewModel.switchVendorReason.value
        val userInfo = mainViewModel.currentUserInfo.value
        val uiState = actionUIStateViewModel!!.uiStateInfo.collectAsState()




        Scaffold(
            topBar = {
                BusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {

                println(uiState.value)
                if (uiState.value.isLoading) {
                    LoadingDialog("Connecting New Vendor")
                } else if (uiState.value.isSuccess) {

                    SuccessDialog(dialogTitle = "Switch Vendor Completed", actionTitle = "Close"){}

                } else if (uiState.value.isFailed) {
                    ErrorDialog(dialogTitle = "Error Occurred Please Try Again", actionTitle = "Retry"){

                    }
                }

                BusinessInfoContent(mainViewModel.connectedVendor.value){
                 profilePresenter.switchVendor(userId = userInfo.userId!!,
                     vendorId = switchVendorId, action = CustomerMovementEnum.Exit.toPath(),
                     exitReason = switchVendorReason)
                }
            },
            backgroundColor = Color.Transparent
        )
    }
}
