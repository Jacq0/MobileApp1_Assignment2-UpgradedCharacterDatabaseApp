package org.assignment.characterapplication.models

import org.assignment.characterapplication.interfaces.CharacterInterface
import timber.log.Timber.i

var lastId = 0L

internal fun getNextId(): Long
{
    return lastId++;
}

class CharacterMemStore: CharacterInterface
{
    val characters = ArrayList<CharacterModel>()

    override fun findAll(): List<CharacterModel> {
        return characters;
    }

    override fun create(character: CharacterModel) {
        character.id = getNextId()
        characters.add(character)
        logAll()
    }

    override fun findByString(searched: String): List<CharacterModel> {
        TODO("Not yet implemented")
    }

    override fun update(character: CharacterModel) {
        TODO("Not yet implemented")
    }

    override fun delete(character: CharacterModel) {
        TODO("Not yet implemented")
    }

    override fun filterBy(
        filter: String,
        characters: List<CharacterModel>,
        user: UserModel
    ): List<CharacterModel> {
        TODO("Not yet implemented")
    }

    private fun logAll() {
        characters.forEach { i("$it") }
    }
}