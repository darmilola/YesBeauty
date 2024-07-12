package domain.Enums


enum class Screens {
    MAIN_TAB,
    BOOKING,
    CONSULTATION,
    CART,
    ORDERS,
    CONNECT_VENDOR_TAB,
    CONSULTATION_ROOM,
    EDIT_PROFILE,
    VENDOR_INFO,
    JOIN_SPA,
    THERAPIST_DASHBOARD,
    PENDING_APPOINTMENT,
    ORDER_DETAILS,
    TALK_WITH_A_THERAPIST;
    fun toPath() = when (this) {
        MAIN_TAB -> 0
        BOOKING -> 1
        CONSULTATION -> 2
        CART -> 3
        ORDERS -> 4
        CONNECT_VENDOR_TAB -> 5
        CONSULTATION_ROOM -> 6
        EDIT_PROFILE -> 7
        VENDOR_INFO -> 8
        PENDING_APPOINTMENT -> 9
        THERAPIST_DASHBOARD ->10
        JOIN_SPA -> 11
        TALK_WITH_A_THERAPIST -> 12
        ORDER_DETAILS -> 13
    }
}