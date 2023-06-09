package com.example.ornamancompose.di

import android.content.Context
import com.example.ornamancompose.model.datastore.AuthPreferences
import com.example.ornamancompose.model.datastore.dataStore
import com.example.ornamancompose.model.remote.ApiConfig
import com.example.ornamancompose.repository.Repository

object DependencyInjector {
    fun provideRepository(context : Context) : Repository{
        val apiService = ApiConfig.getApiService()
        val preference = AuthPreferences.getInstance(context.dataStore)
        return Repository(apiService, preference)
    }
}