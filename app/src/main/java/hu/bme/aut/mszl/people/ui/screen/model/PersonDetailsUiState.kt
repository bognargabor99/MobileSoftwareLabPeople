package hu.bme.aut.mszl.people.ui.screen.model

sealed interface PersonDetailsUiState {
    object None : PersonDetailsUiState
    object ChooseCategory : PersonDetailsUiState
    object CreateCategory : PersonDetailsUiState
}