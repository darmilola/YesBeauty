package presentation.bookings

import StackedSnackbarHost
import StackedSnakbarHostState
import theme.styles.Colors
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import domain.Enums.Screens
import domain.Models.Services
import presentation.components.ButtonComponent
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.BookingScreenHandler
import presentation.dialogs.ErrorDialog
import presentation.dialogs.LoadingDialog
import presentation.dialogs.SuccessDialog
import presentation.viewmodels.BookingViewModel
import presentation.viewmodels.MainViewModel
import presentation.viewmodels.ScreenUIStateViewModel
import UIStates.ScreenUIStates
import presentation.widgets.ShowSnackBar
import presentation.widgets.SnackBarType
import rememberStackedSnackbarHostState


class BookingScreen(private val mainViewModel: MainViewModel) : Tab, KoinComponent {
    private val bookingPresenter: BookingPresenter by inject()
    private var screenUiStateViewModel: ScreenUIStateViewModel? = null
    private var bookingViewModel: BookingViewModel? = null
    override val options: TabOptions
        @Composable
        get() {
            val title = "Bookings"

            return remember {
                TabOptions(
                    index = 0u,
                    title = title
                )
            }
        }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val pagerState = rememberPagerState(pageCount = { 3 })
        val addMoreService = remember { mutableStateOf(false) }
        val lastItemRemoved = remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        val creatingAppointmentProgress = remember { mutableStateOf(false) }
        val creatingAppointmentSuccess = remember { mutableStateOf(false) }
        val creatingAppointmentFailed = remember { mutableStateOf(false) }

        if (screenUiStateViewModel == null) {
            screenUiStateViewModel = kmpViewModel(
                factory = viewModelFactory {
                    ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
                },
            )
        }

    if(bookingViewModel == null) {
        bookingViewModel = kmpViewModel(
            factory = viewModelFactory {
                BookingViewModel(savedStateHandle = createSavedStateHandle())
            },
        )
    }


        val handler = BookingScreenHandler(
            bookingViewModel!!, bookingPresenter,
           onPageLoading = {
               screenUiStateViewModel!!.switchScreenUIState(ScreenUIStates(loadingVisible = true))
            },
            onShowUnsavedAppointment = {},
            onContentVisible = {
                screenUiStateViewModel!!.switchScreenUIState(ScreenUIStates(contentVisible = true))
            }, onErrorVisible = {
                screenUiStateViewModel!!.switchScreenUIState(ScreenUIStates(errorOccurred = true))
            },
            onCreateAppointmentStarted = {
                creatingAppointmentProgress.value = true
            },
            onCreateAppointmentSuccess = {
                creatingAppointmentProgress.value = false
                creatingAppointmentSuccess.value = true
            },
            onCreateAppointmentFailed = {
                creatingAppointmentProgress.value = false
                creatingAppointmentFailed.value = true
            })
        handler.init()


        if (creatingAppointmentProgress.value) {
            Box(modifier = Modifier.fillMaxWidth(0.90f)) {
                LoadingDialog("Creating Appointment...")
            }
        }
        else if (creatingAppointmentSuccess.value){
            Box(modifier = Modifier.fillMaxWidth()) {
                SuccessDialog("Creating Appointment Successful", actionTitle = "Done", onConfirmation = {
                    bookingViewModel!!.clearCurrentBooking()
                    mainViewModel.clearVendorRecommendation()
                    mainViewModel.clearUnsavedAppointments()
                    coroutineScope.launch {
                        pagerState.scrollToPage(0)
                        mainViewModel.setScreenNav(
                            Pair(
                                Screens.BOOKING.toPath(),
                                Screens.MAIN_TAB.toPath()
                            )
                        )
                    }
                })
            }
        }
        else if (creatingAppointmentFailed.value){
            Box(modifier = Modifier.fillMaxWidth()) {
                ErrorDialog("Creating Appointment Failed", actionTitle = "Retry", onConfirmation = {
                    bookingPresenter.createAppointment(mainViewModel.unSavedAppointments.value, mainViewModel.currentUserInfo.value, mainViewModel.connectedVendor.value)
                })
            }
        }


        val stackedSnackBarHostState = rememberStackedSnackbarHostState(
            maxStack = 5,
            animation = StackedSnackbarAnimation.Bounce
        )

        if (addMoreService.value){
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                bookingViewModel!!.setCurrentBookingId(-1)
                mainViewModel.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.MAIN_TAB.toPath()))
            }
        }
        if (lastItemRemoved.value){
            rememberCoroutineScope().launch {
                pagerState.scrollToPage(0)
                bookingViewModel!!.setCurrentBookingId(-1)
                mainViewModel.clearUnsavedAppointments()
                mainViewModel.setScreenNav(Pair(Screens.BOOKING.toPath(), Screens.MAIN_TAB.toPath()))
            }
        }

        Scaffold(
            snackbarHost = { StackedSnackbarHost(hostState = stackedSnackBarHostState)  }
        ) {

            val layoutModifier =
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = Color.White)
            Column(modifier = layoutModifier) {

                BookingScreenTopBar(pagerState, mainViewModel, bookingViewModel!!)

                val bgStyle = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .fillMaxHeight()

                Box(
                    contentAlignment = Alignment.TopCenter, modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(color = Color.White)
                ) {

                    Column(
                        modifier = bgStyle
                    ) {
                        AttachBookingPages(
                            pagerState,
                            screenUiStateViewModel!!,
                            mainViewModel,
                            bookingViewModel!!,
                            services = mainViewModel.selectedService.value,
                            onAddMoreServiceClicked = {
                                addMoreService.value = true
                            },
                            onLastItemRemoved = {
                                lastItemRemoved.value = true
                            }
                        )
                        AttachActionButtons(pagerState, mainViewModel, stackedSnackBarHostState, bookingPresenter)
                    }
                }

            }
        }

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachActionButtons(pagerState: PagerState, mainViewModel: MainViewModel, stackedSnackBarHostState: StackedSnakbarHostState, bookingPresenter: BookingPresenter){

        var btnFraction by remember { mutableStateOf(0f) }
        val currentPage = pagerState.currentPage


        btnFraction = if (pagerState.currentPage == 1){
            0.5f
        } else {
            0f
        }


        val coroutineScope = rememberCoroutineScope()

        val buttonStyle = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .fillMaxWidth()
            .clip(CircleShape)
            .height(45.dp)


        Row (modifier = Modifier
            .padding(bottom = 10.dp,start = 10.dp, end = 10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,) {

            val bookingNavText = if(currentPage == 2) "Go To Payments" else "Continue"

            ButtonComponent(modifier = buttonStyle, buttonText = bookingNavText,
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.primaryColor),
                fontSize = 16, shape = RoundedCornerShape(10.dp),
                textColor = Color(color = 0xFFFFFFFF), style = MaterialTheme.typography.h6, borderStroke = null) {

                if (currentPage == 2){
                    bookingPresenter.createAppointment(mainViewModel.unSavedAppointments.value, mainViewModel.currentUserInfo.value, mainViewModel.connectedVendor.value)
                }
                else {

                    coroutineScope.launch {
                        if (currentPage == 0) {
                            if (bookingViewModel?.selectedServiceType?.value?.serviceId == -1) {
                                ShowSnackBar(title = "No Service Selected",
                                    description = "Please Select a Service to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else {
                                pagerState.animateScrollToPage(1)
                            }
                        } else if (currentPage == 1) {
                            if (bookingViewModel?.currentAppointmentBooking?.value?.serviceTypeTherapists == null) {
                                ShowSnackBar(title = "No Therapist Selected",
                                    description = "Please Select a Therapist to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else if (bookingViewModel?.currentAppointmentBooking?.value?.appointmentTime == null) {
                                ShowSnackBar(title = "No Time Selected",
                                    description = "Please Select Appointment Time to proceed",
                                    actionLabel = "",
                                    duration = StackedSnackbarDuration.Short,
                                    snackBarType = SnackBarType.ERROR,
                                    stackedSnackBarHostState,
                                    onActionClick = {})
                            } else {
                                pagerState.animateScrollToPage(2)
                            }
                        }
                    }
                }
            }
        }

    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun AttachBookingPages(pagerState: PagerState, screenUiStateViewModel: ScreenUIStateViewModel, mainViewModel: MainViewModel, bookingViewModel: BookingViewModel, services: Services, onAddMoreServiceClicked:() -> Unit, onLastItemRemoved:() -> Unit){

        val  boxModifier =
            Modifier
                .padding(top = 5.dp)
                .background(color = Color.White)
                .fillMaxHeight(0.90f)
                .fillMaxWidth()

        // AnimationEffect
        Box(contentAlignment = Alignment.BottomCenter, modifier = boxModifier) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> BookingSelectServices(mainViewModel, bookingViewModel,services)
                    1 -> if(page == pagerState.targetPage) {
                        BookingSelectTherapists(mainViewModel,screenUiStateViewModel,bookingViewModel,bookingPresenter)
                    }
                    2 -> if(page == pagerState.targetPage) {
                        BookingOverview(
                            mainViewModel,
                            screenUiStateViewModel,
                            bookingViewModel,
                            bookingPresenter,
                            onAddMoreServiceClicked = {
                                onAddMoreServiceClicked()
                            },
                            onLastItemRemoved = {
                                onLastItemRemoved()
                            })
                    }
                }
            }

        }

    }
}

