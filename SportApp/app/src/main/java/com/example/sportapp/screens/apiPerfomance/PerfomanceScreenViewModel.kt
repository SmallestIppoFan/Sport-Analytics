package com.example.sportapp.screens.apiPerfomance

import androidx.lifecycle.ViewModel
import com.example.sportapp.data.Resource
import com.example.sportapp.modul.PerfomanceDetails
import com.example.sportapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PerfomanceScreenViewModel @Inject constructor(private val repository: AppRepository):ViewModel() {
    suspend fun getApiPerfomance():Resource<PerfomanceDetails>{
        return repository.getApiPerfomance()
    }
}