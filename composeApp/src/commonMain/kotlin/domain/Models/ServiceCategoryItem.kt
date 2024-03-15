package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceCategoryItem(@SerialName("id") val categoryId: Int = -1, @SerialName("vendor_id") val vendorId: Int = -1, @SerialName("service_id") val serviceId: Int = -1, @SerialName("title") val title: String = "",
                               @SerialName("price") val price: Int = 0, @SerialName("homeServicePrice") val homeServicePrice: Int = 0, @SerialName("description") val description: String = "",
                               @SerialName("homeServiceAvailable") val homeServiceAvailable: Boolean = false, @SerialName("timeLength") val timeLength: Int = 0, @SerialName("isAvailable") val isAvailable: Boolean  = false, @SerialName("appointments")  val appointments: Int  = 0,
                               @SerialName("specialists")  var specialists: ArrayList<ServiceTypeSpecialist>? = arrayListOf(), val isSelected: Boolean = false)



data class ServiceCategoryUIModel(
    val selectedCategory: ServiceCategoryItem?,
    val vendorServiceCategories: List<ServiceCategoryItem>)