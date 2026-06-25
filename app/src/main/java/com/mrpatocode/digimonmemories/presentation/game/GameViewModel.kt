package com.mrpatocode.digimonmemories.presentation.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.domain.model.Score
import com.mrpatocode.digimonmemories.domain.usecase.GetFeaturedDigimonUseCase
import com.mrpatocode.digimonmemories.domain.usecase.InsertScoreGetUseCase
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getFeaturedDigimonUseCase: GetFeaturedDigimonUseCase,
    private val insertScoreGetUseCase: InsertScoreGetUseCase
) : ViewModel() {

    private val _featuredDigimon = MutableLiveData<Resource<List<Digimon>>>()
    val featuredDigimon: LiveData<Resource<List<Digimon>>> = _featuredDigimon

    private val _successes = MutableLiveData(0)
    val success: LiveData<Int> get() = _successes

    private val _attempts = MutableLiveData(0)

    private val _errors = MutableLiveData(0)

    private val _changes = MutableLiveData(0)

    private val _level = MutableLiveData(1)
    val level: LiveData<Int> get() = _level

    private val _hearts = MutableLiveData(3)
    val hearts: LiveData<Int> get() = _hearts

    private val _hints = MutableLiveData(1)
    val hints: LiveData<Int> get() = _hints

    private val _stateChange = MutableLiveData(false)
    val stateChange: LiveData<Boolean> get() = _stateChange

    private val _stateSaveStatistics = MutableLiveData<Resource<Unit>>()
    val stateSaveStatistics: LiveData<Resource<Unit>> = _stateSaveStatistics

    private fun getFeaturedDigimon(count: Int) {
        viewModelScope.launch {
            _featuredDigimon.value = Resource.Loading()
            _featuredDigimon.value = getFeaturedDigimonUseCase(count)
        }
    }

    fun currentGameLevel() {
        val currentSuccess = _successes.value ?: 0

        when {
            currentSuccess <= 3 -> {
                getFeaturedDigimon(2)
            }
            currentSuccess <= 6 -> {
                getFeaturedDigimon(4)
            }
            else -> {
                getFeaturedDigimon(6)
            }
        }
    }

    fun incrementSuccess() {
        val currentSuccess = (_successes.value ?: 0) + 1
        _successes.value = currentSuccess

        _level.value = (_level.value ?: 1) + 1
        _stateChange.value = true

        if (currentSuccess % 3 == 0) {
            _hints.value = (_hints.value ?: 1) + 1
        }

        if (currentSuccess % 5 == 0) {
            _hearts.value = (_hearts.value ?: 3) + 1
        }
    }

    fun incrementErrors() {
        _errors.value = (_errors.value ?: 0) + 1
        _hearts.value = (_hearts.value ?: 3) - 1
    }

    fun incrementAttempts() {
        _attempts.value = (_attempts.value ?: 0) + 1
    }

    fun subtractHints() {
        _hints.value = (_hints.value ?: 1) - 1
    }

    fun incrementChanges() {
        _changes.value = (_changes.value ?: 0) + 1
    }

    fun disableChange() {
        _changes.value = (_changes.value ?: 0) + 1
        _stateChange.value = false
    }

    fun resetStatistics() {
        _successes.value = 0
        _attempts.value = 0
        _errors.value = 0
        _changes.value = 0
        _level.value = 1
        _hearts.value = 3
        _hints.value = 1
        _stateChange.value = false
    }

    fun saveStatistics() {
        _stateSaveStatistics.value = Resource.Loading()
        val score = Score(
            success = _successes.value ?: 0,
            attempts = _attempts.value ?: 0,
            errors = _errors.value ?: 0,
            level = _level.value ?: 1
        )

        viewModelScope.launch {
            _stateSaveStatistics.value = insertScoreGetUseCase(score)
        }
    }
}