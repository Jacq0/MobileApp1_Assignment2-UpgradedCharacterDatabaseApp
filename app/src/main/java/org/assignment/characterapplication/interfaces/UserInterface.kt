package org.assignment.characterapplication.interfaces

import org.assignment.characterapplication.models.UserModel

interface UserInterface
{
    fun getUser(id: String): UserModel?
    fun create(id: String)
    fun update(user: UserModel)
}