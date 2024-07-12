package presentation.appointments

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import domain.Models.PlatformTime
import domain.Models.VendorTime

interface AppointmentContract {
    interface View {
        fun showLce(screenUiState: ScreenUIStates)
        fun showDeleteActionLce(actionUIStates: ActionUIStates)
        fun showPostponeActionLce(actionUIStates: ActionUIStates)
        fun showJoinMeetingActionLce(actionUIStates: ActionUIStates)
        fun showGetAvailabilityActionLce(actionUIStates: ActionUIStates)
        fun showAppointments(appointments: AppointmentResourceListEnvelope)
        fun showTherapistAvailability(bookedAppointment: List<Appointment>,platformTime: List<PlatformTime>,
                                      vendorTime: List<VendorTime>)
        fun onLoadMoreAppointmentStarted()
        fun onLoadMoreAppointmentEnded()
        fun onJoinMeetingTokenReady(meetingToken: String)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUserAppointments(userId: Long)
        abstract fun getMoreAppointments(userId: Long, nextPage: Int = 1)
        abstract fun postponeAppointment(appointment: Appointment, newAppointmentTime: Int,  day: Int, month: Int, year: Int)
        abstract fun deleteAppointment(appointmentId: Int)
        abstract fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String)
        abstract fun getTherapistAvailability(therapistId: Int, vendorId: Long, day: Int, month: Int, year: Int)
    }
}
