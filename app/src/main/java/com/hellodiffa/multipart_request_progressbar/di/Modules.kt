package com.hellodiffa.multipart_request_progressbar.di

import com.hellodiffa.multipart_request_progressbar.remote.BaseApi
import org.koin.dsl.module


val apiModule = module {
    single { BaseApi.provideHttpClient() }
    single { BaseApi.provideRetrofit() }
}