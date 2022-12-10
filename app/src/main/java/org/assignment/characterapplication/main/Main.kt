package org.assignment.characterapplication.main

import android.app.Application
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.assignment.characterapplication.interfaces.CharacterInterface
import org.assignment.characterapplication.interfaces.UserInterface
import org.assignment.characterapplication.models.CharacterJSONStore
import org.assignment.characterapplication.models.CharacterMemStore
import org.assignment.characterapplication.models.UserJSONStore
import org.assignment.characterapplication.models.UserModel
import timber.log.Timber
import timber.log.Timber.i

public class Main: Application()
{
    lateinit var characters : CharacterInterface
    lateinit var users: UserInterface
    lateinit var currentUser : UserModel

    override fun onCreate()
    {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        characters = CharacterJSONStore(applicationContext)
        users = UserJSONStore(applicationContext)
        //manageUser() fix this up for the favouriting
        i("Character Database App Started")
    }

    private fun manageUser()
    {
        if(users.getUser(Firebase.auth.currentUser!!.uid) == null)
        {
            users.create(Firebase.auth.currentUser!!.uid)
        }

        currentUser = users.getUser(Firebase.auth.currentUser!!.uid)!!

        i("Got current user")
    }
}