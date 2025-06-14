package domain

import com.example.delivery.dtos.CreateUserDto
import domain.entities.User
import domain.useCases.user.CreateUserUseCase
import doubles.IdGeneratorDouble
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Test

class CreateUserUseCaseTests {

    @ParameterizedTest
    @CsvSource("id,name,mail,password","anotherId,anotherName,anotherMail,anotherPassword")
    fun `return new user`(id: String, name: String, mail: String, password: String){
        val expected = User(id, name, mail, password)
        val idGenerator = IdGeneratorDouble(id)
        val userRepository = UserRepositoryDouble()
        val dto = CreateUserDto(name, mail, password)
        val useCase = CreateUserUseCase(userRepository, idGenerator)

        val result = useCase.execute(dto)

        result.shouldBe(id)
        userRepository.userStored.shouldBe(expected)
    }

    @Test
    fun `validate true - mail not exist`(){
        val mail = "mail"
        val userRepository = UserRepositoryDouble()
        val useCase = CreateUserUseCase(userRepository, IdGeneratorDouble(""))

        val result = useCase.validate(mail)

        result.shouldBeTrue()
    }

    @Test
    fun `validate false - mail exist`(){
        val mail = "anotherMail"
        val userRepository = UserRepositoryDouble().withUser(User("","",mail,""))
        val useCase = CreateUserUseCase(userRepository, IdGeneratorDouble(""))

        val result = useCase.validate(mail)

        result.shouldBeFalse()
    }
}