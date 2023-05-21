package hu.bme.aut.mszl.people.ui.screen.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.mszl.people.R
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import hu.bme.aut.mszl.people.ui.screen.model.PeopleListUiState
import hu.bme.aut.mszl.people.ui.util.CategoryChooserDialog
import hu.bme.aut.mszl.people.ui.util.InfiniteCircularProgressBar
import hu.bme.aut.mszl.people.ui.util.SubstituteImage

@Composable
fun PeopleListScreen(
    peopleListViewModel: PeopleListViewModel = hiltViewModel(),
    onPersonClick: (PersonEntity) -> Unit
) {
    var showCategoryList: Boolean by remember { mutableStateOf(false) }

    if (showCategoryList) {
        CategoryChooserDialog(
            subtitle = stringResource(id = R.string.choose_category_view_detailed),
            categories = peopleListViewModel.allCategories,
            closeDialog = { showCategoryList = false },
            onChoose = {
                peopleListViewModel.setChosenCategory(it)
                showCategoryList = false
            }
        )
    }

    Column {
        PeopleListTopAppBar(
            onRefresh = { peopleListViewModel.refreshPeople() },
            onLoadList = { showCategoryList = true }
        )
        when (peopleListViewModel.peopleListState) {
            is PeopleListUiState.Error -> { ErrorScreen() }
            is PeopleListUiState.Loading -> { LoadingScreen() }
            is PeopleListUiState.Success -> {
                SuccessScreen(
                    people = (peopleListViewModel.peopleListState as PeopleListUiState.Success).people,
                    onPersonClick = onPersonClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListTopAppBar(
    onRefresh: () -> Unit,
    onLoadList: () -> Unit
) {
    TopAppBar(
        title = { Text(stringResource(R.string.people)) },
        actions = {
            TopAppBarActionButton(
                imageVector = Icons.Default.Refresh,
                description = stringResource(id = R.string.refresh),
                onClick = onRefresh
            )
            TopAppBarActionButton(
                imageVector = Icons.Default.List,
                description = stringResource(id = R.string.load),
                onClick = onLoadList
            )
        },
    )
}

@Composable
fun SuccessScreen(
    people: List<PersonEntity>,
    onPersonClick: (PersonEntity) -> Unit
) {
    PersonList(people = people, onPersonClick = onPersonClick)
}

@Composable
fun LoadingScreen() {
    ScreenContent {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InfiniteCircularProgressBar()
            Text(text = stringResource(R.string.loading))
        }
    }
}

@Composable
fun ErrorScreen() {
    ScreenContent {
        Text(text = stringResource(R.string.something_wrong))
    }
}

@Composable
fun PersonList(
    people: List<PersonEntity>,
    onPersonClick: (PersonEntity) -> Unit
) {
    LazyColumn {
        items(people.size) {
            PersonListItem(person = people[it], onClick = onPersonClick)
        }
    }
}

@Composable
fun PersonListItem(
    person: PersonEntity,
    onClick: (PersonEntity) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color(0xffe5e4e2))
            .clickable { onClick(person) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubstituteImage(name = person.name)
        Column {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp),
                text = person.name,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.padding(start = 12.dp, bottom = 12.dp),
                text = person.location
            )
        }
    }
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
private fun ScreenContent(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        contentAlignment = Alignment.TopStart
    ) {
        content()
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}