package presentation.connectVendor

import UIStates.AppUIStates
import domain.Models.Vendor
import domain.Models.VendorResourceListEnvelope

class ConnectVendorContract {
    interface View {
        fun showScreenLce(appUIStates: AppUIStates)
        fun showActionLce(appUIStates: AppUIStates, message: String = "")
        fun showVendors(vendors: VendorResourceListEnvelope?, isFromSearch: Boolean = false, isLoadMore: Boolean = false)
        fun showVendorsView(nearbyVendor: List<Vendor>?, newVendor: List<Vendor>?)
        fun onLoadMoreVendorStarted(isSuccess: Boolean = false)
        fun onLoadMoreVendorEnded(isSuccess: Boolean = false)
    }

    abstract class Presenter {
        abstract fun registerUIContract(view: View?)
        abstract fun connectVendor(userId: Long, vendorId: Long, action: String)
        abstract fun getVendor(country: String, state: Long, connectedVendor: Long)
        abstract fun getMoreVendor(country: String, state: Long, connectedVendor: Long, nextPage: Int = 1)
        abstract fun searchVendor(country: String, connectedVendor: Long, searchQuery: String)
        abstract fun searchMoreVendors(country: String, connectedVendor: Long, searchQuery: String, nextPage: Int = 1)
        abstract fun viewVendors(country: String, state: Long, connectedVendor: Long)
    }
}