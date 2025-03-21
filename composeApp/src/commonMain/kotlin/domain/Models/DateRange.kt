package domain.Models

import kotlinx.datetime.LocalDate

class DateRange(override val start: LocalDate,
                override val endInclusive: LocalDate,
                val stepDays: Long = 1) :
    Iterable<LocalDate>, ClosedRange<LocalDate> {

    override fun iterator(): Iterator<LocalDate> =
        DateIterator(start, endInclusive, stepDays)

    infix fun step(days: Long) = DateRange(start, endInclusive, days)

}