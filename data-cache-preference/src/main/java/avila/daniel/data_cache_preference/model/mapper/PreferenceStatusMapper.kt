package avila.daniel.data_cache_preference.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterFilterStatusParameter

class PreferenceStatusMapper(
    private val alive: String,
    private val dead: String,
    private val unknown: String,
    private val any: String
) : Mapper<String, CharacterFilterStatusParameter>() {
    override fun map(model: String): CharacterFilterStatusParameter = model.run {
        when {
            this == alive -> {
                CharacterFilterStatusParameter.ALIVE
            }
            this == dead -> {
                CharacterFilterStatusParameter.DEAD
            }
            this == unknown -> {
                CharacterFilterStatusParameter.UNKNOWN
            }
            this == any -> {
                CharacterFilterStatusParameter.ANY
            }
            else -> {
                CharacterFilterStatusParameter.ANY
            }
        }
    }

    override fun inverseMap(model: CharacterFilterStatusParameter): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}