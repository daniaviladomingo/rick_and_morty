package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterGenderFilter

class GenderParameterMapper(
    private val male: String,
    private val female: String,
    private val genderless: String,
    private val unknown: String,
    private val any: String
) : Mapper<CharacterGenderFilter, String>() {
    override fun map(model: CharacterGenderFilter): String = model.run {
        when (this) {
            CharacterGenderFilter.MALE -> male
            CharacterGenderFilter.FEMALE -> female
            CharacterGenderFilter.GENDERLESS -> genderless
            CharacterGenderFilter.UNKNOWN -> unknown
            CharacterGenderFilter.ANY -> any
        }
    }

    override fun inverseMap(model: String): CharacterGenderFilter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}