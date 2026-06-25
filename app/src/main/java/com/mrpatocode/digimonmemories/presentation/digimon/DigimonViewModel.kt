package com.mrpatocode.digimonmemories.presentation.digimon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrpatocode.digimonmemories.domain.model.Digimon
import com.mrpatocode.digimonmemories.domain.usecase.CheckConnectivityUseCase
import com.mrpatocode.digimonmemories.domain.usecase.GetAllDigimonUseCase
import com.mrpatocode.digimonmemories.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DigimonViewModel @Inject constructor(
    private val checkConnectivityUseCase: CheckConnectivityUseCase,
    private val getAllDigimonUseCase: GetAllDigimonUseCase
) : ViewModel() {
    private val _allDigimon = MutableLiveData<Resource<List<Digimon>>>()
    val allDigimon: LiveData<Resource<List<Digimon>>> = _allDigimon

    fun loadDigimonList(){
        if (!checkConnectivityUseCase.invoke()){
            _allDigimon.value = Resource.Error("There is no internet connection")
            return
        }

        viewModelScope.launch{
            _allDigimon.value = Resource.Loading()
            _allDigimon.value = getAllDigimonUseCase()
        }
    }
}