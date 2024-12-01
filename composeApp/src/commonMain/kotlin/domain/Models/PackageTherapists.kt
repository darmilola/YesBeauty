package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PackageTherapists(@SerialName("id") val id: Long = -1, @SerialName("package_id") val packageId: Long = -1,
                             @SerialName("therapist_id") val therapistId: Long = -1, @SerialName("therapist_profile_info") val therapistInfo: TherapistInfo = TherapistInfo(),
                             var isSelected: Boolean = false): Parcelable