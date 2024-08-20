package domain.Models

import com.hoc081098.kmp.viewmodel.parcelable.Parcelable

interface PlatformNavigator: Parcelable {
     fun startVideoCall(authToken: String)
     fun startScanningBarCode(onCodeReady: (String) -> Unit)
     fun getUserLocation(onLocationReady: (String, String) -> Unit)
     fun startGoogleSSO(onAuthSuccessful: (String) -> Unit,
                        onAuthFailed: () -> Unit)
     fun startPhoneSS0(phone: String)
     fun verifyOTP(verificationCode: String, onVerificationSuccessful: (String) -> Unit,
                   onVerificationFailed: () -> Unit)
     fun startXSSO(onAuthSuccessful: (String) -> Unit,
                   onAuthFailed: () -> Unit)
    fun startImageUpload(onUploadDone: (String) -> Unit)
    fun startNotificationService(onTokenReady: (String) -> Unit)
    fun sendOrderBookingNotification(customerName: String, vendorLogoUrl: String, fcmToken: String)
    fun sendMeetingBookingNotification(customerName: String, vendorLogoUrl: String,
                                       meetingDay: String, meetingMonth: String, meetingYear: String, meetingTime: String, fcmToken: String)

    fun sendAppointmentBookingNotification(customerName: String, vendorLogoUrl: String, businessName: String,
                                           appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String,
                                           serviceType: String, fcmToken: String)


    fun sendPostponedAppointmentNotification(customerName: String, vendorLogoUrl: String, businessName: String,
                                appointmentDay: String, appointmentMonth: String, appointmentYear: String, appointmentTime: String,
                                serviceType: String, fcmToken: String)
    fun sendConnectVendorNotification(customerName: String, vendorLogoUrl: String, fcmToken: String)

    fun sendCustomerExitNotification(exitReason: String, vendorLogoUrl: String, fcmToken: String)

    fun exitApp()
    fun restartApp()
    fun goToMainScreen()
    fun startPaymentProcess(paymentAmount: String, accessCode: String, paymentCard: PaymentCard, customerEmail: String, onPaymentLoading: () -> Unit, onPaymentSuccessful: () -> Unit, onPaymentFailed: () -> Unit)
 }