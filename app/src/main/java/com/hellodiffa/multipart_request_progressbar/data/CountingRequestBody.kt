package com.hellodiffa.multipart_request_progressbar.data

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*

class CountingRequestBody(
    private val delegate: RequestBody,
    private val onRequestProgress: OnRequestProgress
) : RequestBody() {

    override fun contentType(): MediaType? = delegate.contentType()


    override fun writeTo(sink: BufferedSink) {
        val countingSink = CountingSink(sink)
        val bufferedSink = countingSink.buffer()

        delegate.writeTo(bufferedSink)

        bufferedSink.flush()
    }

    override fun contentLength(): Long {
        try {
            return delegate.contentLength()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return -1
    }

    inner class CountingSink(delegate: Sink) : ForwardingSink(delegate) {
        private var bytesWritten = 0L

        override fun write(source: Buffer, byteCount: Long) {
            super.write(source, byteCount)
            bytesWritten += byteCount
            onRequestProgress(bytesWritten, contentLength())
        }
    }

}

typealias OnRequestProgress = (bytesWritten: Long, contentLength: Long) -> Unit