package presentation.authentication

import UIStates.AppUIStates
import domain.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.Models.User
import domain.Enums.ProfileStatus
import domain.Enums.ServerResponse
import utils.makeValidPhone

class AuthenticationPresenter(apiService: HttpClient): AuthenticationContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AuthenticationContract.View? = null
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl(apiService)

    override fun completeProfile(
        firstname: String, lastname: String, userEmail: String, authPhone: String,
        country: String, state: Long, signupType: String, gender: String, profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onCompleteProfileStarted()
                    authenticationRepositoryImpl.completeProfile(firstname, lastname, userEmail, authPhone,country,state, signupType, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                println("Error 0 $result")
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onCompleteProfileDone(result.userInfo)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onCompleteProfileError()
                                    }
                                    else -> {
                                        contractView?.onCompleteProfileError()
                                    }
                                }
                            },
                            onError = {
                                println("Error 1 ${it.message}")
                                contractView?.onCompleteProfileError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                println("Error 2 ${e.message}")
                contractView?.onCompleteProfileError()
            }
        }
    }

    override fun updateProfile(
        userId: Long,
        firstname: String,
        lastname: String,
        address: String,
        contactPhone: String,
        country: String,
        state: Long,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileUpdateStarted()
                    authenticationRepositoryImpl.updateProfile(userId, firstname, lastname, address, contactPhone, country,state, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = true)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = false)
                                    }
                                    else -> {
                                        contractView?.onProfileUpdateEnded(isSuccessful = false)
                                    }
                                }
                            },
                            onError = {
                                contractView?.onProfileUpdateEnded(isSuccessful = false)
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileUpdateEnded(isSuccessful = false)
            }
        }
    }
    override fun validateEmail(userEmail: String) {
        contractView?.onProfileValidationStarted()
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    authenticationRepositoryImpl.validateEmail(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == ServerResponse.SUCCESS.toPath()){
                                    when (result.profileStatus) {
                                        ProfileStatus.DONE.toPath() -> {
                                            contractView?.goToMainScreen(result.userInfo)
                                        }
                                        ProfileStatus.CONNECT_VENDOR.toPath() -> {
                                            contractView?.goToConnectVendor(result.userInfo)
                                        }
                                        ProfileStatus.COMPLETE_PROFILE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToCompleteProfileWithEmail(userEmail)
                                        }
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationError()
                                }
                            },
                            onError = {
                                contractView?.onProfileValidationError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationError()
            }
        }
    }

    override fun updateFcmToken(userId: Long, fcmToken: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    authenticationRepositoryImpl.updateFcmToken(userId, fcmToken)
                        .subscribe(onSuccess = { result -> }, onError = {})
                }
                result.dispose()
            } catch(_: Exception) {}
        }
    }

    override fun validatePhone(phone: String, requireValidation: Boolean) {
       var validPhone = ""
        validPhone = if (requireValidation) {
            makeValidPhone(phone)
        } else{
            phone
        }
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.onProfileValidationStarted()
                    authenticationRepositoryImpl.validatePhone(validPhone)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == ServerResponse.SUCCESS.toPath()){
                                    when (result.profileStatus) {
                                        ProfileStatus.DONE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToMainScreen(result.userInfo)
                                        }
                                        ProfileStatus.CONNECT_VENDOR.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToConnectVendor(result.userInfo)
                                        }
                                        ProfileStatus.COMPLETE_PROFILE.toPath() -> {
                                            contractView?.onProfileValidationEnded()
                                            contractView?.goToCompleteProfileWithPhone(phone)
                                        }
                                    }
                                }
                                else{
                                    contractView?.onProfileValidationError()
                                }
                            },
                            onError = {
                                contractView?.onProfileValidationError()
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.onProfileValidationError()
            }
        }
    }

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }
}