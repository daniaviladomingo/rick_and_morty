package avila.daniel.data_cache_db.model.mapper

import avila.daniel.data_cache_db.model.CharacterComplete
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.mapper.Mapper

class CharacterAllMapper(
    private val locationDbMapper: LocationDbMapper,
    private val originDbMapper: OriginDbMapper
) : Mapper<CharacterComplete, Character>() {
    override fun map(model: CharacterComplete): Character = model.run {
        Character(
            "",
            listOf(),
            character.gender,
            character.id,
            character.image,
            locationDbMapper.inverseMap(location),
            character.name,
            originDbMapper.inverseMap(origin),
            character.species,
            character.status,
            character.type,
            ""
        )
    }

    override fun inverseMap(model: Character): CharacterComplete {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}