package ru.movie.app.ui.models

sealed class State {
    object EmptyState : State()
    object LoadingState : State()
    data class FinishState(val message: String) : State()
    data class ErrorState(val message: String) : State()
}
