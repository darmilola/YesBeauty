package domain.bookings

import com.badoo.reaktive.single.Single
import domain.Models.PendingBookingAppointmentResponse
import domain.Models.ServerResponse
import domain.Models.ServiceTherapistsResponse
import domain.Models.ServiceTypesResponse
import io.ktor.client.HttpClient

class BookingRepositoryImpl(apiService: HttpClient): BookingRepository {

    private val bookingNetworkService: BookingNetworkService = BookingNetworkService(apiService)

    override suspend fun getServiceTherapist(serviceTypeId: Long, vendorId: Long, day: Int, month: Int, year: Int): Single<ServiceTherapistsResponse> {
        val param = GetTherapistsRequest(serviceTypeId, vendorId, day, month, year)
        return bookingNetworkService.getTherapists(param)
    }

    override suspend fun getMobileServiceTherapist(
        serviceTypeId: Long,
        vendorId: Long,
        day: Int,
        month: Int,
        year: Int
    ): Single<ServiceTherapistsResponse> {
        val param = GetTherapistsRequest(serviceTypeId, vendorId, day, month, year)
        return bookingNetworkService.getMobileTherapists(param)
    }

    override suspend fun getServiceData(serviceId: Long): Single<ServiceTypesResponse> {
        val param = GetServiceDataRequest(serviceId)
        return bookingNetworkService.getServiceData(param)
    }

    override suspend fun createPendingBookingAppointment(userId: Long, vendorId: Long, serviceId: Long, serviceTypeId: Long, therapistId: Long,
                                                          appointmentTime: Int, day: Int, month: Int, year: Int, serviceLocation: String,
                                                          bookingStatus: String, appointmentType: String): Single<PendingBookingAppointmentResponse> {
        val param = CreatePendingBookingAppointmentRequest(userId, vendorId, serviceId, serviceTypeId, therapistId,
            appointmentTime, day, month, year, serviceLocation, bookingStatus, appointmentType)
        return bookingNetworkService.createPendingBookingAppointment(param)
    }

    override suspend fun createPendingPackageBookingAppointment(
        userId: Long,
        vendorId: Long,
        packageId: Long,
        appointmentTime: Int,
        day: Int,
        month: Int,
        year: Int,
        serviceLocation: String,
        bookingStatus: String,
        appointmentType: String
    ): Single<PendingBookingAppointmentResponse> {
        val param = CreatePendingBookingPackageAppointmentRequest(userId, vendorId, packageId, appointmentTime, day, month, year, serviceLocation, bookingStatus, appointmentType)
        return bookingNetworkService.createPendingBookingPackageAppointment(param)
    }

    override suspend fun createAppointment(
        userId: Long,
        vendorId: Long,
        paymentAmount: Int,
        bookingStatus: String,
        day: Int,
        month: Int,
        year: Int,
    ): Single<ServerResponse> {
        val param = CreateAppointmentRequest(userId, vendorId, day, month, year, bookingStatus, paymentAmount)
        return bookingNetworkService.createAppointment(param)
    }

    override suspend fun getPendingBookingAppointment(userId: Long, bookingStatus: String): Single<PendingBookingAppointmentResponse> {
        val param = GetPendingBookingAppointmentRequest(userId,bookingStatus)
        return bookingNetworkService.getPendingBookingAppointment(param)
    }

    override suspend fun deletePendingBookingAppointment(pendingAppointmentId: Long): Single<ServerResponse> {
        val param = DeletePendingBookingAppointmentRequest(pendingAppointmentId)
        return bookingNetworkService.deletePendingBookingAppointment(param)
    }

    override suspend fun deleteAllPendingAppointment(userId: Long, bookingStatus: String): Single<ServerResponse> {
        val param = DeleteAllPendingBookingAppointmentRequest(userId, bookingStatus)
        return bookingNetworkService.deleteAllPendingBookingAppointment(param)
    }

}