package avila.daniel.rickmorty.ui.model.mapper

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.CharacterUI

class CharacterUIMapper(
    private val maxNameLenght: Int
) : Mapper<Character, CharacterUI>() {
    override fun map(model: Character): CharacterUI = model.run {
        val fname =
            name.run { if (length > maxNameLenght) substring(0..maxNameLenght) + "..." else this }
        CharacterUI(fname, image, species)
    }

    override fun inverseMap(model: CharacterUI): Character {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}