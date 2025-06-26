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

fun Route.discoveryRouting(
    discoveryPresenter: DiscoveryPresenter,
    housePresenter: HousePresenter
) {


    route("/house") {
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
