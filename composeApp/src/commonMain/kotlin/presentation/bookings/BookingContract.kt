package presentation.bookings

import domain.Models.ServiceTypeSpecialist
import domain.Models.UnsavedAppointment
import domain.Models.User
import domain.Models.Vendor
import presentation.viewmodels.ActionUIStates
import presentation.viewmodels.ScreenUIStates

class BookingContract {
    interface View {
        fun showScreenLce(uiState: ScreenUIStates, message: String = "")
        fun showActionLce(uiState: ActionUIStates, message: String = "")
        fun showTherapists(serviceSpecialists: List<ServiceTypeSpecialist>)
        fun showUnsavedAppointment()
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getUnSavedAppointment()
        abstract fun getServiceTherapists(serviceTypeId: Int, day: Int, month: Int, year: Int)
        abstract fun createAppointment(unsavedAppointments: ArrayList<UnsavedAppointment>, currentUser: User, currentVendor: Vendor)
    }
}