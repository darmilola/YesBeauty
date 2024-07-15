package domain.appointments

import com.badoo.reaktive.single.Single
import domain.Models.Appointment
import domain.Models.AppointmentListDataResponse
import domain.Models.JoinMeetingResponse
import domain.Models.ServerResponse
import domain.Models.TherapistAvailabilityResponse

interface AppointmentRepository {
    suspend fun getAppointments(userId: Long, nextPage: Int = 1): Single<AppointmentListDataResponse>
    suspend fun postponeAppointment(appointment: Appointment, appointmentTime: Int,  day: Int, month: Int, year: Int): Single<ServerResponse>
    suspend fun deleteAppointment(appointmentId: Long): Single<ServerResponse>
    suspend fun joinMeeting(customParticipantId: String, presetName: String, meetingId: String): Single<JoinMeetingResponse>
    suspend fun getTherapistAvailability(therapistId: Int,vendorId: Long,day: Int, month: Int, year: Int): Single<TherapistAvailabilityResponse>
}