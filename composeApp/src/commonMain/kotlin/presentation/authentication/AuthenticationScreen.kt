package presentation.authentication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import domain.Models.PlatformNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.hoc081098.kmp.viewmodel.compose.kmpViewModel
import com.hoc081098.kmp.viewmodel.createSavedStateHandle
import com.hoc081098.kmp.viewmodel.viewModelFactory
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import domain.Models.Auth0ConnectionResponse
import domain.Enums.AuthSSOScreenNav
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.DomainViewHandler.AuthenticationScreenHandler
import presentation.connectVendor.ConnectVendorScreen
import presentation.dialogs.LoadingDialog
import presentation.main.MainScreen
import presentation.viewmodels.AuthenticationViewModel
import presentation.viewmodels.ScreenUIStateViewModel

open class AuthenticationScreen(private var currentPosition: Int = AuthSSOScreenNav.AUTH_LOGIN.toPath(),
                                val  platformNavigator: PlatformNavigator? = null,
                                private val auth0ConnectionResponse: Auth0ConnectionResponse? = null) : Screen, KoinComponent {

    private val preferenceSettings: Settings = Settings()
    private val authenticationPresenter : AuthenticationPresenter by inject()
    private var screenUiStateViewModel: ScreenUIStateViewModel? = null
    private var userNavigationPosition = AuthSSOScreenNav.AUTH_LOGIN.toPath()
    private var authenticationViewModel: AuthenticationViewModel? = null
    private var auth0UserEmail = ""

    @Composable
    override fun Content() {

        val contentVisible = remember { mutableStateOf(false) }
        val contentLoading = remember { mutableStateOf(false) }
        val errorVisible = remember { mutableStateOf(false) }
        val imageUploading = remember { mutableStateOf(false) }
        val isAuthEmailAssigned = remember { mutableStateOf(false) }
        var userNavigation = remember { mutableStateOf(false) }

        // for Ios Auth0
        preferenceSettings as ObservableSettings
        preferenceSettings.addStringListener("auth0Email","") {
                value: String -> if(value != "") {
              auth0UserEmail = value
              isAuthEmailAssigned.value = true
           }
        }

        preferenceSettings.addBooleanListener("imageUploadProcessing",false) {
                value: Boolean -> imageUploading.value = value
        }

        if (auth0ConnectionResponse != null && currentPosition == AuthSSOScreenNav.AUTH_LOGIN.toPath()) {
            // for Android Auth0
            auth0UserEmail = auth0ConnectionResponse.email
            isAuthEmailAssigned.value = true
        }

        screenUiStateViewModel = kmpViewModel(
            factory = viewModelFactory {
                ScreenUIStateViewModel(savedStateHandle = createSavedStateHandle())
            },
        )

        authenticationViewModel = kmpViewModel(
            factory = viewModelFactory {
                AuthenticationViewModel(savedStateHandle = createSavedStateHandle())
            },
        )

        // View Contract Handler Initialisation
        val handler = AuthenticationScreenHandler(authenticationViewModel!!,authenticationPresenter,
            onUserLocationReady = {

            }
            ,preferenceSettings,
            onPageLoading = {
                contentLoading.value = true
            },
            onContentVisible = {
                contentLoading.value = false
                contentVisible.value = true
                isAuthEmailAssigned.value = false
            },
            onErrorVisible = {
                errorVisible.value = true
                contentLoading.value = false
                isAuthEmailAssigned.value = false
            },
            enterPlatform = {
                preferenceSettings["userEmail"] = it
                preferenceSettings["isVendorConnected"] = true
                userNavigation.value = true
                //isAuthEmailAssigned.value = false
                userNavigationPosition = AuthSSOScreenNav.MAIN.toPath()
            },
            completeProfile = {
                preferenceSettings.clear()
                userNavigation.value = true
                isAuthEmailAssigned.value = false
                userNavigationPosition = AuthSSOScreenNav.COMPLETE_PROFILE.toPath()
            },
            connectVendor = {
                preferenceSettings.clear()
                preferenceSettings["userEmail"] = it
                preferenceSettings["isVendorConnected"] = false
                isAuthEmailAssigned.value = false
                userNavigation.value = true
                userNavigationPosition = AuthSSOScreenNav.CONNECT_VENDOR.toPath()
            }, isLoading = {}, isSuccess = {}, isFailed = {})
        handler.init()

        //Main Service Content Arena
        if (contentLoading.value && currentPosition != AuthSSOScreenNav.COMPLETE_PROFILE.toPath()) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Loading...")
            }
        }
        else if (contentLoading.value && currentPosition == AuthSSOScreenNav.COMPLETE_PROFILE.toPath()) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Completing Profile...")
            }
        }

        else if(imageUploading.value) {
            Box(modifier = Modifier.fillMaxWidth(0.80f)) {
                LoadingDialog("Uploading...")
            }
        }

        if (isAuthEmailAssigned.value && auth0UserEmail.isNotEmpty() && currentPosition == AuthSSOScreenNav.AUTH_LOGIN.toPath()) {
            authenticationPresenter.validateUserProfile(auth0UserEmail)
        }
        if(!userNavigation.value) {
            AuthenticationScreenCompose(currentPosition = currentPosition)
        }
        else if(userNavigation.value && userNavigationPosition == AuthSSOScreenNav.COMPLETE_PROFILE.toPath()){
            currentPosition =  AuthSSOScreenNav.COMPLETE_PROFILE.toPath()
            AuthenticationScreenCompose(currentPosition = userNavigationPosition, userEmail = auth0UserEmail)
        }
        else if(userNavigation.value && userNavigationPosition == AuthSSOScreenNav.CONNECT_VENDOR.toPath()){
            currentPosition =  AuthSSOScreenNav.CONNECT_VENDOR.toPath()
            val navigator = LocalNavigator.current
            navigator?.replaceAll(ConnectVendorScreen(platformNavigator))
        }
        else if(userNavigation.value && userNavigationPosition == AuthSSOScreenNav.MAIN.toPath()){
            currentPosition =  AuthSSOScreenNav.MAIN.toPath()
            val navigator = LocalNavigator.current
            navigator?.replaceAll(MainScreen(platformNavigator = platformNavigator))
        }

    }

    open fun setLoginAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        println(auth0ConnectionResponse.email)
        preferenceSettings.clear()
        if (auth0ConnectionResponse.email.isNotEmpty()) {
            preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
            preferenceSettings["auth0Email"] = auth0ConnectionResponse.email
            preferenceSettings["authAction"] = auth0ConnectionResponse.action
        }
    }

    open fun setSignupAuthResponse(auth0ConnectionResponse: Auth0ConnectionResponse) {
        println(auth0ConnectionResponse.email)
        preferenceSettings.clear()
        if (auth0ConnectionResponse.email.isNotEmpty()) {
            preferenceSettings["connectionType"] = auth0ConnectionResponse.connectionType
            preferenceSettings["auth0Email"] = auth0ConnectionResponse.email
            preferenceSettings["authAction"] = auth0ConnectionResponse.action
        }
    }

    open fun setImageUploadResponse(imageUrl: String) {
        preferenceSettings["imageUrl"] = imageUrl
    }

    open fun setImageUploadProcessing(isDone: Boolean = false) {
        preferenceSettings["imageUploadProcessing"] = isDone
    }

    @Composable
    open fun AuthenticationScreenCompose(currentPosition: Int = AuthSSOScreenNav.AUTH_LOGIN.toPath(), userEmail: String = "") {
              when (currentPosition) {
                    AuthSSOScreenNav.AUTH_LOGIN.toPath() -> {
                        SignUpLogin(platformNavigator = platformNavigator!!)
                    }

                AuthSSOScreenNav.AUTH_SIGNUP.toPath() -> {
                        SignUpLogin(platformNavigator!!)
                    }

                AuthSSOScreenNav.COMPLETE_PROFILE.toPath() -> {
                        CompleteProfile(authenticationPresenter,userEmail,platformNavigator = platformNavigator!!)
                    }

                }
         }
}
