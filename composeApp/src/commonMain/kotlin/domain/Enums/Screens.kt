package domain.Enums


enum class Screens {
    MAIN_SCREEN,
    BOOKING,
    CONSULTATION,
    CART,
    ORDERS,
    CONNECT_VENDOR,
    CONSULTATION_ROOM,
    EDIT_PROFILE,
    VENDOR_INFO,
    JOIN_SPA,
    THERAPIST_DASHBOARD,
    PENDING_APPOINTMENT,
    ORDER_DETAILS,
    JOIN_SPA_INFO,
    CONNECTED_VENDOR_DETAILS,
    PACKAGE_INFO,
    PACKAGE_BOOKING,
    DEFAULT;
    fun toPath() = when (this) {
        DEFAULT -> -1
        MAIN_SCREEN -> 0
        BOOKING -> 1
        CONSULTATION -> 2
        CART -> 3
        ORDERS -> 4
        CONNECT_VENDOR -> 5
        CONSULTATION_ROOM -> 6
        EDIT_PROFILE -> 7
        VENDOR_INFO -> 8
        PENDING_APPOINTMENT -> 9
        THERAPIST_DASHBOARD ->10
        JOIN_SPA -> 11
        ORDER_DETAILS -> 13
        CONNECTED_VENDOR_DETAILS -> 14
        JOIN_SPA_INFO -> 15
        PACKAGE_INFO -> 16
        PACKAGE_BOOKING -> 17
    }
}