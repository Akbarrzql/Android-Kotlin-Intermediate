package com.example.learnkotlinintermediate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.learnkotlinintermediate.repository.Respositry

class MainViewModelFactory(private val repository: Respositry) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
