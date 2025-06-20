package com.example.domain.useCases.users

import com.example.delivery.dtos.UserDto
import com.example.delivery.enums.UserRole
import domain.entities.User
import domain.useCases.user.GetUserUseCase
import doubles.UserRepositoryDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetUserUseCaseTest {

    @Test
    fun `get user by mail`(){
        val id = "anId"
        val user = User(id,"","", "", UserRole.PERSON,"")
        val expected = UserDto(id,"","", "", UserRole.PERSON,"")
        val repository = UserRepositoryDouble().withUser(user).withUserExist(true)
        val useCase = GetUserUseCase(repository)

        val result = useCase.execute(id)

        repository.lastIdCalled.shouldBe(id)
        result.shouldBe(expected)
    }

    @Test
    fun `get user by another mail`(){
        val id = "anotherId"
        val user = User(id,"","", "", UserRole.PERSON,"")
        val expected = UserDto(id,"","", "", UserRole.PERSON,"")
        val repository = UserRepositoryDouble().withUser(user).withUserExist(true)
        val useCase = GetUserUseCase(repository)

        val result = useCase.execute(id)

        repository.lastIdCalled.shouldBe(id)
        result.shouldBe(expected)
    }

    @Test
    fun `not get user if mail not exist`(){
        val id = "idNOTExist"
        val expected = UserDto(id, "", "","", UserRole.PERSON,"")
        val repository = UserRepositoryDouble().withUserExist(false)
        val useCase = GetUserUseCase(repository)

        val result = useCase.execute(id)

        result.shouldBe(expected)
    }
}