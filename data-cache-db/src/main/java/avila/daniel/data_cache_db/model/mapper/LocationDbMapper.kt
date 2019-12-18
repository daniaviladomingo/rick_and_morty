package avila.daniel.data_cache_db.model.mapper

import avila.daniel.data_cache_db.model.LocationDb
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.mapper.Mapper

class LocationDbMapper : Mapper<LocationCompose, LocationDb>() {
    override fun map(model: LocationCompose): LocationDb = model.run {
        LocationDb(name, url)
    }

    override fun inverseMap(model: LocationDb): LocationCompose = model.run {
        LocationCompose(name, url)
    }
}