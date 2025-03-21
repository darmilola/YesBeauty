package domain.connectVendor

import com.badoo.reaktive.single.Single
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import domain.Models.VendorListDataResponse
import domain.Models.ViewVendorsResponse
import io.ktor.client.HttpClient

 class ConnectVendorRepositoryImpl(apiService: HttpClient): ConnectVendorRepository {

     private val connectVendorNetworkService: ConnectVendorNetworkService = ConnectVendorNetworkService(apiService)
    override suspend fun connectVendor(userId: Long, vendorId: Long, action: String): Single<ServerResponse> {
        val param = ConnectVendorRequest(userId, vendorId, action)
        return connectVendorNetworkService.connectVendor(param)
    }

    override suspend fun getVendor(country: String, state: Long, connectedVendor: Long, nextPage: Int): Single<VendorListDataResponse> {
        val param = GetVendorRequest(country, state, connectedVendor)
        return connectVendorNetworkService.getVendor(param,nextPage)
    }

    override suspend fun searchVendor(country: String, connectedVendor: Long, searchQuery: String, nextPage: Int): Single<VendorListDataResponse> {
        val param = SearchVendorRequest(country = country, query = searchQuery, connectedVendor = connectedVendor)
        return connectVendorNetworkService.searchVendor(param,nextPage)
    }

     override suspend fun viewVendors(
         country: String,
         state: Long,
         connectedVendor: Long
     ): Single<ViewVendorsResponse> {
         val param = ViewVendorRequest(country = country, connectedVendor = connectedVendor, state = state)
         return connectVendorNetworkService.viewVendor(param)
     }
 }