package presentation.authentication

import infrastructure.authentication.AuthenticationRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import presentation.viewmodels.UIStates
import com.badoo.reaktive.single.subscribe
import dev.jordond.compass.Place
import domain.Models.User
import presentation.viewmodels.AsyncUIStates


class AuthenticationPresenter(apiService: HttpClient): AuthenticationContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: AuthenticationContract.View? = null
    private val authenticationRepositoryImpl: AuthenticationRepositoryImpl = AuthenticationRepositoryImpl(apiService)

    override fun completeProfile(
        firstname: String,
        lastname: String,
        userEmail: String,
        address: String,
        contactPhone: String,
        countryId: Int,
        cityId: Int,
        gender: String,
        profileImageUrl: String
    ) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.completeProfile(firstname, lastname, userEmail, address, contactPhone, countryId, cityId, gender, profileImageUrl)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    contractView?.goToConnectVendor(userEmail)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun validateUserProfile(userEmail: String) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showLce(UIStates(loadingVisible = true))
                    authenticationRepositoryImpl.validateUserProfile(userEmail)
                        .subscribe(
                            onSuccess = { result ->
                                if (result.status == "success"){
                                    contractView?.showLce(UIStates(contentVisible = true))
                                    directUser(result.userInfo)
                                }
                                else{
                                    contractView?.showLce(UIStates(errorOccurred = true))
                                    contractView?.goToCompleteProfile(userEmail)
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showLce(UIStates(errorOccurred = true), message = it1) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLce(UIStates(errorOccurred = true))
            }
        }
    }

    override fun getUserLocation(lat: Double, lng: Double) {
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    contractView?.showAsyncLce(AsyncUIStates(isLoading = true))
                    authenticationRepositoryImpl.reverseGeocode(lat, lng)
                        .subscribe(
                            onSuccess = { result ->
                                if (result?.country?.isNotEmpty() == true){
                                    contractView?.showAsyncLce(AsyncUIStates(isSuccess = true))
                                    contractView?.showUserLocation(result)
                                }
                                else{
                                    contractView?.showAsyncLce(AsyncUIStates(isFailed = true))
                                }
                            },
                            onError = {
                                it.message?.let { it1 -> contractView?.showAsyncLce(AsyncUIStates(isFailed = true)) }
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showAsyncLce(AsyncUIStates(isFailed = true))
            }
        }
    }

    override fun registerUIContract(view: AuthenticationContract.View?) {
        contractView = view
    }

    override fun startAuth0() {
        contractView?.onAuth0Started()
    }

    override fun endAuth0() {
        contractView?.onAuth0Ended()
    }

    private fun directUser(user: User){
        if (user.connectedVendor != -1){
            contractView?.goToMainScreen(user.userEmail!!)
        }
        else if (user.connectedVendor == -1){
            contractView?.goToConnectVendor(user.userEmail!!)
        }
    }

}