package com.example.tiptime

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SumadorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReplyUiState())
    val uiState: StateFlow<ReplyUiState> = _uiState
    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        _uiState.value =
            ReplyUiState(
                isShowingHomepage = true
            )
    }
    fun updateSumScreenStates(num: ReplyUiState) {
        _uiState.update {
            it.copy(
                isShowingHomepage = false,
                sumador1=num.sumador1,
                sumador2=num.sumador2
            )
        }
    }

    fun resetHomeScreenStates() {
        _uiState.update {
            it.copy(
                isShowingHomepage = true,
                sumador1="",
                sumador2=""
            )
        }
    }
}