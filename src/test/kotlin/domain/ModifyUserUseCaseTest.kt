package domain

import domain.entities.User
import domain.useCases.user.ModifyUserUseCase
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test

class ModifyUserUseCaseTest {

   // @ParameterizedTest
    @CsvSource("id,name,mail,password","anotherId,anotherName,anotherMail,anotherPassword")

    @Test
    fun `modify existing user password`(){
        val userId = "id"
        val user = User(userId,"name","mail","password")
        val newPassword = "newPassword"
        val userRepository = UserRepositoryDouble().withUser(user)
        val useCase = ModifyUserUseCase(userRepository)
        useCase.execute(userId, newPassword)

        userRepository.getStoredUser().password.shouldBe(newPassword)
    }

    @Test
    fun `modify another existing user password`(){
        val userId = "id"
        val user = User(userId,"name","mail","anotherPassword")
        val newPassword = "anotherNewPassword"
        val userRepository = UserRepositoryDouble().withUser(user)
        val useCase = ModifyUserUseCase(userRepository)
        useCase.execute(userId, newPassword)

        userRepository.getStoredUser().password.shouldBe(newPassword)
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