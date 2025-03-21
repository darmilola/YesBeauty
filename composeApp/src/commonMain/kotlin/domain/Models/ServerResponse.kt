package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import domain.Enums.ProfileStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServerResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "")

@Serializable
data class JoinMeetingResponse(@SerialName("status") val status: String = "", @SerialName("token") val token: String = "")

@Serializable
data class AuthenticationResponse(@SerialName("status") val status: String = "", @SerialName("data") val userInfo: User = User(),
                                  @SerialName("profile_Status") val profileStatus: String = ProfileStatus.COMPLETE_PROFILE.toPath())

@Serializable
data class CompleteProfileResponse(@SerialName("status") val status: String = "", @SerialName("userInfo") val userInfo: User = User())

@Serializable
data class HomePageResponse(@SerialName("status") val status: String = "", @SerialName("homePage") val homepageInfo: HomepageInfo = HomepageInfo())

@Serializable @Parcelize
data class CountryStatesResponse(@SerialName("status") val status: String = "",
                                 @SerialName("states") val states: ArrayList<State>): Parcelable

@Serializable
data class ServiceTherapistsResponse(@SerialName("status") val status: String = "", @SerialName("therapists") val serviceTherapists: List<ServiceTypeTherapists> = arrayListOf(),
                                     @SerialName("platformTime") val platformTimes: List<PlatformTime>? = null, @SerialName("vendorTime") val vendorTimes: List<VendorTime>? = null)

@Serializable
data class TimeAvailabilityResponse(@SerialName("status") val status: String = "", @SerialName("platformTime") val platformTimes: List<PlatformTime>? = null, @SerialName("vendorTime") val vendorTimes: List<VendorTime>? = null)

@Serializable @Parcelize
data class ServiceTypesResponse(@SerialName("status") val status: String = "",
                                @SerialName("serviceTypes") val serviceTypes: List<ServiceTypeItem>? = null,
                                @SerialName("serviceImages") val serviceImages: List<ServiceImages>? = null): Parcelable

@Serializable
data class TherapistReviewsResponse(@SerialName("status") val status: String = "", @SerialName("reviews") val reviews: List<AppointmentReview>)


@Serializable
data class TherapistAvailabilityResponse(@SerialName("status") val status: String = "",
                                         @SerialName("bookedTimes") val bookedAppointment: List<Appointment>,
                                         @SerialName("platformTime") val platformTimes: List<PlatformTime>,
                                         @SerialName("vendorTime") val vendorTimes: List<VendorTime>)

@Serializable
data class VendorAccountResponse(@SerialName("status") val status: String = "", @SerialName("vendorInfo") val vendorInfo: Vendor)

@Serializable
class InitCheckoutResponse(@SerialName("status") var status: String, @SerialName("result")  var authorizationResultJsonString: String)

@Serializable
data class VendorAvailabilityResponse(@SerialName("status") val status: String = "",
                                          @SerialName("vendorTimes") val vendorTimes: List<VendorTime>,
                                          @SerialName("platformTimes") val platformTimes: List<PlatformTime>)

@Serializable
data class PendingBookingAppointmentResponse(@SerialName("status") val status: String = "", @SerialName("message") val message: String = "",
                                             @SerialName("appointments") val appointments: List<UserAppointment>? = null)

@Serializable
data class FavoriteProductResponse(@SerialName("status") val status: String = "",
                                      @SerialName("items") val favoriteProductItems: List<FavoriteProductModel> = arrayListOf())

@Serializable
data class FavoriteProductIdResponse(@SerialName("status") val status: String = "",
                                      @SerialName("items") val favoriteProductIds: List<FavoriteProductIdModel>)

@Serializable
data class ViewVendorsResponse(@SerialName("status") val status: String = "",
                               @SerialName("nearbyVendor") val nearbyVendors: List<Vendor>,
                               @SerialName("newVendor") val newVendors: List<Vendor> )