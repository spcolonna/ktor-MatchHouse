package com.example.domain.useCases.users

import com.example.delivery.dtos.CreateUserDto
import com.example.delivery.enums.UserRole
import domain.entities.User
import domain.useCases.user.ModifyUserUseCase
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test

class ModifyUserUseCaseTest {

    @ParameterizedTest
    @CsvSource("id,name,mail,phoneNumber,AGENCY,AgenciaPepe","anotherId,anotherName,anotherMail,anotherPhoneNumber,PERSON,nada")
    fun `modify existing user`(id: String, name: String, mail: String, phoneNumber: String, role: UserRole, agencyName: String){
        val expected = User(id, name, mail, phoneNumber, role, agencyName)
        val dto = CreateUserDto(id, name, mail, phoneNumber, role, agencyName)
        val userRepository = UserRepositoryDouble().withUser(User("id2", "name2", "mail2", "0009090", UserRole.PERSON, ""))
        val useCase = ModifyUserUseCase(userRepository)

        useCase.execute(dto)

        userRepository.userStored.shouldBe(expected)
    }

    @Test
    fun `validate user not exist`(){
        val userRepository = UserRepositoryDouble()
        val useCase = ModifyUserUseCase(userRepository)
        val result = useCase.validate("id")

        result.shouldBeFalse()
    }

    @Test
    fun `validate user exist`(){
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val useCase = ModifyUserUseCase(userRepository)
        val result = useCase.validate("id")

        result.shouldBeTrue()
    }
}