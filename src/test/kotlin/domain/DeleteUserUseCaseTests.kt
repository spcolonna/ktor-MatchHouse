package domain

import com.example.delivery.enums.UserRole
import domain.entities.User
import domain.useCases.user.DeleteUserUseCase
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test

class DeleteUserUseCaseTests() {

    @ParameterizedTest
    @CsvSource("id", "anotherId")
    fun `delete existing user`(id: String) {
        val user = givenAUser(userId = id)
        val userRepository = UserRepositoryDouble().withUser(user)
        val useCase = DeleteUserUseCase(userRepository)

        useCase.executeDelete(id)

        userRepository.lastUserIdDeleted.shouldBe(id)
    }

    @Test
    fun `validate false - user not exists`(){
        val userRepository = UserRepositoryDouble().withUserExist(false)
        val useCase = DeleteUserUseCase(userRepository)

        val result = useCase.validateUser("ids")
        result.shouldBeFalse()
    }


    @Test
    fun `validate true - user exists`(){
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val useCase = DeleteUserUseCase(userRepository)

        val result = useCase.validateUser("id")
        result.shouldBeTrue()
    }



    private fun givenAUser(userId: String = "id",
                           userName: String = "name",
                           mail: String = "mail",
                           phoneNumber: String = "phoneNumber",
                           role: UserRole = UserRole.PERSON,
                           agencyName: String = "agencyName",
    ) = User(userId, userName, mail, phoneNumber, role, agencyName)
}