package applications.date

expect object DateTime {
    fun getFormattedDate(
        timestamp: String,
    ): String
}