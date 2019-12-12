package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterFilterGenderParameter

class GenderParameterMapper(
    private val male: String,
    private val female: String,
    private val genderless: String,
    private val unknown: String,
    private val any: String
) : Mapper<CharacterFilterGenderParameter, String>() {
    override fun map(model: CharacterFilterGenderParameter): String = model.run {
        when (this) {
            CharacterFilterGenderParameter.MALE -> male
            CharacterFilterGenderParameter.FEMALE -> female
            CharacterFilterGenderParameter.GENDERLESS -> genderless
            CharacterFilterGenderParameter.UNKNOWN -> unknown
            CharacterFilterGenderParameter.ANY -> any
        }
    }

    override fun inverseMap(model: String): CharacterFilterGenderParameter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}