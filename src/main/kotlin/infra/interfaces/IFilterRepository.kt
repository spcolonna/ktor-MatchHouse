package com.example.infra.interfaces

import com.example.domain.entities.UserFilter

interface IFilterRepository {
    fun store(userFilter: UserFilter)
    fun update(userFilter: UserFilter)
}
