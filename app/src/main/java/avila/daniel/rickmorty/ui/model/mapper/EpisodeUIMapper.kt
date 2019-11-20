package avila.daniel.rickmorty.ui.model.mapper

import android.util.Log
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.EpisodeUI

class EpisodeUIMapper(
    private val rangeSeason: IntRange,
    private val rangeEpisode: IntRange
) : Mapper<Episode, EpisodeUI>() {
    override fun map(model: Episode): EpisodeUI = model.run {
        val nEpisode = episode.substring(rangeEpisode)
        val nSeason = episode.substring(rangeSeason)
        EpisodeUI(name, nEpisode.toInt(), nSeason.toInt(), airDate, characters.size)
    }

    override fun inverseMap(model: EpisodeUI): Episode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}