package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class HomepageInfo (
    @SerialName("user_info") val userInfo: User? = null,
    @SerialName("vendor_info") val vendorInfo: Vendor? = null,
    @SerialName("specialist_info") val specialistInfo: SpecialistInfo? = null,
    @SerialName("vendor_services") val vendorServices: ArrayList<Services>? = null,
    @SerialName("vendor_recommendations") val recommendationRecommendations: ArrayList<VendorRecommendation>? = null,
    @SerialName("recent_appointments") val recentAppointment: ArrayList<Appointment>? = null,
    @SerialName("popular_products") var popularProducts: ArrayList<Product>? = null): Parcelable
