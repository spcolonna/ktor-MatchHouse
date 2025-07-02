package com.example.doubles

import com.example.domain.entities.UserFilter
import com.example.infra.interfaces.IFilterRepository

class FilterRepositoryDouble : IFilterRepository {

    lateinit var updatedUserFilter: UserFilter
    lateinit var storedUserFilter: UserFilter
    private var filterExist = false

    override fun store(userFilter: UserFilter) {
        storedUserFilter = userFilter
    }

    override fun update(userFilter: UserFilter) {
        updatedUserFilter = userFilter
    }

    override fun get(userId: String): UserFilter {
        TODO("Not yet implemented")
    }

    override fun exist(userId: String) = filterExist

    fun withExistFilter(): FilterRepositoryDouble {
        filterExist = true
        return this
    }
}
