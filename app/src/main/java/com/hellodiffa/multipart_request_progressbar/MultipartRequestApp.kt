package com.hellodiffa.multipart_request_progressbar

import android.app.Application
import com.hellodiffa.multipart_request_progressbar.di.apiModule
import org.koin.core.context.startKoin

class MultipartRequestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(apiModule)
        }
    }
}