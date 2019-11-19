package avila.daniel.domain.model.mapper

abstract class Mapper<M, P> {
    abstract fun map(model: M): P
    abstract fun inverseMap(model: P): M

    fun map(values: List<M>) = mutableListOf<P>().apply { values.forEach { add(map(it)) } }
    fun inverseMap(values: List<P>) = mutableListOf<M>().apply { values.forEach { add(inverseMap(it)) } }
}