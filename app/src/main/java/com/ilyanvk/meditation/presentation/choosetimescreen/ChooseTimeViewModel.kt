package com.ilyanvk.meditation.presentation.choosetimescreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChooseTimeViewModel : ViewModel() {
    val selectedTime = MutableLiveData<Int>()

    fun updateSelectedTime(time: Int) {
        selectedTime.value = time
    }
}