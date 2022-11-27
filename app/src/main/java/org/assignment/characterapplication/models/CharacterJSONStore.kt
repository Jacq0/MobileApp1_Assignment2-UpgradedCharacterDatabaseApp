package org.assignment.characterapplication.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.assignment.characterapplication.helpers.*
import org.assignment.characterapplication.interfaces.CharacterInterface
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "characters.json"
val gsonBuilder: Gson = GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<CharacterModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class CharacterJSONStore(private val context: Context) : CharacterInterface {
    var characters = mutableListOf<CharacterModel>()

    init {
        if(exists(context, JSON_FILE))
        {
            deserialize()
        }
    }

    override fun findAll(): List<CharacterModel> {
        logAll()
        return characters
    }

    override fun create(character: CharacterModel) {
        character.id = generateRandomId()
        characters.add(character)
        serialize()
    }

    override fun update(character: CharacterModel) {
        for(char in characters)
        {
            if(char.id == character.id)
            {
                char.name = character.name
                char.description = character.description
                char.originalAppearance = character.originalAppearance
                char.originalAppearanceYear = character.originalAppearanceYear
                char.image = character.image

                serialize()
            }
        }
    }

    override fun delete(character: CharacterModel)
    {
        for (char in characters)
        {
            if (char.id == character.id)
            {
                characters.remove(char);

                serialize()
            }
        }
    }


    private fun serialize() {
        val jsonString = gsonBuilder.toJson(characters, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        characters = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll()
    {
        characters.forEach { Timber.i("$it")}
    }
}

class UriParser: JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}