package com.example.triviafutbol.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.triviafutbol.data.repository.TriviaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RankingViewModel(
    private val repository: TriviaRepository = TriviaRepository()
) : ViewModel() {

    private val _rankingList = MutableStateFlow<List<RankingUser>>(emptyList())
    val rankingList: StateFlow<List<RankingUser>> = _rankingList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isWeeklyTab = MutableStateFlow(true)
    val isWeeklyTab: StateFlow<Boolean> = _isWeeklyTab.asStateFlow()

    init {
        loadRanking()
    }

    fun setTab(isWeekly: Boolean) {
        _isWeeklyTab.value = isWeekly
        loadRanking()
    }

    fun loadRanking() {
        viewModelScope.launch {
            _isLoading.value = true
            val list = repository.fetchLeaderboard()
            _rankingList.value = list
            _isLoading.value = false
        }
    }
}
