package utils

import models.AppointmentItem

fun getAppointmentViewHeight(
        itemList: List<AppointmentItem>
    ): Int {
        val itemCount = itemList.size

        return itemCount * 220
    }

