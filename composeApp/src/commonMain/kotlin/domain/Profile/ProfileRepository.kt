package domain.Profile

import com.badoo.reaktive.single.Single
import domain.Models.AuthenticationResponse
import domain.Models.ServerResponse

interface ProfileRepository {
    suspend fun updateProfile(
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

    suspend fun deleteProfile(userEmail: String): Single<ServerResponse>
}


