package com.hellodiffa.multipart_request_progressbar.di

import com.hellodiffa.multipart_request_progressbar.data.repository.UploadRepositoryImpl
import com.hellodiffa.multipart_request_progressbar.domain.executor.SchedulerProvider
import com.hellodiffa.multipart_request_progressbar.presentation.MainViewModel
import com.hellodiffa.multipart_request_progressbar.remote.api.BaseApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val apiModule = module {
    single { BaseApi.provideHttpClient() }
    single { BaseApi.provideRetrofit(get()) }
    single { BaseApi.createService(get()) }
}

val provideExecutor = module {
    single { SchedulerProvider.DEFAULT }
}

val provideRepository = module {
    single { UploadRepositoryImpl(get()) }
}

val provideViewModel = module {
    viewModel { MainViewModel(get(), get()) }
}