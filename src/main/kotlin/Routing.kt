package com.example

import com.example.delivery.presenter.HousePresenter
import com.example.delivery.request.CreateHouseRequest
import com.example.delivery.request.LoginUserRequest
import com.example.delivery.request.UserPositionRequest
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
    val housePresenter = HousePresenter(UseCaseProvider.getCreateProperty(), UseCaseProvider.getGetHouses())


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

        route("/house/nearby") {
            get {
                val lat = call.parameters["lat"]?.toDoubleOrNull()
                val lon = call.parameters["lon"]?.toDoubleOrNull()

                housePresenter.nearbyHouses(
                    UserPositionRequest(lat, lon),
                    ResponseBuilder(call)
                )
            }
        }

        route("/house") {
            post {
                val body = call.receive<CreateHouseRequest>()
                housePresenter.createHouse(body, ResponseBuilder(call))
            }

            get {
                housePresenter.getAllHouses(ResponseBuilder(call))
            }
        }
    }
}
