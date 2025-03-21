package presentation.payment

import UIStates.AppUIStates
import domain.Models.PaymentAuthorizationResult
import domain.payment.PaymentRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import com.badoo.reaktive.single.subscribe
import domain.Enums.Currency
import domain.Enums.ServerResponse

class PaymentPresenter(apiService: HttpClient): PaymentContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: PaymentContract.View? = null
    private val paymentRepositoryImpl: PaymentRepositoryImpl = PaymentRepositoryImpl(apiService)
    override fun registerUIContract(view: PaymentContract.View?) {
        contractView = view
    }

    override fun initCheckOut(customerEmail: String, amount: String, currency: String) {
        contractView?.showPaymentLce(AppUIStates(isLoading = true, loadingMessage = "Processing..."))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    paymentRepositoryImpl.initCheckout(paymentAmount = amount, customerEmail = customerEmail, currency = currency)
                        .subscribe(
                            onSuccess = { result ->
                                when (result.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val authorizationResult = Json.decodeFromString<PaymentAuthorizationResult>(result.authorizationResultJsonString)
                                        contractView?.showPaymentLce(AppUIStates(isSuccess = true, successMessage = "Processing Successful"))
                                        contractView?.showAuthorizationResult(authorizationResult)
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showPaymentLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showPaymentLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showPaymentLce(AppUIStates(isFailed = true, errorMessage = "Processing Error"))
            }
        }
    }




}