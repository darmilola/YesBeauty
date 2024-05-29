package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable
import com.hoc081098.kmp.viewmodel.parcelable.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable @Parcelize
data class ServiceImages (@SerialName("id") val id: Int?, @SerialName("service_id") val serviceId: Int?, @SerialName("imageUrl") val imageUrl: String? = ""): Parcelable
