package hu.bme.aut.mszl.people.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.bme.aut.mszl.people.R
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import hu.bme.aut.mszl.people.ui.screen.list.TopAppBarActionButton
import hu.bme.aut.mszl.people.ui.screen.model.PersonDetailsUiState
import hu.bme.aut.mszl.people.ui.util.CategorySaverDialog
import hu.bme.aut.mszl.people.ui.util.CategoryCreatorDialog
import hu.bme.aut.mszl.people.ui.util.SubstituteImage

@Composable
fun PeopleDetailsScreen(
    viewModel: PeopleDetailsViewModel = hiltViewModel(),
    person: PersonEntity,
    saved: Boolean
) {
    var savedToDb by remember { mutableStateOf(saved) }

    when (viewModel.detailsState) {
        PersonDetailsUiState.ChooseCategory -> {
            ChooseCategoryStateDialog(
                categories = viewModel.allCategories,
                onClose = { viewModel.detailsState = PersonDetailsUiState.None },
                onNewCategory = { viewModel.detailsState = PersonDetailsUiState.CreateCategory },
                onNonNewCategory = { chosenCategory ->
                    viewModel.savePersonToCategory(person, chosenCategory)
                    savedToDb = true
                }
            )
        }
        PersonDetailsUiState.CreateCategory -> {
            CategoryCreatorDialog(
                onClose = { viewModel.detailsState = PersonDetailsUiState.None },
                onCreate = { newCategory ->
                    viewModel.savePersonToCategory(person, newCategory)
                    savedToDb = true
                }
            )
        }
        else -> {}
    }
    Column {
        PeopleDetailsTopAppBar(
            name = person.name,
            saved = savedToDb,
            onSave = { viewModel.detailsState = PersonDetailsUiState.ChooseCategory },
            onDelete = {
                viewModel.deletePerson(person.id)
                savedToDb = false
            }
        )
        Divider()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubstituteImage(
                    name = person.name,
                    size = 200.dp,
                    fontSize = 80.sp
                )
                Text(
                    text = person.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                PersonPropertyList(person = person)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleDetailsTopAppBar(
    name: String,
    saved: Boolean,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    TopAppBar(
        title = { Text(name) },
        actions = {
            if (saved)
                TopAppBarActionButton(
                    imageVector = Icons.Default.Delete,
                    description = stringResource(id = R.string.delete),
                    onClick = onDelete
                )
            else
                TopAppBarActionButton(
                    imageVector = Icons.Default.Star,
                    description = stringResource(id = R.string.save),
                    onClick = onSave
                )
        },
    )
}

@Composable
fun PersonPropertyList(person: PersonEntity) {
    val propertyMap = mapOf(
        Icons.Default.LocationOn to person.location,
        Icons.Default.Face to person.gender,
        Icons.Default.Email to person.email,
        Icons.Default.DateRange to person.dob.substring(0, 10),
        Icons.Default.Phone to person.phone,
        Icons.Default.Home to person.nat
    )
    TableScreen(dataMap = propertyMap)
}

@Composable
fun TableScreen(dataMap: Map<ImageVector, String>) {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .background(Color.LightGray)
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TableCellText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.properties),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    weight = 1f
                )
            }
        }
        items(dataMap.size) {
            val vector = dataMap.keys.toList()[it]
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)) {
                TableCellIcon(imageVector = vector, weight = .2f)
                dataMap[vector]?.let { it -> TableCellText(text = it, weight = .8f) }
            }
        }
    }
}

@Composable
fun RowScope.TableCellText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal,
    weight: Float
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxHeight()
            .border(1.dp, Color.LightGray)
            .weight(weight)
            .padding(8.dp),
        textAlign = textAlign,
        fontWeight = fontWeight
    )
}

@Composable
fun RowScope.TableCellIcon(
    imageVector: ImageVector,
    weight: Float
) {
    Icon(
        modifier = Modifier
            .fillMaxHeight()
            .border(1.dp, Color.LightGray)
            .weight(weight)
            .padding(8.dp),
        imageVector = imageVector,
        contentDescription = null
    )
}

@Composable
fun ChooseCategoryStateDialog(
    categories: List<String>,
    onClose: () -> Unit,
    onNewCategory: () -> Unit,
    onNonNewCategory: (String) -> Unit
) {
    CategorySaverDialog(
        subtitle = stringResource(R.string.choose_category_detailed),
        categories = categories,
        closeDialog = onClose,
        onChoose = { chosenCategory ->
            if (chosenCategory == "New")
                onNewCategory()
            else
                onNonNewCategory(chosenCategory)
        }
    )
}