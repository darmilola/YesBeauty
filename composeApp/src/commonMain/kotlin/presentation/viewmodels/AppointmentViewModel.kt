package presentation.viewmodels

import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import domain.Models.Appointment
import domain.Models.ServiceTime
import domain.Models.TimeOffs
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import presentation.dataModeller.CalendarDataSource

class PostponementViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _therapistAvailableTimes = savedStateHandle.getStateFlow("therapistAvailableTimes", arrayListOf<ServiceTime>())
    private var _therapistBookedTimes = savedStateHandle.getStateFlow("therapistBookedAppointment", arrayListOf<Appointment>())
    private var _therapistTimeOffs = savedStateHandle.getStateFlow("therapistTimeOffs", arrayListOf<TimeOffs>())
    private var _currentAppointment = savedStateHandle.getStateFlow("currentAppointment",Appointment())
    private var _postponementViewUIState = savedStateHandle.getStateFlow("postponementViewUIState", AsyncUIStates())
    private var _day =  savedStateHandle.getStateFlow("day", -1)
    private var _month =  savedStateHandle.getStateFlow("month", -1)
    private var _year =  savedStateHandle.getStateFlow("year", -1)
    private var _newSelectedTime = savedStateHandle.getStateFlow("newSelectedTime", ServiceTime())


    val therapistAvailableTimes: StateFlow<List<ServiceTime>>
        get() = _therapistAvailableTimes

    val therapistBookedTimes: StateFlow<List<Appointment>>
        get() = _therapistBookedTimes

    val therapistTimeOffs: StateFlow<List<TimeOffs>>
        get() = _therapistTimeOffs

    val currentAppointment: StateFlow<Appointment>
        get() = _currentAppointment

    val postponementViewUIState: StateFlow<AsyncUIStates>
        get() = _postponementViewUIState

    val day: StateFlow<Int>
        get() = _day

    val month: StateFlow<Int>
        get() = _month

    val year: StateFlow<Int>
        get() = _year
    val selectedTime: StateFlow<ServiceTime>
        get() = _newSelectedTime

    fun setTherapistAvailableTimes(availableTimes: List<ServiceTime>) {
        savedStateHandle["therapistAvailableTimes"] = availableTimes
    }

    fun setTherapistTimeOffs(timeOffs: List<TimeOffs>) {
        savedStateHandle["therapistTimeOffs"] = timeOffs
    }


    fun setCurrentAppointment(currentAppointment: Appointment) {
        savedStateHandle["currentAppointment"] = currentAppointment
    }

    fun setTherapistBookedAppointment(bookedAppointment: List<Appointment>) {
        savedStateHandle["therapistBookedAppointment"] = bookedAppointment
    }

    fun setPostponementViewUIState(asyncUIStates: AsyncUIStates) {
        savedStateHandle["postponementViewUIState"] = asyncUIStates
    }

    fun clearServiceTimes() {
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<ServiceTime>()
    }

    fun setSelectedDay(day: Int) {
        savedStateHandle["day"] = day
    }

    fun setSelectedMonth(month: Int) {
        savedStateHandle["month"] = month
    }

    fun setSelectedYear(year: Int) {
        savedStateHandle["year"] = year
    }


    fun clearPostponementSelection(){
        savedStateHandle["newSelectedTime"] = ServiceTime()
        savedStateHandle["therapistAvailableTimes"] = arrayListOf<ServiceTime>()
        savedStateHandle["day"] = CalendarDataSource().today.dayOfMonth
        savedStateHandle["month"] = CalendarDataSource().today.monthNumber
        savedStateHandle["year"] = CalendarDataSource().today.year

    }

    fun setNewSelectedTime(serviceTime: ServiceTime) {
        savedStateHandle["newSelectedTime"] = serviceTime
    }

}