package com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses

import com.pk.zbtz.zbtzbackend.domain.HumanSummary

data class GetPeopleResponse(
    val people: List<HumanSummary>,
    val nextOffset: Int?,
    val totalPages: Int,
    val totalRecords: Int,
)
