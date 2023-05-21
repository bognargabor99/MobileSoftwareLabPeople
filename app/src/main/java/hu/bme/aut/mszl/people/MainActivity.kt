package hu.bme.aut.mszl.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.mszl.people.navigation.PeopleDetails
import hu.bme.aut.mszl.people.navigation.PeopleList
import hu.bme.aut.mszl.people.persistence.model.PersonEntity
import hu.bme.aut.mszl.people.ui.screen.details.PeopleDetailsScreen
import hu.bme.aut.mszl.people.ui.screen.list.PeopleListScreen
import hu.bme.aut.mszl.people.ui.theme.PeopleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PeopleApp()
        }
    }
}

@Composable
fun PeopleApp(
    modifier: Modifier = Modifier
) {
    PeopleTheme {
        val navController = rememberNavController()
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = PeopleList.route
        ) {
            composable(route = PeopleList.route) {
                PeopleListScreen() {
                    val url = PeopleDetails.route
                        .replace("{id}", it.id.toString())
                        .replace("{saved}", (it.categoryId != -1L).toString())
                        .replace("{categoryId}", it.categoryId.toString())
                        .replace("{gender}", it.gender)
                        .replace("{name}", it.name)
                        .replace("{location}", it.location)
                        .replace("{email}", it.email)
                        .replace("{dob}", it.dob)
                        .replace("{phone}", it.phone)
                        .replace("{nat}", it.nat)
                    navController.navigateSingleTopTo(url)
                }
            }
            composable(
                route = PeopleDetails.route,
                arguments = listOf(
                    navArgument("id") { type = NavType.LongType },
                    navArgument("saved") { type = NavType.BoolType },
                    navArgument("categoryId") { type = NavType.LongType },
                    navArgument("gender") { type = NavType.StringType },
                    navArgument("name") { type = NavType.StringType },
                    navArgument("location") { type = NavType.StringType },
                    navArgument("email") { type = NavType.StringType },
                    navArgument("dob") { type = NavType.StringType },
                    navArgument("phone") { type = NavType.StringType },
                    navArgument("nat") { type = NavType.StringType },

                )
            ) { backStackEntry ->
                val saved = backStackEntry.arguments?.getBoolean("saved")!!
                val person = PersonEntity(
                    id = backStackEntry.arguments?.getLong("id")!!,
                    categoryId = backStackEntry.arguments?.getLong("categoryId")!!,
                    gender = backStackEntry.arguments?.getString("gender")!!,
                    name = backStackEntry.arguments?.getString("name")!!,
                    location = backStackEntry.arguments?.getString("location")!!,
                    email = backStackEntry.arguments?.getString("email")!!,
                    dob = backStackEntry.arguments?.getString("dob")!!,
                    picture_url = "",
                    phone = backStackEntry.arguments?.getString("phone")!!,
                    nat = backStackEntry.arguments?.getString("nat")!!,
                )
                PeopleDetailsScreen(person = person, saved = saved)
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }