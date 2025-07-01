package com.example.domain.useCases.users

import com.example.domain.entities.UserFilter
import com.example.domain.useCases.user.StoreFilterUseCase
import com.example.doubles.FilterRepositoryDouble
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class StoreFilterUseCaseTest {

    @Test
    fun `store user filter`(){
        val userFilter = givenAUserFilter()
        val repository = FilterRepositoryDouble()
        val useCase = StoreFilterUseCase(repository, UserRepositoryDouble())

        useCase.execute(userFilter)

        repository.storedUserFilter.shouldBe(userFilter)
    }

    @Test
    fun `validate user`(){
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val useCase = StoreFilterUseCase(FilterRepositoryDouble(), userRepository)

        val result = useCase.validate("userId")

        result.shouldBeTrue()
    }

    @Test
    fun `NOT validate user`(){
        val userRepository = UserRepositoryDouble().withUserExist(false)
        val useCase = StoreFilterUseCase(FilterRepositoryDouble(), userRepository)

        val result = useCase.validate("userId")

        result.shouldBeFalse()
    }

    private fun givenAUserFilter(userId: String = "userId") = UserFilter(userId, "country", "department", "neighbord", 0.0, 0.0, 1, 1, 1)


}