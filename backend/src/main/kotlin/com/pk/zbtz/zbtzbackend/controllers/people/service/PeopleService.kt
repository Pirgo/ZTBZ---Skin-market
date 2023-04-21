package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.domain.Human

interface PeopleService {
    fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?,
    ): ResponseWithStatistics<GetPeopleResponse>

    fun get(humanId: Long): ResponseWithStatistics<Human>

    fun add(request: AddHumanRequest): ResponseWithStatistics<Human>

    fun delete(humanId: Long): ResponseWithStatistics<Unit>
}