package com.hellodiffa.multipart_request_progressbar.domain

import com.hellodiffa.multipart_request_progressbar.remote.response.EventResponse
import io.reactivex.Flowable
import io.reactivex.Single

interface UploadRepository {

    fun uploadImage(filePath: String): Flowable<Double?>

    fun uploadImageWithoutProgress(filePath: String): Single<EventResponse>
}