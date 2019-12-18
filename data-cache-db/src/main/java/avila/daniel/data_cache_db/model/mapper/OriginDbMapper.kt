package avila.daniel.data_cache_db.model.mapper

import avila.daniel.data_cache_db.model.OriginDb
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.mapper.Mapper

class OriginDbMapper : Mapper<Origin, OriginDb>() {
    override fun map(model: Origin): OriginDb = model.run {
        OriginDb(name, url)
    }

    override fun inverseMap(model: OriginDb): Origin = model.run {
        Origin(name, url)
    }
}