package domain.Enums

enum class SharedPreferenceEnum {

    COUNTRY,
    PROFILE_ID,
    FIRSTNAME,
    VENDOR_ID,
    VENDOR_WHATSAPP_PHONE,
    AUTH_TYPE,
    AUTH_EMAIL,
    AUTH_PHONE,
    API_KEY,
    LATITUDE,
    LONGITUDE;

    fun toPath() = when (this) {
        COUNTRY -> "country"
        PROFILE_ID -> "profileId"
        FIRSTNAME -> "firstname"
        VENDOR_ID -> "vendorId"
        VENDOR_WHATSAPP_PHONE -> "whatsappPhone"
        AUTH_TYPE -> "authType"
        AUTH_EMAIL -> "authEmail"
        AUTH_PHONE -> "authPhone"
        API_KEY -> "apiKey"
        LATITUDE -> "latitude"
        LONGITUDE -> "longitude"

    }

}