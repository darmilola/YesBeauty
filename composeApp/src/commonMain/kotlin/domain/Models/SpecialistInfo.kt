package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpecialistInfo(@SerialName("id") val id: Int? = null, @SerialName("specialist_id") val specialistId: Int? = null,
                          @SerialName("rating") val rating: Double? = null, @SerialName("vendor_id") val vendorId: Int? = null,
                          @SerialName("isAvailable") val isAvailable: Boolean? = null, @SerialName("isAvailableForHomeService") val isAvailableForHomeService: Boolean? = false,
                          @SerialName("rateCount") val rateCount: Int? = null, @SerialName("profile_info") val profileInfo: User? = null,
                          @SerialName("booked_times") val bookedTimes: List<BookedTimes>? = arrayListOf(),
                          @SerialName("specialist_reviews") val specialistReviews: List<SpecialistReviews>? = arrayListOf(), val isSelected: Boolean = false)


data class TherapistUIModel(
    var selectedTherapistMap: HashMap<Int, SpecialistInfo>?,
    var salonTherapist: List<SpecialistInfo>
)
