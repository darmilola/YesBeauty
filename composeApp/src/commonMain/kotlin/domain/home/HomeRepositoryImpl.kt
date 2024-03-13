package domain.home

import com.badoo.reaktive.single.Single
import domain.Models.HomePageResponse
import infrastructure.authentication.AuthenticationNetworkService
import infrastructure.authentication.AuthenticationRepository
import io.ktor.client.HttpClient

class HomeRepositoryImpl(apiService: HttpClient):
    HomeRepository {

    private val homeNetworkService: HomeNetworkService = HomeNetworkService(apiService)
    override suspend fun getUserHomePage(userEmail: String): Single<HomePageResponse> {
        val param = GetHomeRequest(userEmail)
        return homeNetworkService.getHomePage(param)
    }

}