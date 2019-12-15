package avila.daniel.rickmorty.ui.model.mapper

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.compose.Status
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.compose.GenderParcelable
import avila.daniel.rickmorty.ui.model.compose.LocationParcelable
import avila.daniel.rickmorty.ui.model.compose.OriginParcelable
import avila.daniel.rickmorty.ui.model.compose.StatusParcelable

class CharacterParcelableMapper : Mapper<Character, CharacterParcelable>() {
    override fun map(model: Character): CharacterParcelable = model.run {
        CharacterParcelable(
            created,
            episode,
            when (gender) {
                Gender.MALE -> GenderParcelable.MALE
                Gender.FEMALE -> GenderParcelable.FEMALE
                Gender.GENDERLESS -> GenderParcelable.GENDERLESS
                Gender.UNKNOWN -> GenderParcelable.UNKNOWN
            },
            id,
            image,
            LocationParcelable(location.name, location.url),
            name,
            OriginParcelable(origin.name, origin.url),
            species,
            when (status) {
                Status.ALIVE -> StatusParcelable.ALIVE
                Status.DEAD -> StatusParcelable.DEAD
                Status.UNKNOWN -> StatusParcelable.UNKNOWN
            },
            type,
            url
        )
    }

    override fun inverseMap(model: CharacterParcelable): Character = model.run {
        Character(
            created,
            episode,
            when (gender) {
                GenderParcelable.MALE -> Gender.MALE
                GenderParcelable.FEMALE -> Gender.FEMALE
                GenderParcelable.GENDERLESS -> Gender.GENDERLESS
                GenderParcelable.UNKNOWN -> Gender.UNKNOWN
            },
            id,
            image,
            LocationCompose(location.name, location.url),
            name,
            Origin(origin.name, origin.url),
            species,
            when (status) {
                StatusParcelable.ALIVE -> Status.ALIVE
                StatusParcelable.DEAD -> Status.DEAD
                StatusParcelable.UNKNOWN -> Status.UNKNOWN
            },
            type,
            url
        )
    }
}