package avila.daniel.data_cache_db.model.mapper

import avila.daniel.data_cache_db.model.CharacterDb
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.mapper.Mapper

class CharacterMapperDb : Mapper<Character, CharacterDb>() {
    override fun map(model: Character): CharacterDb = model.run {
        CharacterDb(id, name, status, species, type, gender, image)
    }

    override fun inverseMap(model: CharacterDb): Character = model.run {
        Character(
            "",
            listOf(),
            gender,
            id,
            image,
            LocationCompose("", ""),
            name,
            Origin("", ""),
            species,
            status,
            type,
            ""
        )
    }
}