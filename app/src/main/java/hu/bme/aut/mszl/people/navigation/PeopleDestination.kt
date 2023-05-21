package hu.bme.aut.mszl.people.navigation

interface PeopleDestination {
    val route: String
}

object PeopleList : PeopleDestination {
    override val route = "list"
}

object PeopleDetails : PeopleDestination {
    override val route = "details?id={id}&saved={saved}&categoryId={categoryId}&gender={gender}&name={name}&location={location}&email={email}&dob={dob}&phone={phone}&nat={nat}"
}