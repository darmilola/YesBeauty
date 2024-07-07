package utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import applications.device.ScreenSizeInfo
import domain.Models.Appointment
import domain.Models.CustomerOrder
import domain.Models.HomepageInfo
import domain.Models.OrderItem
import domain.Models.Product
import domain.Models.ScreenSizeInfo
import domain.Models.Services
import domain.Models.UnsavedAppointment
import domain.Models.UserAppointmentsData

fun getAppointmentViewHeight(
        itemList: List<UserAppointmentsData>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 200
    }

fun getOrderViewHeight(
    itemCount: Int
): Int{

    return itemCount * 350
}

fun getRecentAppointmentViewHeight(
    itemList: List<Appointment>
): Int {
    val itemCount = itemList.size

    return itemCount * 200
}



fun getUnSavedAppointmentViewHeight(
    itemList: List<UnsavedAppointment>
): Int {
    val itemCount = itemList.size

    return itemCount * 190
}

fun getPopularProductViewHeight(
    itemList: List<Product>
): Int {
    val itemCount = itemList.size

    return itemCount * 225
}

fun getServicesViewHeight(
    itemList: List<Services>
): Int {
    val lineCount: Int = itemList.size/4

    return lineCount * 140
}

fun getPercentOfScreenHeight(
    screenHeight: Dp,
    percentChange: Int
): Int {
    val screenHeightChange = (percentChange.toDouble().div(100.0)) * screenHeight.value
    return screenHeightChange.toInt()
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

fun calculateHomePageScreenHeight(homepageInfo: HomepageInfo, screenSizeInfo: ScreenSizeInfo, isStatusExpanded: Boolean = false): Int{
    val serviceCount = homepageInfo.vendorServices!!.size
    val recommendationsCount = homepageInfo.recommendationRecommendations!!.size
    val recentAppointmentCount = homepageInfo.recentAppointment!!.size

    val heightAtExpanded = getPercentOfScreenHeight(screenSizeInfo.heightPx.dp, percentChange = 80)
    val heightAtCollapsed = getPercentOfScreenHeight(screenSizeInfo.heightPx.dp, percentChange = 60)


    val servicesHeight = serviceCount * 140
    val recommendationsHeight = 400
    val recentAppointmentHeight = recentAppointmentCount * 200

    return servicesHeight + recentAppointmentHeight + recommendationsHeight
}

