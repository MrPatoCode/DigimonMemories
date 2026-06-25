package com.mrpatocode.digimonmemories.presentation.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.domain.usecase.DeleteScoreUseCase
import com.mrpatocode.digimonmemories.domain.usecase.GetAllScoresUseCase
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(
    private val getAllScoresUseCase: GetAllScoresUseCase,
    private val deleteScoreUseCase: DeleteScoreUseCase
): ViewModel() {
    private var realList: List<Score> = emptyList()
    private val _statistics = MutableLiveData<Resource<List<Score>>>()
    val statistics: LiveData<Resource<List<Score>>> = _statistics

    fun getStatistics() {
        _statistics.value = Resource.Loading()
        viewModelScope.launch {
            _statistics.value = getAllScoresUseCase()
            realList = _statistics.value?.data.orEmpty()
        }
    }

    fun orderStatistics(order: String) {
        val list = _statistics.value?.data.orEmpty()
        val orderList = when (order) {
            "success" -> Resource.Success(list.sortedByDescending { it.success })
            "attempts" -> Resource.Success(list.sortedByDescending { it.attempts })
            "errors" -> Resource.Success(list.sortedByDescending { it.errors })
            else -> Resource.Success(realList)
        }
        viewModelScope.launch {
            _statistics.value = orderList
        }
    }

    fun deleteScore(score: Score) {
        viewModelScope.launch {
            deleteScoreUseCase(score).let {
                if (it is Resource.Success){
                    getStatistics()
                } else if (it is Resource.Error){
                    _statistics.value = Resource.Error(it.message ?: "")
                }
            }
        }
    }
}