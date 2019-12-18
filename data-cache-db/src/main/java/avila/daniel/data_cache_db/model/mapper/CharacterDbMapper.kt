package avila.daniel.data_cache_db.model.mapper

import avila.daniel.data_cache_db.model.CharacterDb
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.mapper.Mapper

class CharacterDbMapper : Mapper<Character, CharacterDb>() {
    override fun map(model: Character): CharacterDb = model.run {
        CharacterDb(id, location.name, origin.name, name, status, species, type, gender, image)
    }

    override fun inverseMap(model: CharacterDb): Character {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}