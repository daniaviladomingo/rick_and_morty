package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.domain.model.settings.compose.CharacterFilterStatusParameter

class StatusParameterMapper(
    private val alive: String,
    private val dead: String,
    private val unknown: String,
    private val any: String
) : Mapper<CharacterFilterStatusParameter, String>() {
    override fun map(model: CharacterFilterStatusParameter): String = model.run {
        when (this) {
            CharacterFilterStatusParameter.ALIVE -> alive
            CharacterFilterStatusParameter.DEAD -> dead
            CharacterFilterStatusParameter.UNKNOWN -> unknown
            CharacterFilterStatusParameter.ANY -> any
        }
    }

    override fun inverseMap(model: String): CharacterFilterStatusParameter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}