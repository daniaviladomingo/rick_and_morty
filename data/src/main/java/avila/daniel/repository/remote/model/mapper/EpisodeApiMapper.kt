package avila.daniel.repository.remote.model.mapper

import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.repository.remote.model.EpisodeApi

class EpisodeApiMapper : Mapper<EpisodeApi, Episode>() {
    override fun map(model: EpisodeApi): Episode = model.run {
        Episode(air_date, characters, created, episode, id, name, url)
    }

    override fun inverseMap(model: Episode): EpisodeApi {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}