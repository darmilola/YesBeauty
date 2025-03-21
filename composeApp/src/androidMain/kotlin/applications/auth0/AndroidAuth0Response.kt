package applications.auth0

import android.os.Parcel
import android.os.Parcelable
import domain.Enums.Auth0ConnectionType
import domain.Enums.AuthenticationAction
import domain.Enums.AuthenticationStatus
import kotlinx.serialization.Serializable

@Serializable
data class AndroidAuth0ConnectionResponse(
    val connectionType: String? = Auth0ConnectionType.GOOGLE.toPath(),
    val email: String? = "", val action: String? = AuthenticationAction.SIGNUP.toPath(),
    val status: String? = AuthenticationStatus.AUTHENTICATION_SUCCESS.toPath()): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(connectionType)
        parcel.writeString(email)
        parcel.writeString(action)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AndroidAuth0ConnectionResponse> {
        override fun createFromParcel(parcel: Parcel): AndroidAuth0ConnectionResponse {
            return AndroidAuth0ConnectionResponse(parcel)
        }

        override fun newArray(size: Int): Array<AndroidAuth0ConnectionResponse?> {
            return arrayOfNulls(size)
        }
    }

}
