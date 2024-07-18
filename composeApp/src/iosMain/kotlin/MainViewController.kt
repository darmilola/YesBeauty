
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.russhwolf.settings.Settings
import domain.Models.Auth0ConnectionResponse
import domain.Enums.AuthSSOScreenNav
import domain.Enums.AuthenticationAction
import domain.Enums.AuthenticationStatus
import domain.Models.PlatformNavigator
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.UIKit.UIViewController
import presentation.Splashscreen.SplashScreen
import presentation.main.MainScreen


class MainViewController: PlatformNavigator {

    private var onLoginEvent: ((connectionType: String) -> Unit)? = null
    private var onSignupEvent: ((connectionType: String) -> Unit)? = null
    private var onLogoutEvent: ((connectionType: String) -> Unit)? = null
    private var onLocationEvent: (() -> Unit)? = null
    private var onUploadImageEvent: ((data: NSData) -> Unit)? = null
    private val preferenceSettings: Settings = Settings()
    //Handles All Screens Used For Authentication
    //Handles All Other Screens in the System
    private val mainScreen = MainScreen(platformNavigator = this)
    fun MainViewController(onLoginEvent:(connectionType: String) -> Unit,
                           onLogoutEvent:(connectionType: String) -> Unit,
                           onSignupEvent: ((connectionType: String) -> Unit)?,
                           onUploadImageEvent: (data: NSData) -> Unit,
                           onLocationEvent: () -> Unit): UIViewController {

            val view = ComposeUIViewController(configure = {
                onFocusBehavior = OnFocusBehavior. DoNothing }) {
                Navigator(SplashScreen(this)) { navigator ->
                    SlideTransition(navigator)

                 }
            }
            this.onLoginEvent = onLoginEvent
            this.onLogoutEvent = onLogoutEvent
            this.onSignupEvent = onSignupEvent
            this.onUploadImageEvent = onUploadImageEvent
            this.onLocationEvent = onLocationEvent
            return view
    }



    private fun onLogoutAuthResponse(response: Auth0ConnectionResponse) {
        preferenceSettings.clear()
    }


    override fun startVideoCall(authToken: String) {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun startImageUpload(imageByteArray: ByteArray) {
        val data = imageByteArray.usePinned {
            NSData.create(
                bytes = it.addressOf(0),
                length = imageByteArray.size.toULong()
            )
        }
        onUploadImageEvent?.let {
            it(data)
        }
    }


    override fun getUserLocation() {
       onLocationEvent?.let {
           it()
       }
    }

    override fun startGoogleSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startPhoneSS0(phone: String) {
        TODO("Not yet implemented")
    }

    override fun verifyOTP(
        verificationCode: String,
        onVerificationSuccessful: (String) -> Unit,
        onVerificationFailed: () -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun startXSSO(onAuthSuccessful: (String) -> Unit, onAuthFailed: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startScanningBarCode(onCodeReady: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startImageUpload(onUploadDone: (String) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun startNotificationService(onTokenReady: (String) -> Unit) {
        TODO("Not yet implemented")
    }
}



