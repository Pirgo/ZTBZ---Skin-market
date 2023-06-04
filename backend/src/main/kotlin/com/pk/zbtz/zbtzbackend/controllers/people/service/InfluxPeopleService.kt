package com.pk.zbtz.zbtzbackend.controllers.people.service

import com.pk.zbtz.zbtzbackend.controllers.ResponseWithStatistics
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.AddHumanRequest
import com.pk.zbtz.zbtzbackend.controllers.people.requests_and_responses.GetPeopleResponse
import com.pk.zbtz.zbtzbackend.domain.Human
import org.springframework.stereotype.Service

@Service
class InfluxPeopleService() : PeopleService {
    override fun getAll(
        nameToSearch: String?,
        pageSize: Int?,
        offset: Int?
    ): ResponseWithStatistics<GetPeopleResponse> {
        TODO("Not yet implemented")
    }

    override fun get(humanId: String): ResponseWithStatistics<Human> {
        TODO("Not yet implemented")
    }

    override fun add(request: AddHumanRequest): ResponseWithStatistics<Human> {
        TODO("Not yet implemented")
    }

    override fun delete(humanId: String): ResponseWithStatistics<Unit> {
        TODO("Not yet implemented")
    }
}
