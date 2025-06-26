package com.example

import com.example.delivery.presenter.HousePresenter
import com.example.delivery.request.*
import delivery.response.ResponseBuilder
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.houseRouting(housePresenter: HousePresenter) {

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
    }
}
