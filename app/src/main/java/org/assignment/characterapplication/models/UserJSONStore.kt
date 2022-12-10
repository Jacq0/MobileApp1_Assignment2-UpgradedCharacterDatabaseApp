package org.assignment.characterapplication.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.assignment.characterapplication.helpers.*
import org.assignment.characterapplication.interfaces.UserInterface
import java.lang.reflect.Type
import kotlin.collections.ArrayList


const val USER_JSON = "users.json"
val userGsonBuilder: Gson = GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()

val typeList: Type = object: TypeToken<ArrayList<UserModel>>() {}.type

class UserJSONStore(private val context: Context): UserInterface
{
    var users = mutableListOf<UserModel>()

    init
    {
        if(exists(context, USER_JSON))
        {
            deserialize()
        }
    }

    //if we get a null return we can create the new userModel
    override fun getUser(id: String): UserModel?
    {
        val u: UserModel? = users.find { it.id == id}

        return u
    }

    override fun create(id: String) {
        val user = UserModel(id, mutableListOf<Long>())

        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        val u = users.find { it.id == user.id }

        if(u != null)
        {
            u.favourites = user.favourites //update user favourites

            serialize()
        }
    }


    private fun serialize() {
        val jsonString = userGsonBuilder.toJson(users, typeList)
        write(context, USER_JSON, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, USER_JSON)
        users = userGsonBuilder.fromJson(jsonString, typeList)
    }
}


