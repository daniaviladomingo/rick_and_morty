package avila.daniel.data_cache_preference.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterStatusFilter

class PreferenceStatusMapper(
    private val alive: String,
    private val dead: String,
    private val unknown: String,
    private val any: String
) : Mapper<String, CharacterStatusFilter>() {
    override fun map(model: String): CharacterStatusFilter = model.run {
        when {
            this == alive -> {
                CharacterStatusFilter.ALIVE
            }
            this == dead -> {
                CharacterStatusFilter.DEAD
            }
            this == unknown -> {
                CharacterStatusFilter.UNKNOWN
            }
            this == any -> {
                CharacterStatusFilter.ANY
            }
            else -> {
                CharacterStatusFilter.ANY
            }
        }
    }

    override fun inverseMap(model: CharacterStatusFilter): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}