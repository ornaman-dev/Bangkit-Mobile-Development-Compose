package com.example.ornamancompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ornamancompose.di.DependencyInjector

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScanViewModel::class.java)){
            return ScanViewModel(DependencyInjector.provideRepostiory()) as T
        }else if(modelClass.isAssignableFrom(AuthViewModel::class.java)){
            return AuthViewModel(DependencyInjector.provideRepostiory()) as T
        }else if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(DependencyInjector.provideRepostiory()) as T
        }
        throw IllegalArgumentException("Unknown view model")
    }

    companion object{
        private var INSTANCE : ViewModelFactory? = null
        fun getInstance() : ViewModelFactory{
            if(INSTANCE == null){
                INSTANCE = ViewModelFactory()
            }
            return INSTANCE!!
        }
    }
}