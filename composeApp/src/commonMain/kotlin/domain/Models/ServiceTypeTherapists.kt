package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable @Parcelize
data class ServiceTypeTherapists(@SerialName("id") val id: Long? = null, @SerialName("therapist_id") val therapistId: Long? = null,
                                 @SerialName("service_type_id") val serviceTypeId: Long? = null, @SerialName("therapist_info") val therapistInfo: TherapistInfo? = null,
                                 val isSelected: Boolean = false): Parcelable
data class ServiceTypeTherapistUIModel(
    val selectedTherapist: ServiceTypeTherapists?,
    val visibleTherapist: List<ServiceTypeTherapists>)