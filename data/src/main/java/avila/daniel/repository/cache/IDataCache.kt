package avila.daniel.repository.cache

import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterParameter
import io.reactivex.Completable
import io.reactivex.Single

interface IDataCache {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterParameter>

    fun getCharactersFavorites(): Single<List<Character>>
    fun addCharacterFavorite(character: Character): Completable
    fun removeCharacterFavorite(id: Int): Completable

    fun isFavorite(id: Int): Single<Boolean>
}