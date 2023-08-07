package com.example.sportapp.screens.live

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.data.DataOrException
import com.example.sportapp.modul.Data
import com.example.sportapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class LiveScreenViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {
    val data: MutableState<DataOrException<Data, Boolean, Exception>>
    = mutableStateOf(DataOrException(Data(emptyList()),true,Exception("")))

    init {
        getData()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(){
        viewModelScope.launch {
            data.value.loading=true
            var date=getTodayDate()
            data.value=repository.getPredictions(date)
            if (!data.value.data?.data?.isNullOrEmpty()!!) data.value.loading=false
        }
        Log.d("SomeShitHere",data.value.e.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTodayDate(): String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return today.format(formatter)
    }


}