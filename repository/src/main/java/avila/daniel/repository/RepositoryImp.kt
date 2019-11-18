package avila.daniel.repository

import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.IDataRemote
import io.reactivex.Singlefromfrom

class RepositoryImp(
    private val dataRemote: IDataRemote
): IRepository {
    override fun getCharacters(page: Int): Single<List<Character>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCharacter(id: Int): Single<Character> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocations(page: Int): Single<List<Location>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocation(id: Int): Single<Location> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEpisodes(page: Int): Single<List<Episode>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEpisode(id: Int): Single<Episode> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}