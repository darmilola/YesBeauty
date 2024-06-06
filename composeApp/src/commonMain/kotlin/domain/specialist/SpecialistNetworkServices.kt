package domain.specialist

import com.badoo.reaktive.single.toSingle
import domain.Models.ServerResponse
import domain.Models.SpecialistAvailabilityResponse
import domain.Models.SpecialistReviews
import domain.Models.SpecialistReviewsResponse
import domain.Models.SpecialistTimeAvailabilityResponse
import domain.Profile.DeleteProfileRequest
import domain.Profile.UpdateProfileRequest
import domain.appointments.GetSpecialistAvailabilityRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class SpecialistNetworkService(private val apiService: HttpClient) {

    suspend fun getReviews(getReviewsRequest: GetReviewsRequest) =
        apiService.post {
            url("/api/v1/specialist/reviews/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getReviewsRequest)
        }.body<SpecialistReviewsResponse>().toSingle()

    suspend fun getSpecialistAvailability(getSpecialistAvailableTimeRequest: GetSpecialistAvailableTimeRequest) =
        apiService.post {
            url("/api/v1/specialist/availability/time/get")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(getSpecialistAvailableTimeRequest)
        }.body<SpecialistTimeAvailabilityResponse>().toSingle()

    suspend fun addTimeOff(timeOffRequest: TimeOffRequest) =
        apiService.post {
            url("/api/v1/specialist/off/add")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(timeOffRequest)
        }.body<ServerResponse>().toSingle()

    suspend fun removeTimeOff(timeOffRequest: TimeOffRequest) =
        apiService.post {
            url("/api/v1/specialist/off/remove")
            /*headers {
                append(HttpHeaders.Authorization, "abc123")
            }*/
            contentType(ContentType.Application.Json)
            setBody(timeOffRequest)
        }.body<ServerResponse>().toSingle()

}