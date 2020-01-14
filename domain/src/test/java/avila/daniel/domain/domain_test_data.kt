package avila.daniel.domain

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.compose.Status

val TEST_CHARACTER = Character("", listOf(), Gender.MALE, 0, "", LocationCompose("", ""), "", Origin("", ""), "", Status.DEAD, "", "")
val TEST_EPISODE = Episode("", listOf(), "", "", 0, "", "")
val TEST_LOCATION = Location("", "", 0, "", listOf(), "", "")

val LIST_CHARACTER = listOf(TEST_CHARACTER)
val LIST_EPISODE = listOf(TEST_EPISODE)
val LIST_LOCATION = listOf(TEST_LOCATION)

const val ID_CHARACTER = 1
const val ID_EPISODE = 1
const val ID_LOCATION = 1

const val CHARACTERS_PAGE = 1
const val EPISODE_PAGE = 1
const val LOCATION_PAGE = 1
