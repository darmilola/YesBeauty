package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.Product
import domain.Models.UserAppointment
import domain.Models.UserOrders
import domain.Models.Vendor
import domain.Models.VendorPackage
import domain.Models.VendorRecommendation
import kotlinx.coroutines.flow.StateFlow

class ResourceListEnvelopeViewModel<in T : Any>(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<T>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<@UnsafeVariance T>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<@UnsafeVariance T>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<@UnsafeVariance T>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}






class AppointmentResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<UserAppointment>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<UserAppointment>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<UserAppointment>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<UserAppointment>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }
}

class PackagesResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<VendorPackage>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<VendorPackage>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<VendorPackage>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<VendorPackage>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }
}







class TherapistAppointmentResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<Appointment>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<Appointment>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<Appointment>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<Appointment>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }
}







class ProductResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<Product>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<Product>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: List<Product>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<Product>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}




class OrdersResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<UserOrders>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<UserOrders>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<UserOrders>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<UserOrders>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}



class RecommendationsResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<VendorRecommendation>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<VendorRecommendation>>
        get() = _resources

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<VendorRecommendation>?) {
        savedStateHandle["resources"] = resources
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<VendorRecommendation>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}





class VendorsResourceListEnvelopeViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _resources =  savedStateHandle.getStateFlow("resources", mutableListOf<Vendor>())

    private var _nearbyVendors =  savedStateHandle.getStateFlow("nearbyVendors", listOf<Vendor>())

    private var _newVendors =  savedStateHandle.getStateFlow("newVendors", listOf<Vendor>())

    private var _nextPageUrl =  savedStateHandle.getStateFlow("nextPageUrl", "")

    private var _prevPageUrl =  savedStateHandle.getStateFlow("prevPageUrl", "")

    private var _currentPage =  savedStateHandle.getStateFlow("currentPage", 0)

    private var _totalItemCount =  savedStateHandle.getStateFlow("totalItemCount", 0)

    private var _displayedItemCount =  savedStateHandle.getStateFlow("displayedItemCount", 0)

    private var _isLoadingMore =  savedStateHandle.getStateFlow("isLoadingMore", false)

    private var _isSearching =  savedStateHandle.getStateFlow("isSearching", false)

    private var _isRefreshing =  savedStateHandle.getStateFlow("isRefreshing", false)


    val resources: StateFlow<MutableList<Vendor>>
        get() = _resources

    val nearbyVendors: StateFlow<List<Vendor>>
        get() = _nearbyVendors

    val newVendors: StateFlow<List<Vendor>>
        get() = _newVendors

    val prevPageUrl: StateFlow<String>
        get() = _prevPageUrl

    val nextPageUrl: StateFlow<String>
        get() = _nextPageUrl

    val currentPage: StateFlow<Int>
        get() = _currentPage

    val totalItemCount: StateFlow<Int>
        get() = _totalItemCount

    val displayedItemCount: StateFlow<Int>
        get() = _displayedItemCount

    val isLoadingMore: StateFlow<Boolean>
        get() = _isLoadingMore

    val isSearching: StateFlow<Boolean>
        get() = _isSearching

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing


    fun setResources(resources: MutableList<Vendor>?) {
        savedStateHandle["resources"] = resources
    }

    fun setNearbyVendor(nearbyVendors: List<Vendor>?) {
        savedStateHandle["nearbyVendors"] = nearbyVendors
    }

    fun setNewVendor(newVendors: List<Vendor>?) {
        savedStateHandle["newVendors"] = newVendors
    }

    fun setPrevPageUrl(prevPageUrl: String) {
        savedStateHandle["prevPageUrl"] = prevPageUrl
    }

    fun setNextPageUrl(nextPageUrl: String) {
        savedStateHandle["nextPageUrl"] = nextPageUrl
    }

    fun setCurrentPage(currentPage: Int) {
        savedStateHandle["currentPage"] = currentPage
    }

    fun setTotalItemCount(totalItemCount: Int) {
        savedStateHandle["totalItemCount"] = totalItemCount
    }

    fun setDisplayedItemCount(displayedItemCount: Int) {
        savedStateHandle["displayedItemCount"] = displayedItemCount
    }

    fun setLoadingMore(isLoadingMore: Boolean) {
        savedStateHandle["isLoadingMore"] = isLoadingMore
    }

    fun setIsSearching(isSearching: Boolean) {
        savedStateHandle["isSearching"] = isSearching
    }

    fun setIsRefreshing(isRefreshing: Boolean) {
        savedStateHandle["isRefreshing"] = isRefreshing
    }

    fun clearData(data: MutableList<Vendor>) {
        setResources(data)
        setCurrentPage(-1)
        setDisplayedItemCount(-1)
        setIsSearching(false)
        setIsRefreshing(false)
        setLoadingMore(false)
        super.onCleared()
    }


}

