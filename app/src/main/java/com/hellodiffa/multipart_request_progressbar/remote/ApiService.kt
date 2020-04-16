package com.hellodiffa.multipart_request_progressbar.remote

import com.hellodiffa.multipart_request_progressbar.remote.response.EventResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Multipart
    @POST("api/v1/events/")
    fun uploadFile(
        @Part("name") name: RequestBody?,
        @Part("quota") quota: RequestBody?,
        @Part("fk_userid") fkUserId: RequestBody?,
        @Part("date") date: RequestBody?,
        @Part("place") place: RequestBody?,
        @Part("detail") detail: RequestBody?,
        @Part("category") category: RequestBody?,
        @Part("talent") talent: RequestBody?,
        @Part file: MultipartBody.Part
    ): Single<EventResponse>
}