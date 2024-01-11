package com.ilyanvk.meditation.presentation.timerscreen

interface TimerControl {
    fun onStart()
    fun onPause()
    fun onResume()
    fun onEnd()
    fun onTick(millisUntilFinished: Long)
    fun onCancel()
}
