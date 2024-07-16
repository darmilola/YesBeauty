package presentation.therapist

import GGSansSemiBold
import StackedSnackbarHost
import UIStates.ActionUIStates
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import kotlinx.serialization.Transient
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.TherapistHandler
import presentation.dialogs.LoadingDialog
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.TherapistAppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.TherapistViewModel
import presentation.viewmodels.UIStateViewModel
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors

class TherapistDashboardTab() : Tab, KoinComponent {

    @Transient
    private val therapistPresenter: TherapistPresenter by inject()
    @Transient
    private var uiStateViewModel: UIStateViewModel? = null
    @Transient
    private var actionUiStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private var therapistViewModel: TherapistViewModel? = null
    @Transient
    private var appointmentResourceListEnvelopeViewModel: TherapistAppointmentResourceListEnvelopeViewModel? = null
    @Transient
    private var availabilityActionUIStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private var joinMeetingActionUIStateViewModel: ActionUIStateViewModel? = null
    @Transient
    private var mainViewModel: MainViewModel? = null

    @OptIn(ExperimentalResourceApi::class)
    override val options: TabOptions
        @Composable
        get() {
            val title = "Therapist Dashboard"
            val icon = painterResource("drawable/dashboard_icon.png")

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon

                )
            }
        }

    fun setMainViewModel(mainViewModel: MainViewModel){
        this.mainViewModel = mainViewModel
    }

    @Composable
    override fun Content() {

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (availabilityActionUIStateViewModel == null) {
            availabilityActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (joinMeetingActionUIStateViewModel == null) {
            joinMeetingActionUIStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

        if (uiStateViewModel == null) {
            uiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    UIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (actionUiStateViewModel == null) {
            actionUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )

        }

        if (therapistViewModel == null) {
            therapistViewModel = kmpViewModel(
                factory = viewModelFactory {
                    TherapistViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        if (appointmentResourceListEnvelopeViewModel == null) {
            appointmentResourceListEnvelopeViewModel = kmpViewModel(
                factory = viewModelFactory {
                    TherapistAppointmentResourceListEnvelopeViewModel(savedStateHandle = createSavedStateHandle())
                })

            val therapistInfo = mainViewModel!!.currentUserInfo.value
            therapistPresenter.getTherapistAppointments(therapistInfo.userId!!)
        }

        val handler = TherapistHandler(therapistPresenter,
             uiStateViewModel = uiStateViewModel!!,
             actionUiStateViewModel!!,
            onReviewsReady = {
                therapistViewModel!!.setTherapistReviews(it)
             },
             onMeetingTokenReady = {},
            appointmentResourceListEnvelopeViewModel!!)
        handler.init()

        val therapistInfo = mainViewModel!!.currentUserInfo.value
        val actionState = actionUiStateViewModel!!.therapistDashboardUiState.collectAsState()


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce)

        if (actionState.value.isLoading){
            //Content Loading
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog(actionState.value.loadingMessage)
            }
        }
        else if (actionState.value.isSuccess){
            appointmentResourceListEnvelopeViewModel!!.clearData(mutableListOf())
            actionUiStateViewModel!!.switchActionUIState(ActionUIStates(isDefault = true))
            therapistPresenter.getTherapistAppointments(therapistInfo.userId!!)
        }


        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                TherapistDashboardTopBar(mainViewModel!!, appointmentResourceListEnvelopeViewModel!!)
            },
            content = {
                 TabScreen()
            },
            backgroundColor = Color.White,
            floatingActionButton = {}
        )
    }


    @Composable
    fun TabScreen() {
        val tabItems: ArrayList<String> = arrayListOf()
        tabItems.add("Appointments")
        tabItems.add("Reviews")
        var tabIndex by remember { mutableStateOf(0) }
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            ScrollableTabRow(selectedTabIndex = tabIndex,
                modifier = Modifier.height(40.dp),
                backgroundColor = Color.Transparent,
                indicator = { tabPositions ->
                    Box(modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabIndex])
                            .height(3.dp)
                            .padding(start = 30.dp, end = 30.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Colors.primaryColor))
                     },
                divider = {}) {
                tabItems.forEachIndexed { index, title ->
                    Tab(text =
                    {
                        TextComponent(
                            text = tabItems[index],
                            fontSize = 16,
                            fontFamily = GGSansSemiBold,
                            textStyle = MaterialTheme.typography.h6,
                            textColor = Colors.darkPrimary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 30
                        )
                    }, selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                        }

                    )
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                when(tabIndex){
                    0 -> TherapistAppointment(mainViewModel!!, uiStateViewModel!!, appointmentResourceListEnvelopeViewModel, therapistPresenter, actionUiStateViewModel!!)
                    1 -> TherapistReviews(mainViewModel!!, therapistPresenter, therapistViewModel!!, uiStateViewModel!!)
                }

            }
        }
    }


}