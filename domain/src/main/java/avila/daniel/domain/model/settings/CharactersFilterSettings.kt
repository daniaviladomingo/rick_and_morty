package avila.daniel.domain.model.settings

data class CharactersFilterSettings(
    val name: Boolean,
    val status: String,
    val specie: Boolean,
    val type: Boolean,
    val gender: String
)