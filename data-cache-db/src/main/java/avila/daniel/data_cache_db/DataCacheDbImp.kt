package avila.daniel.data_cache_db

import avila.daniel.data_cache.db.IDataCacheDb
import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

class DataCacheDbImp: IDataCacheDb {
    override fun removeFavoriteCharacter(id: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFavoriteCharacter(character: Character): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllFavoriteCharacters(): Single<List<Character>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}