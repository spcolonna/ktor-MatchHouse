package com.example.com.example.delivery

import com.example.com.example.delivery.response.ResponseBuilder
import com.example.com.example.delivery.request.CreateUserRequest

class UserPresenter {

    fun createUser(request: CreateUserRequest, responseBuilder: ResponseBuilder) {
        responseBuilder.onValid("LuccaThiago")
//        if(createUserUserCase.validate(request.mail))
//            responseBuilder.onValid(createUserUserCase.execute(request.toDto()))
//        else
//            responseBuilder.onError()
    }

    fun deleteUser(userId: String, responseBuilder: ResponseBuilder) {
//        if(deleteUserUseCase.validateUser(userId)){
//            responseBuilder.onValid(deleteUserUseCase.executeDelete(userId))
//        }else{
//            responseBuilder.onError()
//        }
    }
}
