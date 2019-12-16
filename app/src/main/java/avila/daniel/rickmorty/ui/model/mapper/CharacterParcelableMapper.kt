package avila.daniel.rickmorty.ui.model.mapper

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.compose.LocationParcelable
import avila.daniel.rickmorty.ui.model.compose.OriginParcelable

class CharacterParcelableMapper : Mapper<Character, CharacterParcelable>() {
    override fun map(model: Character): CharacterParcelable = model.run {
        CharacterParcelable(
            created,
            episode,
            gender,
            id,
            image,
            LocationParcelable(location.name, location.url),
            name,
            OriginParcelable(origin.name, origin.url),
            species,
            status,
            type,
            url
        )
    }

    override fun inverseMap(model: CharacterParcelable): Character = model.run {
        Character(
            created,
            episode,
            gender,
            id,
            image,
            LocationCompose(location.name, location.url),
            name,
            Origin(origin.name, origin.url),
            species,
            status,
            type,
            url
        )
    }
}