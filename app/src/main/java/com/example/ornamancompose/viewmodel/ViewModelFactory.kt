package com.example.ornamancompose.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ornamancompose.di.DependencyInjector
import com.example.ornamancompose.repository.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(
    private val repository: Repository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScanViewModel::class.java)){
            return ScanViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(repository) as T
        }else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }

    companion object{
        private var INSTANCE : ViewModelFactory? = null
        fun getInstance(context: Context) : ViewModelFactory{
            if(INSTANCE == null){
                INSTANCE = ViewModelFactory(DependencyInjector.provideRepository(context))
            }
            return INSTANCE!!
        }
    }
}