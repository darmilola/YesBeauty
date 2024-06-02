package presentation.appointments

import domain.Models.Appointment
import domain.Models.AppointmentResourceListEnvelope
import domain.Models.AvailableTime
import domain.Models.TimeOffs
import presentation.viewmodels.AppointmentResourceListEnvelopeViewModel
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.PostponementViewModel
import presentation.viewmodels.UIStateViewModel
import presentation.viewmodels.UIStates


class AppointmentsHandler(
    private val appointmentResourceListEnvelopeViewModel: AppointmentResourceListEnvelopeViewModel,
    private val uiStateViewModel: UIStateViewModel,
    private val postponementViewModel: PostponementViewModel,
    private val appointmentPresenter: AppointmentPresenter,
    private val onPostponeAppointment: () -> Unit,
    private val onPostponeDone: () -> Unit,
    private val onPostponeFailed: () -> Unit,
    private val onDeleteStarted: () -> Unit,
    private val onDeleteSuccess: () -> Unit,
    private val onDeleteFailed: () -> Unit) : AppointmentContract.View {
    fun init() {
        appointmentPresenter.registerUIContract(this)
    }

    override fun showLce(uiState: UIStates, message: String) {
        uiStateViewModel.switchState(uiState)
    }

    override fun showAsyncLce(uiState: AsyncUIStates, message: String) {
        postponementViewModel.setPostponementViewUIState(uiState)
    }

    override fun showAppointments(appointments: AppointmentResourceListEnvelope) {
        if (appointmentResourceListEnvelopeViewModel.resources.value.isNotEmpty()) {
            val appointmentList = appointmentResourceListEnvelopeViewModel.resources.value
            appointmentList.addAll(appointments.resources!!)
            appointmentResourceListEnvelopeViewModel.setResources(appointmentList)
            appointments.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        } else {
            appointmentResourceListEnvelopeViewModel.setResources(appointments.resources)
            appointments?.prevPageUrl?.let { appointmentResourceListEnvelopeViewModel.setPrevPageUrl(it) }
            appointments?.nextPageUrl?.let { appointmentResourceListEnvelopeViewModel.setNextPageUrl(it) }
            appointments?.currentPage?.let { appointmentResourceListEnvelopeViewModel.setCurrentPage(it) }
            appointments?.totalItemCount?.let { appointmentResourceListEnvelopeViewModel.setTotalItemCount(it) }
            appointments?.displayedItemCount?.let { appointmentResourceListEnvelopeViewModel.setDisplayedItemCount(it) }
        }
    }

    override fun showTherapistAvailability(
        availableTimes: List<AvailableTime>,
        bookedAppointment: List<Appointment>,
        timeOffs: List<TimeOffs>
    ) {
        postponementViewModel.setTherapistAvailableTimes(availableTimes)
        postponementViewModel.setTherapistTimeOffs(timeOffs)
        postponementViewModel.setTherapistBookedAppointment(bookedAppointment)
    }


    override fun onLoadMoreAppointmentStarted(isSuccess: Boolean) {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(true)
    }

    override fun onLoadMoreAppointmentEnded(isSuccess: Boolean) {
        appointmentResourceListEnvelopeViewModel.setLoadingMore(false)
    }

    override fun onPostponeAppointmentStarted() {
        onPostponeAppointment()
    }

    override fun onPostponeAppointmentSuccess() {
        onPostponeDone()
    }

    override fun onPostponeAppointmentFailed() {
        onPostponeFailed()
    }

    override fun onDeleteAppointmentStarted() {
        onDeleteStarted()
    }

    override fun onDeleteAppointmentSuccess() {
        onDeleteSuccess()
    }

    override fun onDeleteAppointmentFailed() {
        onDeleteFailed()
    }

}