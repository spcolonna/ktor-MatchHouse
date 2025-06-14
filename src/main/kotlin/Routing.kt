package com.example

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
