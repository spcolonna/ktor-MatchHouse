package com.example

import com.example.com.example.delivery.UserPresenter
import com.example.com.example.delivery.request.CreateUserRequest
import com.example.com.example.delivery.response.ResponseBuilder
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val userPresenter = UserPresenter()


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
