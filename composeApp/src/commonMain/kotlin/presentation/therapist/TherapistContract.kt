package presentation.therapist

import domain.Models.Appointment
import domain.Models.ResourceListEnvelope
import domain.Models.ServiceTime
import domain.Models.SpecialistReviews
import presentation.viewmodels.AsyncUIStates
import presentation.viewmodels.UIStates

interface TherapistContract {
    interface View {
        fun showLce(uiState: UIStates, message: String = "")
        fun showAsyncLce(uiState: AsyncUIStates, message: String = "")
        fun showReviews(reviews: List<SpecialistReviews>)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun getTherapistReviews(specialistId: Int)
    }
}
