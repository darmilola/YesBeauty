package infrastructure.connectVendor

import com.badoo.reaktive.single.toSingle
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ConnectVendorNetworkService (private val apiService: HttpClient) {
    suspend fun connectVendor(connectVendorRequest: ConnectVendorRequest) =
        apiService.post {
            url("/api/v1/auth/user/vendor/connect")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(connectVendorRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun searchVendor(searchVendorRequest: SearchVendorRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/profile/vendor/search?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(searchVendorRequest)
        }.body<ListDataResponse<Vendor>>().toSingle()

    suspend fun getVendor(getVendorRequest: GetVendorRequest, nextPage: Int = 1) =
        apiService.post {
            url("/api/v1/profile/vendor/get?page=$nextPage")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getVendorRequest)
        }.body<ListDataResponse<Vendor>>().toSingle()


}