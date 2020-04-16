package com.hellodiffa.multipart_request_progressbar.presentation

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hellodiffa.multipart_request_progressbar.base.BaseViewModel
import com.hellodiffa.multipart_request_progressbar.data.repository.UploadRepositoryImpl
import com.hellodiffa.multipart_request_progressbar.domain.executor.SchedulerProvider
import com.hellodiffa.multipart_request_progressbar.remote.result.ResultState
import com.hellodiffa.multipart_request_progressbar.utils.ActualPath

class MainViewModel(
    private val scheduler: SchedulerProvider,
    private val repository: UploadRepositoryImpl
) : BaseViewModel() {


    private val _result = MutableLiveData<ResultState<Double>>()

    val result: LiveData<ResultState<Double>> get() = _result

    private fun setResult(result: ResultState<Double>) {
        _result.postValue(result)
    }

    internal fun uploadFile(uri: Uri, context: Context) {
        val filePath = ActualPath.getActualPath(context, uri)
        filePath?.let {
            compositeDisposable.add(
                repository.uploadImage(it)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .subscribe(
                        { progress ->
                            setResult(ResultState.onLoading(progress))
                        }, { throwable ->
                            setResult(ResultState.onError(throwable))
                        }, {
                            setResult(ResultState.onSuccess())
                        }
                    )
            )
        }
    }

    internal fun uploadFileWithoutProgress(uri: Uri, context: Context) {
        val filePath = ActualPath.getActualPath(context, uri)
        filePath?.let {
            compositeDisposable.add(
                repository.uploadImageWithoutProgress(it)
                    .subscribeOn(scheduler.io())
                    .observeOn(scheduler.ui())
                    .subscribe({
                        setResult(ResultState.onSuccess())
                    }, {
                        setResult(ResultState.onError(it))
                    })
            )
        }
    }
}
