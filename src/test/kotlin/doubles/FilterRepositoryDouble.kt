package com.example.doubles

import com.example.domain.entities.UserFilter
import com.example.infra.interfaces.IFilterRepository

class FilterRepositoryDouble : IFilterRepository {

    lateinit var storedUserFilter: UserFilter

    override fun store(userFilter: UserFilter) {
        storedUserFilter = userFilter
    }

    override fun update(userFilter: UserFilter) {
        TODO("Not yet implemented")
    }
}
