package hu.bme.aut.mszl.people.ui.screen.model

import hu.bme.aut.mszl.people.persistence.model.PersonEntity

sealed interface PeopleListUiState {
    data class Success(val people: List<PersonEntity>) : PeopleListUiState
    object Loading : PeopleListUiState
    object Error : PeopleListUiState
}