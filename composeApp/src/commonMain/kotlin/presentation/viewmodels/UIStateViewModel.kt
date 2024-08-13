package presentation.viewmodels

import UIStates.ActionUIStates
import UIStates.ScreenUIStates
import com.hoc081098.kmp.viewmodel.SavedStateHandle
import com.hoc081098.kmp.viewmodel.ViewModel
import kotlinx.coroutines.flow.StateFlow


class UIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("screenUiState", ScreenUIStates())
    val uiStateInfo: StateFlow<ScreenUIStates>
        get() = _uiState

    fun switchScreenUIState(screenUiStates: ScreenUIStates) {
        savedStateHandle["screenUiState"] = screenUiStates
    }
}

class ActionUIStateViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    private var _uiState = savedStateHandle.getStateFlow("actionUiState", ActionUIStates())
    private var _postponeUiState = savedStateHandle.getStateFlow("postponeUiState", ActionUIStates())
    private var _deleteUiState = savedStateHandle.getStateFlow("deleteActionUiState", ActionUIStates())
    private var _availabilityUiState = savedStateHandle.getStateFlow("availabilityUiState", ActionUIStates())
    private var _switchVendorUiState = savedStateHandle.getStateFlow("switchVendorUiState", ActionUIStates())
    private var _getTherapistUiState = savedStateHandle.getStateFlow("getTherapistUiState", ActionUIStates())
    private var _loadPendingAppointmentUiState = savedStateHandle.getStateFlow("loadPendingAppointmentUiState", ActionUIStates())
    private var _deletePendingAppointmentUiState = savedStateHandle.getStateFlow("deletePendingAppointmentUiState", ActionUIStates())
    private var _createAppointmentUiState = savedStateHandle.getStateFlow("createAppointmentUiState", ActionUIStates())
    private var _joinMeetingUiState = savedStateHandle.getStateFlow("joinMeetingUiState", ActionUIStates())
    private var _therapistDashboardUiState = savedStateHandle.getStateFlow("therapistDashboardUiState", ActionUIStates())
    val postponeUIStateInfo: StateFlow<ActionUIStates>
        get() = _postponeUiState

    val deleteUIStateInfo: StateFlow<ActionUIStates>
        get() = _deleteUiState
    val loadPendingAppointmentUiState: StateFlow<ActionUIStates>
        get() = _loadPendingAppointmentUiState
    val createAppointmentUiState: StateFlow<ActionUIStates>
        get() = _createAppointmentUiState
    val deletePendingAppointmentUiState: StateFlow<ActionUIStates>
        get() = _deletePendingAppointmentUiState

    val therapistDashboardUiState: StateFlow<ActionUIStates>
        get() = _therapistDashboardUiState

    val getTherapistUiState: StateFlow<ActionUIStates>
        get() = _getTherapistUiState

    val availabilityStateInfo: StateFlow<ActionUIStates>
        get() = _availabilityUiState

    val switchVendorUiState: StateFlow<ActionUIStates>
        get() = _switchVendorUiState

    val uiStateInfo: StateFlow<ActionUIStates>
        get() = _uiState

    val joinMeetingStateInfo: StateFlow<ActionUIStates>
        get() = _joinMeetingUiState

    fun switchActionPostponeUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["postponeUiState"] = actionUIStates
    }
    fun switchActionDeleteUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["deleteActionUiState"] = actionUIStates
    }
    fun switchDeletePendingAppointmentUiState(actionUIStates: ActionUIStates) {
        savedStateHandle["deletePendingAppointmentUiState"] = actionUIStates
    }
    fun switchCreateAppointmentUiState(actionUIStates: ActionUIStates) {
        savedStateHandle["createAppointmentUiState"] = actionUIStates
    }

    fun switchActionLoadPendingAppointmentUiState(actionUIStates: ActionUIStates) {
        savedStateHandle["loadPendingAppointmentUiState"] = actionUIStates
    }

    fun switchActionAvailabilityUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["availabilityUiState"] = actionUIStates
    }

    fun switchActionMeetingUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["joinMeetingUiState"] = actionUIStates
    }
    fun switchGetTherapistUiState(actionUIStates: ActionUIStates) {
        savedStateHandle["getTherapistUiState"] = actionUIStates
    }
    fun switchActionTherapistDashboardUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["therapistDashboardUiState"] = actionUIStates
    }

    fun switchVendorActionUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["switchVendorUiState"] = actionUIStates
    }
    fun switchActionUIState(actionUIStates: ActionUIStates) {
        savedStateHandle["actionUiState"] = actionUIStates
    }


}