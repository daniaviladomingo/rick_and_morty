package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.cache.model.compose.CharacterStatusFilter

class StatusParameterMapper(
    private val alive: String,
    private val dead: String,
    private val unknown: String,
    private val any: String
) : Mapper<CharacterStatusFilter, String>() {
    override fun map(model: CharacterStatusFilter): String = model.run {
        when (this) {
            CharacterStatusFilter.ALIVE -> alive
            CharacterStatusFilter.DEAD -> dead
            CharacterStatusFilter.UNKNOWN -> unknown
            CharacterStatusFilter.ANY -> any
        }
    }

    override fun inverseMap(model: String): CharacterStatusFilter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}