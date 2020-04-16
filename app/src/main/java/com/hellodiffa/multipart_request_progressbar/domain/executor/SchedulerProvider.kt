package com.hellodiffa.multipart_request_progressbar.domain.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface SchedulerProvider {

    fun io(): Scheduler
    fun ui(): Scheduler

    companion object {
        val DEFAULT: SchedulerProvider = object :
            SchedulerProvider {
            override fun io(): Scheduler = Schedulers.io()

            override fun ui(): Scheduler = AndroidSchedulers.mainThread()

        }
    }
}