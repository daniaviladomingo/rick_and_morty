package avila.daniel.rickmorty.ui.model.mapper

import avila.daniel.domain.model.Location
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.LocationUI

class LocationUIMapper : Mapper<Location, LocationUI>() {
    override fun map(model: Location): LocationUI = model.run {
        LocationUI(
            id,
            name,
            type,
            dimension.run { if (this == "unknown") "???" else this },
            residents.size
        )
    }

    override fun inverseMap(model: LocationUI): Location {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}