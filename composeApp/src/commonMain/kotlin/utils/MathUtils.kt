package utils

import domain.Enums.AppointmentType
import domain.Enums.ServiceLocationEnum
import domain.Models.OrderItem
import domain.Models.PlacedOrderItemComponent
import domain.Models.UserAppointment
import domain.Products.OrderItemRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin


fun calculateCartCheckoutSubTotal(orderItems: MutableList<OrderItem>): Long {
   var subtotal: Long = 0
  for (item in orderItems){
      val price = item.itemProduct?.productPrice
      val qty = item.itemCount
      val qtyPrice = price?.times(qty)
      subtotal += qtyPrice!!
  }
    return subtotal
}

fun calculateAppointmentPaymentAmount(appointments: List<UserAppointment>): Int {
    var total = 0
    for (item in appointments){
        if (item.resources!!.appointmentType == AppointmentType.SINGLE.toPath()) {
            val price = item.resources.serviceTypeItem!!.price
            val mobileServicePrice = item.resources.serviceTypeItem!!.mobileServicePrice
            val serviceLocation = item.resources.serviceLocation
            total = if (serviceLocation == ServiceLocationEnum.MOBILE.toPath()) {
                total + mobileServicePrice
            } else {
                total + price
            }
        }
        else if (item.resources.appointmentType == AppointmentType.PACKAGE.toPath()){
            val price = item.resources.packageInfo!!.price
            val mobileServicePrice = item.resources.packageInfo!!.mobileServicePrice
            val serviceLocation = item.resources.serviceLocation
            total = if (serviceLocation == ServiceLocationEnum.MOBILE.toPath()){
                total + mobileServicePrice
            } else{
                total + price
            }
        }
    }
    return total
}


fun calculatePackageAppointmentPaymentAmount(appointments: List<UserAppointment>): Int {
    var total: Int = 0
    for (item in appointments){
        if (item.resources!!.appointmentType == AppointmentType.SINGLE.toPath()) {
            val price = item.resources.serviceTypeItem!!.price
            val mobileServicePrice = item.resources.serviceTypeItem!!.mobileServicePrice
            val serviceLocation = item.resources.serviceLocation
            total = if (serviceLocation == ServiceLocationEnum.MOBILE.toPath()) {
                total + mobileServicePrice
            } else {
                total + price
            }
        }
        else if (item.resources.appointmentType == AppointmentType.PACKAGE.toPath()){
            val price = item.resources.packageInfo!!.price
            val mobileServicePrice = item.resources.packageInfo!!.mobileServicePrice
            val serviceLocation = item.resources.serviceLocation
            total = if (serviceLocation == ServiceLocationEnum.MOBILE.toPath()){
                total + mobileServicePrice
            } else{
                total + price
            }
        }
    }
    return total
}


fun calculatePlacedOrderTotalPrice(placedOrderItemComponent: ArrayList<PlacedOrderItemComponent>): Long {
    var total: Long = 0
    for (item in placedOrderItemComponent){
        val price = item.productPrice
        val qty = item.itemCount
        val qtyPrice = price.times(qty)
        total += qtyPrice
    }
    return total
}

fun getUnSavedOrdersRequestJson(orders: List<OrderItem>): String {
    val orderRequestList = arrayListOf<String>()
    for (item in orders) {
        val itemRequest = OrderItemRequest(
            productId = item.productId, productName = item.itemProduct?.productName!!,
            productDescription = item.itemProduct!!.productDescription,
            price = item.itemProduct!!.productPrice, itemCount = item.itemCount,
            imageUrl = item.itemProduct!!.productImages[0].imageUrl
        )
        val jsonStrRequest = Json.encodeToString(itemRequest)
        orderRequestList.add(jsonStrRequest)
    }
    return orderRequestList.toString()
}

fun calculateTotal(subtotal: Long, deliveryFee: Long): Long {
    return subtotal + deliveryFee
}
 fun getDistanceFromCustomer(userLat: Double, userLong: Double, vendorLat: Double, vendorLong: Double): Double {
    val theta = userLong - vendorLong
    var dist: Double = (sin(deg2rad(userLat)) * sin(deg2rad(vendorLat))
            + (cos(deg2rad(userLat))
            * cos(deg2rad(vendorLat))
            * cos(deg2rad(theta))))
    dist = acos(dist)
    dist = rad2deg(dist)
    dist *= 60 * 1.1515
     dist *= 1.609344
    return dist
}

private fun deg2rad(deg: Double): Double {
    return deg * PI / 180.0
}

private fun rad2deg(rad: Double): Double {
    return rad * 180.0 / PI
}

fun getMinuteDrive(distanceFromCustomer: Double): String {
    var minuteDriveText = ""
    minuteDriveText = if (distanceFromCustomer < 60){
        "${distanceFromCustomer.toInt()} Minutes Drive"
    }
    else if (distanceFromCustomer.toInt() == 60){
        "1 Hour Drive"
    }
    else {
        val hour = (distanceFromCustomer/60).toInt()
        val minute = distanceFromCustomer.mod(60.0)
        if (hour.toInt() <= 1){
            "${hour.toInt()} Hour Drive"
        } else{
            "${hour.toInt()} Hours Drive"
        }
    }
    return minuteDriveText
}
