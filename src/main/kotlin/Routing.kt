package com.example

import com.example.delivery.presenter.DiscoveryPresenter
import com.example.delivery.presenter.HousePresenter
import com.example.delivery.request.*
import delivery.presenter.UserPresenter
import delivery.providers.UseCaseProvider
import delivery.request.CreateUserRequest
import delivery.response.ResponseBuilder
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val housePresenter = HousePresenter(
        UseCaseProvider.getCreateProperty(),
        UseCaseProvider.getGetHouses(),
        UseCaseProvider.getGetHousesById(),
        UseCaseProvider.getUserHouses()
    )
    val userPresenter = UserPresenter(
        housePresenter,
        UseCaseProvider.getCreateUser(),
        UseCaseProvider.getUser(),
        UseCaseProvider.getModifyUser(),
        UseCaseProvider.getDeleteUser(),
        UseCaseProvider.getAddFavourite(),
        UseCaseProvider.getFavourite()
    )
    val discoveryPresenter = DiscoveryPresenter(
        UseCaseProvider.getAddHouseToDiscoveryQueue()
    )



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
                println("llego el create con body: " + body)
                userPresenter.createUser(
                    body,
                    ResponseBuilder(call)
                )
            }

            get("/{id}") {
                val id = call.parameters["id"]

                userPresenter.getUser(
                    id.toString(),
                    ResponseBuilder(call)
                )
            }

            post("/{userId}/favorites/{houseId}") {
                val userId = call.parameters["userId"]
                val houseId = call.parameters["houseId"]

                userPresenter.addFavourite(
                    AddFavoriteRequest(userId.toString(), houseId.toString()),
                    ResponseBuilder(call)
                )
            }

            get("/{userId}/favorites") {
                val userId = call.parameters["userId"]

                userPresenter.getFavourites(
                    userId.toString(),
                    ResponseBuilder(call)
                )
            }

            delete("/{userId}/favorites/{houseId}") {
                val userId = call.parameters["userId"]
                val houseId = call.parameters["houseId"]

                userPresenter.deleteFavourite(
                    AddFavoriteRequest(userId.toString(), houseId.toString()),
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

            get("/{userId}") {
                val userId = call.parameters["userId"]
                housePresenter.getUserHouses(userId.toString(), ResponseBuilder(call))
            }

            get("/nearby") {
                val lat = call.parameters["lat"]?.toDoubleOrNull()
                val lon = call.parameters["lon"]?.toDoubleOrNull()

                housePresenter.nearbyHouses(
                    UserPositionRequest(lat, lon),
                    ResponseBuilder(call)
                )
            }
        }

        route("/discovery") {
            post("/ping") {
                val userId = call.parameters["userId"]
                val lat = call.parameters["lat"]?.toDoubleOrNull() ?: 0.0
                val lon = call.parameters["lon"]?.toDoubleOrNull() ?: 0.0

                if (userId.isNullOrBlank()) {
                    call.respond(HttpStatusCode.BadRequest, "El parámetro 'userId' es requerido.")
                    return@post
                }

                discoveryPresenter.handleLocationPing(
                    DiscoveryPingRequest(userId.toString(), lat, lon),
                    ResponseBuilder(call)
                )
            }
        }
    }
}
