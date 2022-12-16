package org.assignment.characterapplication.interfaces

import org.assignment.characterapplication.models.CharacterModel
import org.assignment.characterapplication.models.UserModel

interface CharacterInterface {
    fun findAll(): List<CharacterModel>
    fun findByString(searched: String): List<CharacterModel>
    fun filterBy(filter: String, characters: List<CharacterModel>, user: UserModel): List<CharacterModel>
    fun create(character: CharacterModel)
    fun update(character: CharacterModel)
    fun delete(character: CharacterModel)
}