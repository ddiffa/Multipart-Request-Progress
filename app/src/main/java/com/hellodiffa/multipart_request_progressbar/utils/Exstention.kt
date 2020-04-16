package com.hellodiffa.multipart_request_progressbar.utils

import com.hellodiffa.multipart_request_progressbar.data.CountingRequestBody
import io.reactivex.FlowableEmitter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun String.createMultiPartBody(): MultipartBody.Part {
    val file = File(this)
    val requestBody = file.createRequestBody()
    return MultipartBody.Part.createFormData(
        name = "file",
        filename = file.name,
        body = requestBody
    )
}

fun File.createRequestBody(): RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), this)

fun String.createRequestBody(): RequestBody =
    RequestBody.create("text/plain".toMediaTypeOrNull(), this);

fun createMultipartBody(filePath: String, emitter: FlowableEmitter<Double?>): MultipartBody.Part {

    val file = File(filePath)

    return MultipartBody.Part.createFormData(
        "file",
        file.name,
        createCountingRequestBody(file, emitter)
    )
}


fun createCountingRequestBody(file: File, emitter: FlowableEmitter<Double?>): RequestBody {
    val requestBody = file.createRequestBody()
    return CountingRequestBody(requestBody) { bytesWritten, contentLength ->
        val progress = 1.0 * bytesWritten / contentLength
        emitter.onNext(progress)
    }
}