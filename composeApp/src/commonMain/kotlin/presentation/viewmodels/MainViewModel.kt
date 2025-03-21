package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Enums.Currency
import domain.Enums.MainTabEnum
import domain.Enums.ProductType
import domain.Models.FavoriteProductIdModel
import domain.Models.OrderItem
import domain.Models.PlacedOrderItemComponent
import domain.Models.Services
import domain.Models.User
import domain.Models.Vendor
import kotlinx.coroutines.flow.StateFlow
import domain.Models.ServiceTypeItem
import domain.Models.VendorPackage


class MainViewModel(val savedStateHandle: SavedStateHandle): ViewModel(){

    private var _screenTitle = savedStateHandle.getStateFlow("screenTitle","Home")
    private var _connectedVendor =  savedStateHandle.getStateFlow("connectedVendor", Vendor())
    private var _displayCurrencyUnit =  savedStateHandle.getStateFlow("displayCurrencyUnit", Currency.USD.toDisplayUnit())
    private var _displayCurrencyPath =  savedStateHandle.getStateFlow("displayCurrencyPath", Currency.USD.toPath())
    private var _currentUserInfo =  savedStateHandle.getStateFlow("userInfo", User())
    private var _roomDbUserCount =  savedStateHandle.getStateFlow("roomDbUserCount", 0)
    private var _screenNav =  savedStateHandle.getStateFlow("screenNav", Pair(-1,-1))
    private var _selectedService =  savedStateHandle.getStateFlow("selectedService", Services())
    private var _selectedPackage =  savedStateHandle.getStateFlow("selectedPackage", VendorPackage())
    private var _recommendedServiceType =  savedStateHandle.getStateFlow("recommendedServiceType", ServiceTypeItem())
    private var _currentUnsavedOrders =  savedStateHandle.getStateFlow("currentUnsavedOrders", ArrayList<OrderItem>())
    private var _currentUnsavedOrderSize =  savedStateHandle.getStateFlow("currentUnsavedOrderSize", 0)
    private var _selectedProductType =  savedStateHandle.getStateFlow("selectedProductType", ProductType.COSMETICS.toPath())
    private var _currentMainDisplayTab =  savedStateHandle.getStateFlow("displayedTab", MainTabEnum.HOME.toPath())
    private var _isClickedSearchProductState =  savedStateHandle.getStateFlow("isClickedSearchProduct", false)
    private var _switchVendorId =  savedStateHandle.getStateFlow("switchVendorId", -1L)
    private var _switchVendorReason =  savedStateHandle.getStateFlow("switchVendorReason", "")
    private var _switchVendor =  savedStateHandle.getStateFlow("switchVendor", Vendor())
    private var _joinSpa =  savedStateHandle.getStateFlow("joinSpaVendor", Vendor())
    private var _restartApp =  savedStateHandle.getStateFlow("restartApp", false)
    private var _showProductBottomSheet =  savedStateHandle.getStateFlow("showProductBottomSheet", false)
    private var _showPaymentCardsBottomSheet =  savedStateHandle.getStateFlow("showPaymentCardsBottomSheet", false)
    private var _showProductReviewsBottomSheet =  savedStateHandle.getStateFlow("showProductReviewsBottomSheet", false)
    private var _showAppointmentReviewsBottomSheet =  savedStateHandle.getStateFlow("showAppointmentReviewsBottomSheet", false)
    private var _showPackageReviewsBottomSheet =  savedStateHandle.getStateFlow("showPackageReviewsBottomSheet", false)
    private var _onBackPressed =  savedStateHandle.getStateFlow("onBackPressed", false)
    private var _isSwitchVendor =  savedStateHandle.getStateFlow("isSwitchVendor", false)
    private var _orderItemComponents =  savedStateHandle.getStateFlow("orderItemComponents", arrayListOf<PlacedOrderItemComponent>())
    private var _favoriteProductIds =  savedStateHandle.getStateFlow("favoriteProductIds", listOf<FavoriteProductIdModel>())
    private var _dayAvailability =  savedStateHandle.getStateFlow("dayAvailability", arrayListOf<String>())

    val screenTitle: StateFlow<String>
        get() = _screenTitle

    val selectedService: StateFlow<Services>
        get() = _selectedService

    val dayAvailability: StateFlow<ArrayList<String>>
        get() = _dayAvailability

    val selectedPackage: StateFlow<VendorPackage>
        get() = _selectedPackage

    val roomDbUserCount: StateFlow<Int>
        get() = _roomDbUserCount

    val switchVendor: StateFlow<Vendor>
        get() = _switchVendor

    val unSavedOrders: StateFlow<ArrayList<OrderItem>>
        get() = _currentUnsavedOrders

    val orderItemComponents: StateFlow<ArrayList<PlacedOrderItemComponent>>
        get() = _orderItemComponents

    val favoriteProductIds: StateFlow<List<FavoriteProductIdModel>>
        get() = _favoriteProductIds

    val restartApp: StateFlow<Boolean>
        get() = _restartApp

    val selectedProductType: StateFlow<String>
        get() = _selectedProductType

    val showProductBottomSheet: StateFlow<Boolean>
        get() = _showProductBottomSheet

    val showPaymentCardsBottomSheet: StateFlow<Boolean>
        get() = _showPaymentCardsBottomSheet

    val showProductReviewsBottomSheet: StateFlow<Boolean>
        get() = _showProductReviewsBottomSheet

    val showAppointmentReviewsBottomSheet: StateFlow<Boolean>
        get() = _showAppointmentReviewsBottomSheet

    val showPackageReviewsBottomSheet: StateFlow<Boolean>
        get() = _showPackageReviewsBottomSheet

    val isSwitchVendor: StateFlow<Boolean>
        get() = _isSwitchVendor


    val clickedSearchProduct: StateFlow<Boolean>
        get() = _isClickedSearchProductState

    val displayedTab: StateFlow<String>
        get() = _currentMainDisplayTab
    val onBackPressed: StateFlow<Boolean>
        get() = _onBackPressed
    val unSavedOrderSize: StateFlow<Int>
        get() = _currentUnsavedOrderSize
    val connectedVendor: StateFlow<Vendor>
        get() = _connectedVendor
    val displayCurrencyUnit: StateFlow<String>
        get() = _displayCurrencyUnit

    val displayCurrencyPath: StateFlow<String>
        get() = _displayCurrencyPath

    val switchVendorId: StateFlow<Long>
        get() = _switchVendorId
    val switchVendorReason: StateFlow<String>
        get() = _switchVendorReason
    val joinSpaVendor: StateFlow<Vendor>
        get() = _joinSpa

    val currentUserInfo: StateFlow<User>
        get() = _currentUserInfo

    val recommendedServiceType: StateFlow<ServiceTypeItem>
        get() = _recommendedServiceType


    val screenNav: StateFlow<Pair<Int,Int>>
        get() = _screenNav

    fun setTitle(newTitle: String) {
        savedStateHandle["screenTitle"] = newTitle
    }
    fun setJoinSpaVendor(vendor: Vendor) {
        savedStateHandle["joinSpaVendor"] = vendor
    }
    fun setDayAvailability(dayAvailability: ArrayList<String>) {
        savedStateHandle["dayAvailability"] = dayAvailability
    }
    fun setOrderItemComponents(orderItemComponents: ArrayList<PlacedOrderItemComponent>) {
        savedStateHandle["orderItemComponents"] = orderItemComponents
    }
    fun setRoomDbUserAccount(userCount: Int) {
        savedStateHandle["roomDbUserCount"] = userCount
    }
    fun setSwitchVendor(vendor: Vendor) {
        savedStateHandle["switchVendor"] = vendor
    }

    fun setSelectedProductType(productType: String) {
        savedStateHandle["selectedProductType"] = productType
    }
    fun setIsSwitchVendor(isSwitchVendor: Boolean) {
        savedStateHandle["isSwitchVendor"] = isSwitchVendor
    }
    fun showProductBottomSheet(show: Boolean) {
        savedStateHandle["showProductBottomSheet"] = show
    }

    fun showProductReviewsBottomSheet(show: Boolean) {
        savedStateHandle["showProductReviewsBottomSheet"] = show
    }

    fun showAppointmentReviewsBottomSheet(show: Boolean) {
        savedStateHandle["showAppointmentReviewsBottomSheet"] = show
    }

    fun showPackageReviewsBottomSheet(show: Boolean) {
        savedStateHandle["showPackageReviewsBottomSheet"] = show
    }

    fun showPaymentCardsBottomSheet(show: Boolean) {
        savedStateHandle["showPaymentCardsBottomSheet"] = show
    }

    fun setSwitchVendorId(vendorId: Long) {
        savedStateHandle["switchVendorId"] = vendorId
    }

    fun setSwitchVendorReason(switchReason: String) {
        savedStateHandle["switchVendorReason"] = switchReason
    }

    fun setFavoriteProductIds(favoriteProductIds: List<FavoriteProductIdModel>) {
        savedStateHandle["favoriteProductIds"] = favoriteProductIds
    }

    fun setOnBackPressed(onBackPressed: Boolean) {
        savedStateHandle["onBackPressed"] = onBackPressed
    }

    fun setIsClickedSearchProduct(isSearching: Boolean) {
        savedStateHandle["isClickedSearchProduct"] = isSearching
    }

    fun setDisplayedTab(displayedTab: String) {
        savedStateHandle["displayedTab"] = displayedTab
    }

    fun setUnsavedOrderSize(size: Int) {
        savedStateHandle["currentUnsavedOrderSize"] = size
    }

    fun setScreenNav(screenNav: Pair<Int,Int>) {
        savedStateHandle["screenNav"] = screenNav
    }

    fun setConnectedVendor(vendor: Vendor) {
        savedStateHandle["connectedVendor"] = vendor
    }

    fun setDisplayCurrencyUnit(currencyUnit: String) {
        savedStateHandle["displayCurrencyUnit"] = currencyUnit
    }

    fun setDisplayCurrencyPath(currencyPath: String) {
        savedStateHandle["displayCurrencyPath"] = currencyPath
    }

    fun setRecommendationServiceType(serviceTypeItem: ServiceTypeItem) {
        savedStateHandle["recommendedServiceType"] = serviceTypeItem
    }
    fun setVendorBusinessLogoUrl(vendorLogoUrl: String) {
        savedStateHandle["currentVendorLogoUrl"] = vendorLogoUrl
    }

    fun setUserInfo(user: User) {
        savedStateHandle["userInfo"] = user
    }

    fun setSelectedService(selectedService: Services) {
        savedStateHandle["selectedService"] = selectedService
    }
    fun setSelectedPackage(selectedPackage: VendorPackage) {
        savedStateHandle["selectedPackage"] = selectedPackage
    }
    fun setCurrentUnsavedOrders(orderItems: ArrayList<OrderItem>) {
        savedStateHandle["currentUnsavedOrders"] = orderItems
    }
    fun clearCurrentOrderReference() {
        savedStateHandle["currentOrderReference"] = -1
    }

    fun clearUnsavedOrders() {
        savedStateHandle["currentUnsavedOrders"] = ArrayList<OrderItem>()
        savedStateHandle["currentUnsavedOrderSize"] = 0
    }

    fun setIsRestart(isRestart: Boolean) {
        savedStateHandle["isRestart"] = isRestart
    }


}