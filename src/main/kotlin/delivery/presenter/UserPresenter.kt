package delivery.presenter

import delivery.response.ResponseBuilder
import delivery.request.CreateUserRequest
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase

class UserPresenter(
    private val createUserUserCase: CreateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
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


}
