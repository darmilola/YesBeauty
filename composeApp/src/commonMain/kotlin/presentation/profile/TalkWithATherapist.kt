package presentation.profile

import GGSansSemiBold
import StackedSnackbarHost
import UIStates.ActionUIStates
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.AppointmentType
import domain.Enums.Screens
import domain.Enums.ServiceStatusEnum
import domain.Models.CurrentAppointmentBooking
import domain.Models.PlatformTime
import domain.Models.VendorTime
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.components.ButtonComponent
import presentation.components.IndeterminateCircularProgressBar
import presentation.DomainViewHandler.TalkWithTherapistHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.ActionUIStateViewModel
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.widgets.BookingCalendar
import presentation.widgets.MultilineInputWidget
import presentation.widgets.PageBackNavWidget
import presentation.widgets.PostponeTimeGrid
import presentation.widgets.TitleWidget
import presentation.widgets.VendorTimeGrid
import presentations.components.TextComponent
import rememberStackedSnackbarHostState
import theme.styles.Colors
import utils.calculateTherapistServiceTimes
import utils.calculateVendorServiceTimes

class TalkWithATherapist(private val mainViewModel: MainViewModel) : Tab,
    KoinComponent {
    private val profilePresenter: ProfilePresenter by inject()
    private var actionUIStateViewModel: ActionUIStateViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Talk With A Therapist"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @Composable
    override fun Content() {

        val isLoading = remember { mutableStateOf(false) }
        val isDone = remember { mutableStateOf(false) }
        val isSuccess = remember { mutableStateOf(false) }
        val vendorTimes = remember { mutableStateOf(listOf<VendorTime>()) }
        val platformTimes = remember { mutableStateOf(listOf<PlatformTime>()) }
        val description = remember { mutableStateOf("") }
        val serviceType: String = AppointmentType.MEETING.toPath()

        if (actionUIStateViewModel == null) {
            actionUIStateViewModel= kmpViewModel(
                factory = viewModelFactory {
                    ActionUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }


        val currentBooking = CurrentAppointmentBooking()


        val handler = TalkWithTherapistHandler(profilePresenter,
            isLoading = {
                isLoading.value = true
                isSuccess.value = false
                isDone.value = false
            }, isDone = {
                isLoading.value = false
                isDone.value = true
                isSuccess.value = true
            }, isSuccess = {
                isLoading.value = false
                isSuccess.value = true
                isDone.value = true
            },
            onAvailableTimesReady = { it1, it2 ->
                vendorTimes.value = it1
                platformTimes.value = it2
            }, actionUIStateViewModel = actionUIStateViewModel!!)
        handler.init()



        val actionUIState = actionUIStateViewModel!!.uiStateInfo.collectAsState()


        if (actionUIState.value.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                LoadingDialog("Creating Appointment")
            }
        }
        else if (actionUIState.value.isSuccess) {
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Appointment Created Successfully", "Close", onConfirmation = {
                        mainViewModel!!.setScreenNav(
                            Pair(
                                Screens.BOOKING.toPath(),
                                Screens.MAIN_TAB.toPath()))
                })
            }
        }
        else if (actionUIState.value.isFailed) {
            ErrorDialog("Error Occurred", "Close", onConfirmation = {})
        }



        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 1,
            animation = StackedSnackbarAnimation.Bounce
        )

        profilePresenter.getVendorAvailability(mainViewModel.vendorId.value)

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState) },
            topBar = {
                Box(modifier = Modifier.fillMaxWidth().height(60.dp)) {
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(start = 10.dp), contentAlignment = Alignment.CenterStart) {
                        AttachBackIcon(mainViewModel)
                    }
                    Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(), contentAlignment = Alignment.Center) {
                        PageTitle()
                    }
                }
            },
            content = {

                if (isLoading.value) {
                    //Content Loading
                    Box(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight()
                            .padding(top = 40.dp, start = 50.dp, end = 50.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IndeterminateCircularProgressBar()
                    }
                } else if (isDone.value && !isSuccess.value) {

                    //Error Occurred display reload

                } else if (isDone.value && isSuccess.value) {

                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 70.dp, start = 10.dp, end = 10.dp)
                    ) {

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Select Date",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            BookingCalendar(modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(top = 10.dp)) {
                                currentBooking.day = it.dayOfMonth
                                currentBooking.month = it.monthNumber
                                currentBooking.year = it.year
                                println(" ${currentBooking.day}")
                                println(it)
                            }
                        }


                        Column(
                            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
                        ) {

                            val workHours = calculateVendorServiceTimes(platformTimes = platformTimes.value, vendorTimes = vendorTimes.value)

                            VendorTimeContent(workHours, onWorkHourClickListener = {
                                currentBooking.appointmentTime = it
                            })
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
                        ) {

                            TextComponent(
                                text = "Reason for consultation?",
                                fontSize = 16,
                                fontFamily = GGSansSemiBold,
                                textStyle = TextStyle(),
                                textColor = Colors.darkPrimary,
                                textAlign = TextAlign.Left,
                                fontWeight = FontWeight.Black,
                                lineHeight = 30,
                                textModifier = Modifier
                                    .fillMaxWidth().padding(start = 10.dp)
                            )

                            MultilineInputWidget(viewHeight = 150){
                                description.value = it
                            }
                        }
                    }
                }


            },
            bottomBar = {
                val buttonStyle = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)

                ButtonComponent(
                    modifier = buttonStyle,
                    buttonText = "Proceed",
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                    fontSize = 18,
                    shape = CircleShape,
                    textColor = Color(color = 0xFFFFFFFF),
                    style = TextStyle(),
                    borderStroke = null
                ) {

                    profilePresenter.createMeeting(meetingTitle = "Talk With Therapist", userId = mainViewModel.currentUserInfo.value.userId!!,
                        vendorId = mainViewModel.connectedVendor.value.vendorId!!, serviceStatus = ServiceStatusEnum.PENDING.toPath(),
                        appointmentType = AppointmentType.MEETING.toPath(), appointmentTime = currentBooking.appointmentTime?.id!!,
                        day = currentBooking.day, month = currentBooking.month, year = currentBooking.year, meetingDescription = description.value
                    )

                }
            })
    }

    @Composable
    private fun AttachBackIcon(mainViewModel: MainViewModel) {
        PageBackNavWidget {
            when (mainViewModel.screenNav.value.first) {
                Screens.MAIN_TAB.toPath() -> {
                    mainViewModel.setScreenNav(Pair(Screens.TALK_WITH_A_THERAPIST.toPath(), Screens.MAIN_TAB.toPath()))
                }
            }
        }
    }



    @Composable
    fun PageTitle(){
        val rowModifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = rowModifier
        ) {
            TitleWidget(title = "Talk With A Therapist", textColor = Colors.primaryColor)
        }
    }

}

@Composable
fun VendorTimeContent(availableHours: ArrayList<PlatformTime>, onWorkHourClickListener: (PlatformTime) -> Unit) {

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 10.dp, top = 15.dp, bottom = 10.dp)
            .height(250.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment  = Alignment.CenterHorizontally,
    ) {

        TextComponent(
            text = "Available Times",
            fontSize = 18,
            fontFamily = GGSansSemiBold,
            textStyle = TextStyle(),
            textColor = Colors.darkPrimary,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Black,
            lineHeight = 30,
            textModifier = Modifier
                .fillMaxWidth().padding(start = 10.dp, bottom = 20.dp))
        Row(modifier = Modifier.fillMaxWidth(0.90f).wrapContentHeight()) {
            TextComponent(
                text = "Morning",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Afternoon",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            TextComponent(
                text = "Evening",
                fontSize = 16,
                textStyle = TextStyle(),
                textColor = theme.Colors.darkPrimary,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                lineHeight = 25,
                textModifier = Modifier.weight(1f).padding(bottom = 15.dp, top = 10.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
        }

        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Column(modifier = Modifier.weight(1f).wrapContentHeight()) {
                PostponeTimeGrid(platformTimes = availableHours, onWorkHourClickListener = {
                    onWorkHourClickListener(it)
                })
            }

        }
    }
}





