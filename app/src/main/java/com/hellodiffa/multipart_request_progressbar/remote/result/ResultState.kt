package com.hellodiffa.multipart_request_progressbar.remote.result

class ResultState<out T>(
    val status: Status,
    val data: T?,
    val error: Throwable?
) {

    companion object {
        fun <T> onSuccess(): ResultState<T> = ResultState(Status.SUCCESS, null, null)
        fun <T> onError(errorMessage: Throwable): ResultState<T> =
            ResultState(Status.ERROR, null, errorMessage)
        fun <T> onLoading(data: T?): ResultState<T> = ResultState(Status.LOADING, data, null)
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}