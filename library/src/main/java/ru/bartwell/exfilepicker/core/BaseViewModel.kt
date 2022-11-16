package ru.bartwell.exfilepicker.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal open class BaseViewModel<State : Any>(initialState: State) {
    val stateFlow: StateFlow<State> get() = _stateFlow.asStateFlow()
    val state: State get() = stateFlow.value

    private val _stateFlow by lazy { MutableStateFlow(initialState) }

    fun updateState(transform: State.() -> State) {
        _stateFlow.value = transform.invoke(state)
    }
}
