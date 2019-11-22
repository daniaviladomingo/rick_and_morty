package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.Status
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.remote.model.CharacterApi
import avila.daniel.repository.remote.model.EpisodeApi

class CharacterApiMapper : Mapper<CharacterApi, Character>() {
    override fun map(model: CharacterApi): Character = model.run {
        Character(
            created,
            episode,
            when (gender) {
                "Male" -> Gender.MALE
                "Female" -> Gender.FEMALE
                "Genderless" -> Gender.GENDERLESS
                else -> Gender.UNKNOWN
            },
            id,
            image,
            location,
            name,
            origin,
            species,
            when (status) {
                "Alive" -> Status.ALIVE
                "Dead" -> Status.DEAD
                else -> Status.UNKNOWN
            },
            type,
            url
        )
    }

    override fun inverseMap(model: Character): CharacterApi {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}