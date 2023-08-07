package com.example.sportapp.screens.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.data.DataOrException
import com.example.sportapp.data.Resource
import com.example.sportapp.modul.*
import com.example.sportapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(private val repository: AppRepository):ViewModel() {
    suspend fun getHeadToHead(id:Int):Resource<Stats>{
        return repository.getHeadToHead(id)
    }

    suspend fun getPredictionDetails(id: Int):Resource<PredictionDetails>{
        return repository.getPreditionDetails(id)
    }

}