package com.ilyanvk.meditation.presentation.timerscreen

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private val _millisecondsLeft = MutableLiveData<Long>()
    val millisecondsLeft: LiveData<Long> = _millisecondsLeft

    private val _timerIsRunning = MutableLiveData(false)
    val timerIsRunning: LiveData<Boolean> = _timerIsRunning

    private var timer: CountDownTimer? = null

    var timerControl: TimerControl? = null

    fun startTimer(milliseconds: Long) {
        timer = createTimer(milliseconds)
        timer?.start()
        _timerIsRunning.value = true
        timerControl?.onStart()
    }

    fun pauseTimer() {
        timer?.cancel()
        _timerIsRunning.value = false
        timerControl?.onPause()
    }

    fun resumeTimer() {
        timer = createTimer(_millisecondsLeft.value ?: 0)
        timer?.start()
        _timerIsRunning.value = true
        timerControl?.onResume()
    }

    fun cancelTimer() {
        timer?.cancel()
        _millisecondsLeft.value = 0
        _timerIsRunning.value = false
        timerControl?.onCancel()
    }

    private fun createTimer(milliseconds: Long) =
        object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _millisecondsLeft.value = millisUntilFinished
                timerControl?.onTick(millisUntilFinished)
            }

            override fun onFinish() {
                _millisecondsLeft.value = 0
                timerControl?.onEnd()
            }
        }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
}