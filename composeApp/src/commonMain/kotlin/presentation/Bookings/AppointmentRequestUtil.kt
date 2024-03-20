package presentation.Bookings

import androidx.compose.runtime.snapshots.SnapshotStateList
import domain.Models.ServiceLocation
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import domain.bookings.CreateAppointmentRequest
import kotlinx.serialization.SerialName
import presentation.viewmodels.MainViewModel

fun getUnSavedAppointment(unsavedAppointments: SnapshotStateList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor): ArrayList<CreateAppointmentRequest> {
    val appointmentRequestList = arrayListOf<CreateAppointmentRequest>()

    for (item in unsavedAppointments){
        val request = CreateAppointmentRequest(userId = currentUser.userId!!, vendorId = currentVendor.vendorId!!, serviceId = item.serviceId,
            serviceTypeId = item.serviceTypeId!!, specialistId = item.serviceTypeSpecialist?.specialistId!!, recommendationId = item.recommendationId,
            appointmentTime = item.appointmentTime?.id!!, appointmentDate = item.appointmentDate.toString(), serviceLocation = if (item.isHomeService) ServiceLocation.Home.toPath() else ServiceLocation.Spa.toPath(),
            serviceStatus = item.serviceStatus,
            isRecommendedAppointment = item.isRecommendedAppointment)
        appointmentRequestList.add(request)
    }

    return appointmentRequestList
}