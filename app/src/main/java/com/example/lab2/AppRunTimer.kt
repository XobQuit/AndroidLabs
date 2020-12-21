package com.example.lab2

import android.os.CountDownTimer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class AppRunTimer(lifecycle: Lifecycle) : LifecycleObserver {
    private lateinit var appLiveTimer: CountDownTimer
    private lateinit var appActiveTimer: CountDownTimer

    private var appActiveTime: Long = 0
    private var appLifetime: Long = 0

    init {
        lifecycle.addObserver(this)

        appLiveTimer = object : CountDownTimer(100000, 1000) {
            override fun onFinish() {
                //Timber.i("TIMER RESTART")
                start() // restart timer
            }

            override fun onTick(p0: Long) {
                appLifetime += 1
                //Timber.i("LIVE TIMER TICK")
            }
        }

        appActiveTimer = object : CountDownTimer(100000, 1000) {
            override fun onFinish() {
                //Timber.i("TIMER RESTART")
                start() // restart timer
            }

            override fun onTick(p0: Long) {
                appActiveTime += 1
                //Timber.i("ACTIVE TIMER TICK")
            }
        }

    }

    fun getLifetime() : Long {
        return appLifetime
    }

    fun getActiveTime() : Long {
        return appActiveTime
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        Timber.i("start")

        appActiveTimer.start()
        appLiveTimer.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Timber.i("stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        Timber.i("pause")
        Timber.i("App active time was " + appActiveTime + " seconds")
        appActiveTimer.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        appLiveTimer.cancel()
        Timber.i("App lifetime was " + appLifetime + " seconds")
        Timber.i("App active time percentege: " + (appActiveTime / appLifetime * 100))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        Timber.i("resume")
        appActiveTimer.start()
    }
}