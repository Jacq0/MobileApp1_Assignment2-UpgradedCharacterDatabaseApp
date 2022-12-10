package org.assignment.characterapplication.main

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import org.assignment.characterapplication.interfaces.CharacterInterface
import org.assignment.characterapplication.models.CharacterJSONStore
import org.assignment.characterapplication.models.CharacterMemStore
import timber.log.Timber
import timber.log.Timber.i

public class Main: Application()
{
    lateinit var characters : CharacterInterface

    override fun onCreate()
    {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        characters = CharacterJSONStore(applicationContext)
        i("Character Database App Started")
    }
}