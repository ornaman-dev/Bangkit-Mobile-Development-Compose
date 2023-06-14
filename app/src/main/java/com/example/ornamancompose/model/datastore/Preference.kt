package com.example.ornamancompose.model.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ornamancompose.model.remote.LoginResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")

class AuthPreferences private constructor(private val dataStore : DataStore<Preferences>){

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val NAME_KEY = stringPreferencesKey("name")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val ID_KEY = stringPreferencesKey("id")

    fun getUserSession() : Flow<LoginResponse>{
        return dataStore.data.map { preference ->
            LoginResponse(
                name = preference[NAME_KEY] ?: "",
                email = preference[EMAIL_KEY] ?: "",
                accessToken = preference[TOKEN_KEY] ?: "",
                id = preference[ID_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user : LoginResponse){
        dataStore.edit { preference ->
            preference[TOKEN_KEY] = user.accessToken
            preference[NAME_KEY] = user.name
            preference[EMAIL_KEY] = user.email
            preference[ID_KEY] = user.id
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