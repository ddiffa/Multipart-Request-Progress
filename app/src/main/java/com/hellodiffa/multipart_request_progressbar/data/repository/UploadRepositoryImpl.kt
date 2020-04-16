package com.hellodiffa.multipart_request_progressbar.data.repository

import com.hellodiffa.multipart_request_progressbar.domain.UploadRepository
import com.hellodiffa.multipart_request_progressbar.remote.ApiService
import com.hellodiffa.multipart_request_progressbar.remote.response.EventResponse
import com.hellodiffa.multipart_request_progressbar.utils.createMultiPartBody
import com.hellodiffa.multipart_request_progressbar.utils.createMultipartBody
import com.hellodiffa.multipart_request_progressbar.utils.createRequestBody
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Single

class UploadRepositoryImpl(private val api: ApiService) : UploadRepository {

    override fun uploadImage(filePath: String): Flowable<Double?> =
        Flowable.create({ emitter: FlowableEmitter<Double?> ->
            try {
                val response = api.uploadFile(
                    "name".createRequestBody(),
                    "20".createRequestBody(),
                    "2".createRequestBody(),
                    "2019-11-05T01:37:33.473224+00:00".createRequestBody(),
                    "Indonesia".createRequestBody(),
                    "Upload File".createRequestBody(),
                    "Technology".createRequestBody(),
                    "Mark".createRequestBody(),
                    createMultipartBody(filePath, emitter)
                ).blockingGet()

                emitter.onComplete()
            } catch (e: Exception) {
                emitter.tryOnError(e)
            }
        }, BackpressureStrategy.LATEST)

    override fun uploadImageWithoutProgress(filePath: String): Single<EventResponse> =
        api.uploadFile(
            "name".createRequestBody(),
            "20".createRequestBody(),
            "2".createRequestBody(),
            "2019-11-05T01:37:33.473224+00:00".createRequestBody(),
            "Indonesia".createRequestBody(),
            "Upload File".createRequestBody(),
            "Technology".createRequestBody(),
            "Mark".createRequestBody(),
            filePath.createMultiPartBody()
        )
}