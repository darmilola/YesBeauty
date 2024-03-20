package domain.Products

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.Product
import domain.Models.ProductCategoryResponse
import domain.Models.ServerResponse
import domain.Models.ServiceSpecialistsResponse
import domain.bookings.BookingNetworkService
import domain.bookings.BookingRepository
import domain.bookings.CreateAppointmentRequestArray
import domain.bookings.GetSpecialistsRequest
import io.ktor.client.HttpClient

class ProductRepositoryImpl(apiService: HttpClient): ProductRepository {
    private val productNetworkService: ProductNetworkService = ProductNetworkService(apiService)
    override suspend fun getProductCategories(
        vendorId: Int,
        userId: Int
    ): Single<ProductCategoryResponse> {
        val param = GetProductCategoryRequest(vendorId, userId)
        return productNetworkService.getProductCategories(param)
    }

    override suspend fun getProductsInCategory(
        vendorId: Int,
        categoryId: Int,
        nextPage: Int
    ): Single<ListDataResponse<Product>> {
        val param = GetProductsInCategoryRequest(vendorId, categoryId)
        return productNetworkService.getProductInCategory(param,nextPage)
    }

    override suspend fun searchProducts(
        vendorId: Int,
        searchQuery: String,
        nextPage: Int
    ): Single<ListDataResponse<Product>> {
        val param = SearchProductRequest(vendorId, searchQuery)
        return productNetworkService.searchProduct(param,nextPage)
    }

    override suspend fun createOrder(
        vendorId: Int,
        userId: Int,
        orderReference: Int,
        deliveryMethod: String,
        paymentMethod: String,
        orderItems: ArrayList<OrderItemRequest>
    ): Single<ServerResponse> {
        val param = CreateOrderRequest(vendorId, userId, orderReference, deliveryMethod, paymentMethod, orderItems)
        return productNetworkService.createOrder(param)
    }


}