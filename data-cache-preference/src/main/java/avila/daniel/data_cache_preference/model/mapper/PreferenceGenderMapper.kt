package avila.daniel.data_cache_preference.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterFilterGenderParameter

class PreferenceGenderMapper(
    private val male: String,
    private val female: String,
    private val genderless: String,
    private val unknown: String,
    private val any: String
) : Mapper<String, CharacterFilterGenderParameter>() {
    override fun map(model: String): CharacterFilterGenderParameter = model.run {
        when {
            this == male -> {
                CharacterFilterGenderParameter.MALE
            }
            this == female -> {
                CharacterFilterGenderParameter.FEMALE
            }
            this == genderless -> {
                CharacterFilterGenderParameter.GENDERLESS
            }
            this == unknown -> {
                CharacterFilterGenderParameter.UNKNOWN
            }
            this == any -> {
                CharacterFilterGenderParameter.ANY
            }
            else -> {
                CharacterFilterGenderParameter.ANY
            }
        }
    }

    override fun inverseMap(model: CharacterFilterGenderParameter): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}