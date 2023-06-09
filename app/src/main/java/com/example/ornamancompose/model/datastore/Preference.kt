package com.example.ornamancompose.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class User(
    val username : String,
    val email : String,
    val token : String
)

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")

class AuthPreferences private constructor(private val dataStore : DataStore<Preferences>){

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val EMAIL_KEY = stringPreferencesKey("email")

    fun getUserSession() : Flow<User>{
        return dataStore.data.map { preference ->
            User(
                username = preference[USERNAME_KEY] ?: "",
                email = preference[EMAIL_KEY] ?: "",
                token = preference[TOKEN_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user : User){
        dataStore.edit { preference ->
            preference[TOKEN_KEY] = user.token
            preference[USERNAME_KEY] = user.username
            preference[EMAIL_KEY] = user.email
        }
    }

    suspend fun clearSession() = dataStore.edit { preference ->
        preference.clear()
    }

    companion object{
        private var INSTANCE : AuthPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : AuthPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = AuthPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}