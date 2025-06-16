package com.example

import com.example.delivery.request.LoginUserRequest
import delivery.presenter.UserPresenter
import delivery.providers.UseCaseProvider
import delivery.request.CreateUserRequest
import delivery.response.ResponseBuilder
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val userPresenter = UserPresenter(UseCaseProvider.getCreateUser(), UseCaseProvider.getDeleteUser())


    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        route("/login") {
            post {
                val body = call.receive<LoginUserRequest>()

                userPresenter.loginUser(
                    body,
                    ResponseBuilder(call)
                )
            }
        }

        route("/user") {
            post {
                val body = call.receive<CreateUserRequest>()

                userPresenter.createUser(
                    body,
                    ResponseBuilder(call)
                )
            }
        }
    }
}
