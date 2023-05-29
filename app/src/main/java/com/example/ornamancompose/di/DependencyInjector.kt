package com.example.ornamancompose.di

import com.example.ornamancompose.model.remote.ApiConfig
import com.example.ornamancompose.repository.Repository

object DependencyInjector {
    fun provideRepostiory() : Repository{
        val apiService = ApiConfig.getApiService()
        return Repository(apiService)
    }
}