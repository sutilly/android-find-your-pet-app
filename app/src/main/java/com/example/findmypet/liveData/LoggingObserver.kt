package com.example.findmypet.liveData

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

class LoggingObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAnyEvent(lifecycleOwner: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(lifecycleOwner.toString(), event.toString())
    }
}