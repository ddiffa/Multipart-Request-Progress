package com.hellodiffa.multipart_request_progressbar.remote.response

import com.google.gson.annotations.SerializedName

data class EventResponse(

	@field:SerializedName("event_detail")
	val eventDetail: String? = null,

	@field:SerializedName("event_place")
	val eventPlace: String? = null,

	@field:SerializedName("event_talent")
	val eventTalent: String? = null,

	@field:SerializedName("event_date")
	val eventDate: String? = null,

	@field:SerializedName("event_quota")
	val eventQuota: Int? = null,

	@field:SerializedName("event_name")
	val eventName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("event_category")
	val eventCategory: String? = null,

	@field:SerializedName("fk_userid")
	val fkUserid: Int? = null
)