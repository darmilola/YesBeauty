package infrastructure.authentication

import com.badoo.reaktive.single.Single
import domain.Models.AuthenticationResponse
import domain.Models.ListDataResponse
import domain.Models.ServerResponse
import domain.Models.Vendor

interface AuthenticationRepository {
    suspend fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ): Single<ServerResponse>

    suspend fun validateUserProfile(userEmail: String): Single<AuthenticationResponse>

}



