package org.assignment.characterapplication.interfaces

import org.assignment.characterapplication.models.CharacterModel

interface CharacterInterface {
    fun findAll(): List<CharacterModel>
    fun create(character: CharacterModel)
    fun update(character: CharacterModel)
}