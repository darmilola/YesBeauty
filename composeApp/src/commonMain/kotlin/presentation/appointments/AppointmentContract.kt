package presentation.appointments

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import UIStates.AppUIStates
import domain.Models.PlatformNavigator
import domain.Models.PlatformTime
import domain.Models.User
import domain.Models.UserAppointment
import domain.Models.Vendor
import domain.Models.VendorTime

interface AppointmentContract {
    interface View {
        fun showLce(screenUiState: AppUIStates)
        fun showRefreshing(screenUiState: AppUIStates)
        fun showDeleteActionLce(appUIStates: AppUIStates)
        fun showPostponeActionLce(appUIStates: AppUIStates)
        fun showJoinMeetingActionLce(appUIStates: AppUIStates)
        fun showGetAvailabilityActionLce(appUIStates: AppUIStates)
        fun showAppointments(appointments: AppointmentResourceListEnvelope, isRefresh: Boolean)
        fun showTherapistAvailability(bookedAppointment: List<Appointment>,platformTime: List<PlatformTime>,
                                      vendorTime: List<VendorTime>)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserAppointments(userId: Long)
        abstract fun refreshUserAppointments(userId: Long)
        abstract fun getMoreAppointments(userId: Long, nextPage: Int = 1)
        abstract fun postponeAppointment(userAppointment: UserAppointment, newAppointmentTime: Int, day: Int, month: Int, year: Int, vendor: Vendor, user: User, monthName: String, platformNavigator: PlatformNavigator,
                                         platformTime: PlatformTime)
        abstract fun deleteAppointment(appointmentId: Long)
        abstract fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String)
        abstract fun getTherapistAvailability(therapistId: Int, vendorId: Long, day: Int, month: Int, year: Int)
    }
}
