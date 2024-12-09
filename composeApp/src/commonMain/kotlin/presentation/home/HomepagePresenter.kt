package presentation.home

import UIStates.AppUIStates
import domain.home.HomeRepositoryImpl
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.badoo.reaktive.single.subscribe
import domain.Enums.ServerResponse
import utils.calculateServicesGridList


class HomepagePresenter(apiService: HttpClient): HomepageContract.Presenter() {

    private val scope: CoroutineScope = MainScope()
    private var contractView: HomepageContract.View? = null
    private val homeRepositoryImpl: HomeRepositoryImpl = HomeRepositoryImpl(apiService)

    override fun registerUIContract(view: HomepageContract.View?) {
        contractView = view
    }

    override fun getUserHomepage(userId: Long) {
        contractView?.showLoadHomePageLce(AppUIStates(isLoading = true, loadingMessage = "Loading Home"))
        scope.launch(Dispatchers.Main) {
            try {
                val result = withContext(Dispatchers.IO) {
                    homeRepositoryImpl.getUserHomePage(userId)
                        .subscribe(
                            onSuccess = { response ->
                                when (response.status) {
                                    ServerResponse.SUCCESS.toPath() -> {
                                        val servicesGridList = calculateServicesGridList(response.homepageInfo.vendorServices!!)
                                        response.homepageInfo.servicesGridList = servicesGridList
                                        val dayAvailabilityString = arrayListOf<String>()
                                        val vendorDayAvailability = response.homepageInfo.dayAvailability
                                        for (item in vendorDayAvailability!!){
                                            dayAvailabilityString.add(item.platformDay!!.day!!)
                                        }
                                        contractView?.showHome(response.homepageInfo)
                                        contractView?.showVendorDayAvailability(dayAvailabilityString)
                                        contractView?.showLoadHomePageLce(AppUIStates(isSuccess = true))
                                    }
                                    ServerResponse.FAILURE.toPath() -> {
                                        contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                                    }
                                }
                            },
                            onError = {
                                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
                            },
                        )
                }
                result.dispose()
            } catch(e: Exception) {
                contractView?.showLoadHomePageLce(AppUIStates(isFailed = true, errorMessage = "Error Loading Home"))
            }
        }
    }
}