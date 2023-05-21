package hu.bme.aut.mszl.people.ui.screen.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import hu.bme.aut.mszl.people.ui.screen.model.PersonDetailsUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailsViewModel @Inject constructor(
    private val repository: PeopleDetailsRepository
) : ViewModel() {
    var detailsState: PersonDetailsUiState by mutableStateOf(PersonDetailsUiState.None)

    var allCategories: List<String> by mutableStateOf(listOf())
    var personId: Long = -1L

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            allCategories = repository.getCategoryNames()
        }
    }

    fun savePersonToCategory(person: PersonEntity, categoryName: String) {
        viewModelScope.launch {
            val alreadyPresentCategory = repository.getCategoryNames().contains(categoryName)
            val categoryId =
                if (!alreadyPresentCategory) repository.addCategory(categoryName)
                else repository.getCategoryByName(categoryName).id
            personId = repository.addPersonToCategory(categoryId, person)
        }.invokeOnCompletion {
            detailsState = PersonDetailsUiState.None
            fetchCategories()
        }
    }

    fun deletePerson(id: Long) {
        viewModelScope.launch {
            repository.deletePerson(if (id != 0L) id else personId)
        }
    }
}