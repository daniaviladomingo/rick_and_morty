package avila.daniel.rickmorty.ui.util.data

class Resource<out T> private constructor(val status: ResourceState, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(ResourceState.SUCCESS, data, null)

        fun <T> error(message: String?, data: T? = null): Resource<T> =
            Resource(ResourceState.ERROR, data, message ?: "Unknow error")

        fun <T> loading(): Resource<T> =
            Resource(ResourceState.LOADING, null, null)

        fun <T> empty(): Resource<T> =
            Resource(ResourceState.EMPTY, null, null)
    }
}