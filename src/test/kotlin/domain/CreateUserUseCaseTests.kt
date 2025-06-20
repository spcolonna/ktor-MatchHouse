package domain

import com.example.delivery.dtos.CreateUserDto
import com.example.delivery.enums.UserRole
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
    @CsvSource("id,name,mail,phoneNumber,AGENCY,AgenciaPepe","anotherId,anotherName,anotherMail,anotherPhoneNumber,PERSON, ")
    fun `return new user`(id: String, name: String, mail: String, phoneNumber: String, role: UserRole, agencyName: String){
        val expected = User(id, name, mail, phoneNumber, role, agencyName)
        val userRepository = UserRepositoryDouble()
        val dto = CreateUserDto(id, name, mail, phoneNumber, role, agencyName)
        val useCase = CreateUserUseCase(userRepository)

        useCase.execute(dto)

        userRepository.userStored.shouldBe(expected)
    }

    @Test
    fun `validate true - mail not exist`(){
        val mail = "mail"
        val userRepository = UserRepositoryDouble()
        val useCase = CreateUserUseCase(userRepository)

        val result = useCase.validate(mail)

        result.shouldBeTrue()
    }

    @Test
    fun `validate false - mail exist`(){
        val mail = "anotherMail"
        val userRepository = UserRepositoryDouble().withUser(User("","",mail,"", UserRole.PERSON,""))
        val useCase = CreateUserUseCase(userRepository)

        val result = useCase.validate(mail)

        result.shouldBeFalse()
    }
}