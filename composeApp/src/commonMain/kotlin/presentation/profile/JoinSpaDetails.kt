package presentation.profile

import UIStates.ActionUIStates
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Enums.CustomerMovementEnum
import domain.Enums.Screens
import domain.Models.PlatformNavigator
import domain.Models.Vendor
import kotlinx.serialization.Transient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.ProfileHandler
import presentation.DomainViewHandler.SwitchVendorHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.widgets.BusinessInfoContent
import presentation.widgets.BusinessInfoTitle

@Parcelize
class JoinDetailsTab(val platformNavigator: PlatformNavigator) : Tab, KoinComponent, Parcelable {
    @Transient
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private val profilePresenter: ProfilePresenter by inject()
    @Transient
    private val preferenceSettings: Settings = Settings()
    @Transient
    private var mainViewModel: MainViewModel? = null
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

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
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

        val profileHandler = ProfileHandler(profilePresenter,
            onUserLocationReady = {},
            onVendorInfoReady = { it -> },
            actionUIStateViewModel!!)
        profileHandler.init()


        val userInfo = mainViewModel!!.currentUserInfo.value
        val uiState = actionUIStateViewModel!!.uiStateInfo.collectAsState()


        Scaffold(
            topBar = {
                BusinessInfoTitle(mainViewModel = mainViewModel)
            },
            content = {
                val joinVendor = mainViewModel!!.joinSpaVendor.value
                if (uiState.value.isLoading) {
                    LoadingDialog("Joining Spa")
                } else if (uiState.value.isSuccess) {
                    actionUIStateViewModel!!.switchActionUIState(ActionUIStates(isDefault = true))
                    platformNavigator.restartApp()
                    mainViewModel!!.setSwitchVendor(vendor = Vendor())
                    mainViewModel!!.setJoinSpaVendor(vendor = Vendor())
                } else if (uiState.value.isFailed) {
                    ErrorDialog(dialogTitle = "Error Occurred Please Try Again", actionTitle = "Retry"){}
                }

                BusinessInfoContent(joinVendor){
                    profilePresenter.joinSpa(therapistId = userInfo.userId!!, vendorId = joinVendor.vendorId!!)
                }
            },
            backgroundColor = Color.Transparent
        )
    }
}
