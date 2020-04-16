package com.hellodiffa.multipart_request_progressbar

import android.app.Application
import com.hellodiffa.multipart_request_progressbar.di.apiModule
import com.hellodiffa.multipart_request_progressbar.di.provideExecutor
import com.hellodiffa.multipart_request_progressbar.di.provideRepository
import com.hellodiffa.multipart_request_progressbar.di.provideViewModel
import org.koin.core.context.startKoin

class MultipartRequestApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            this@MultipartRequestApp
            modules(apiModule, provideExecutor, provideRepository, provideViewModel)
        }
    }
}