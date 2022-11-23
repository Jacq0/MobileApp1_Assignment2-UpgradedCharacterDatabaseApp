package org.assignment.characterapplication.main

import android.app.Application
import org.assignment.characterapplication.models.CharacterMemStore
import timber.log.Timber
import timber.log.Timber.i

class Main: Application()
{
    val characters = CharacterMemStore()

    override fun onCreate()
    {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Character Database App Started")
    }
}