package com.example.triviafutbol.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviafutbol.data.repository.TriviaRepository
import com.example.triviafutbol.domain.model.UserProfile
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: TriviaRepository = TriviaRepository()
) : ViewModel() {

    val userProfile: StateFlow<UserProfile> = repository.userProfile

    fun updateProfile(newUsername: String, newAvatar: String?) {
        viewModelScope.launch {
            repository.updateUserProfile(newUsername, newAvatar)
        }
    }

    fun triggerStreakSaverDemo() {
        repository.useStreakSaverDemo()
    }

    fun dismissStreakProtectedBanner() {
        repository.dismissStreakProtectedBanner()
    }
}
