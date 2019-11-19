package avila.daniel.rickmorty.ui.model.mapper

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.ui.model.CharacterUI

class CharacterUIMapper: Mapper<Character, CharacterUI>() {
    override fun map(model: Character): CharacterUI = model.run {
        CharacterUI(name, image, species, status)
    }

    override fun inverseMap(model: CharacterUI): Character {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}