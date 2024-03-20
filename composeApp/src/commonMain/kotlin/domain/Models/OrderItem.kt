package domain.Models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem(@SerialName("orderReference") var orderReference: Int? = -1,
                     @SerialName("product_id") var productId: Int = -1, @SerialName("itemCount") var itemCount: Int = -1,
                     @SerialName("product") var itemProduct: Product? = null, val isSelected: Boolean = false)