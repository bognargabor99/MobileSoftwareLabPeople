package hu.bme.aut.mszl.people.ui.screen.list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.mszl.people.ui.screen.model.PeopleListUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleListViewModel @Inject constructor(
    private val repository: PeopleListRepository
) : ViewModel() {
    var peopleListState: PeopleListUiState by mutableStateOf(PeopleListUiState.Loading)
        private set

    private var currentCategory: String = ""
        set(value) {
            field = value
            refreshPeople()
        }

    var allCategories: List<String> by mutableStateOf(listOf())

    init {
        getPeople()
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            allCategories = repository.getCategoryNames()
        }
    }

    fun refreshPeople() {
        peopleListState = PeopleListUiState.Loading
        if (currentCategory.isBlank())
            getPeople()
        else
            refreshPeopleFromCategory()
    }

    fun setChosenCategory(category: String) {
        currentCategory = category
    }

    private fun refreshPeopleFromCategory() {
        viewModelScope.launch {
            val peopleInCategory = repository.getCategory(currentCategory).people
            peopleListState = PeopleListUiState.Success(peopleInCategory)
        }
        fetchCategories()
    }

    private fun getPeople() {
        viewModelScope.launch {
            peopleListState = try {
                val result = repository.getPeople()
                PeopleListUiState.Success(result)
            } catch (e: Exception) {
                Log.d("peoplelist", "Error:${e.localizedMessage}")
                PeopleListUiState.Error
            }
        }
    }
}