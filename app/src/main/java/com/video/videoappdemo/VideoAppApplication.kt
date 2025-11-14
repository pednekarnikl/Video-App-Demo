package com.video.videoappdemo

import android.app.Application
import com.video.videoappdemo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent

class VideoAppApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@VideoAppApplication)
        }
    }
}