package presentation.appointments

import com.badoo.reaktive.single.subscribe
import domain.Models.Appointment
import domain.appointments.AppointmentRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Enums.ServerResponseEnum
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.UserAppointment
import domain.Models.Vendor

class AppointmentPresenter(apiService: HttpClient): AppointmentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AppointmentContract.View? = null
    private val appointmentRepositoryImpl: AppointmentRepositoryImpl = AppointmentRepositoryImpl(apiService)
    override fun registerUIContract(view: AppointmentContract.View?) {
        contractView = view
    }

    override fun getUserAppointments(userId: Long) {
        contractView?.showLce(ScreenUIStates(loadingVisible = true))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponseEnum.SUCCESS.toPath() -> {
                                        contractView?.showLce(ScreenUIStates(contentVisible = true))
                                        contractView?.showAppointments(result.listItem)
                                    }
                                    ServerResponseEnum.EMPTY.toPath() -> {
                                        contractView?.showLce(ScreenUIStates(emptyContent = true))
                                    }
                                    else -> {
                                        contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                                    }
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showLce(ScreenUIStates(errorOccurred = true, errorMessage = "Error Occurred Please Try Again"))
            }
        }
    }

    override fun getMoreAppointments(userId: Long, nextPage: Int) {
        contractView?.onLoadMoreAppointmentStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getAppointments(userId, nextPage)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.onLoadMoreAppointmentEnded()
                                    contractView?.showAppointments(result.listItem)
                                }
                                else{
                                    contractView?.onLoadMoreAppointmentEnded()
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.onLoadMoreAppointmentEnded() }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onLoadMoreAppointmentEnded()
            }
        }
    }
    override fun postponeAppointment(
        userAppointment: UserAppointment,
        newAppointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        vendor: Vendor, user: User, monthName: String, platformNavigator: PlatformNavigator, platformTime: PlatformTime) {
        contractView?.showPostponeActionLce(ActionUIStates(isLoading = true, loadingMessage = "Postponing Your Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.postponeAppointment(userAppointment, newAppointmentTime, day,month,year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    val time = if (platformTime.isAm) platformTime.time+"AM" else platformTime.time+"PM"
                                    contractView?.showPostponeActionLce(ActionUIStates(isSuccess = true, successMessage = "Appointment Postponed"))
                                    platformNavigator.sendPostponedAppointmentNotification(customerName = user.firstname!!, vendorLogoUrl = vendor.businessLogo!!, businessName = vendor.businessName!!, appointmentDay = day.toString(), appointmentMonth = monthName, appointmentYear = year.toString(),
                                        appointmentTime = time, fcmToken = vendor.fcmToken!!, serviceType = userAppointment.resources?.serviceTypeItem!!.title)
                                }
                                else{
                                    contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                println("Error 2 ${it.message}")
                                contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showPostponeActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Postponing Appointment Please Try Again"))
            }
        }
    }

    override fun deleteAppointment(appointmentId: Long) {
        contractView?.showDeleteActionLce(ActionUIStates(isLoading = true, loadingMessage = "Deleting Appointment"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.deleteAppointment(appointmentId)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showDeleteActionLce(ActionUIStates(isSuccess = true, successMessage = "Appointment Deleted"))
                                }
                                else{
                                    contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showDeleteActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Deleting Appointment Please Try Again"))
            }
        }
    }

    override fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String) {
        contractView?.showJoinMeetingActionLce(ActionUIStates(isLoading = true, loadingMessage = "Joining Meeting"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.joinMeeting(customParticipantId, presetName, meetingId)
                        .subscribe(
                            onSuccess = { result ->
                                println("Error 0 $result")
                                if (result.status == "success"){
                                    contractView?.showJoinMeetingActionLce(ActionUIStates(isSuccess = true, successMessage = "Meeting Ready to be joined"))
                                    contractView?.onJoinMeetingTokenReady(result.token)
                                }
                                else{
                                    contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.showJoinMeetingActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Joining Meeting please try again"))
            }
        }
    }

    override fun getTherapistAvailability(therapistId: Int, vendorId: Long, day: Int, month: Int, year: Int) {
        contractView?.showGetAvailabilityActionLce(ActionUIStates(isLoading = true, loadingMessage = "Getting Availability"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    appointmentRepositoryImpl.getTherapistAvailability(therapistId,vendorId, day, month, year)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showTherapistAvailability(result.bookedAppointment, result.platformTimes, result.vendorTimes)
                                    contractView?.showGetAvailabilityActionLce(ActionUIStates(isSuccess = true, successMessage = "Availability Ready"))
                                }
                                else{
                                    contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                                }
                            },
                            onError = {
                                contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showGetAvailabilityActionLce(ActionUIStates(isFailed = true, errorMessage = "Error Fetching Availability, Please Try Again"))
            }
        }
    }

}