package avila.daniel.data_cache_preference.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterGenderFilter

class PreferenceGenderMapper(
    private val male: String,
    private val female: String,
    private val genderless: String,
    private val unknown: String,
    private val any: String
) : Mapper<String, CharacterGenderFilter>() {
    override fun map(model: String): CharacterGenderFilter = model.run {
        when {
            this == male -> {
                CharacterGenderFilter.MALE
            }
            this == female -> {
                CharacterGenderFilter.FEMALE
            }
            this == genderless -> {
                CharacterGenderFilter.GENDERLESS
            }
            this == unknown -> {
                CharacterGenderFilter.UNKNOWN
            }
            this == any -> {
                CharacterGenderFilter.ANY
            }
            else -> {
                CharacterGenderFilter.ANY
            }
        }
    }

    override fun inverseMap(model: CharacterGenderFilter): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}