package delivery.presenter

import com.example.delivery.request.LoginUserRequest
import com.example.delivery.response.LoginUserResponse
import delivery.response.ResponseBuilder
import delivery.request.CreateUserRequest
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase
import java.util.*

class UserPresenter(
    private val createUserUserCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
) {

    fun createUser(request: CreateUserRequest, responseBuilder: ResponseBuilder) {
        if(createUserUserCase.validate(request.mail))
            responseBuilder.onValid(createUserUserCase.execute(request.toDto()))
        else
            responseBuilder.onError()
    }

    fun deleteUser(userId: String, responseBuilder: ResponseBuilder) {
        if(deleteUserUseCase.validateUser(userId)){
            responseBuilder.onValid(deleteUserUseCase.executeDelete(userId))
        }else{
            responseBuilder.onError()
        }
    }

    fun loginUser(body: LoginUserRequest, responseBuilder: ResponseBuilder) {
        val response = LoginUserResponse(
            id = UUID.randomUUID().toString(),
            mail = body.mail,
            token = "un-token-jwt-secreto-aqui" // En un futuro, aquí generarías un token real
        )
        responseBuilder.onValid(response)
    }


}
