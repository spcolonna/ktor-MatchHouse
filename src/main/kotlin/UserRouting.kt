package com.example

import com.example.delivery.request.*
import delivery.presenter.UserPresenter
import delivery.request.CreateUserRequest
import delivery.response.ResponseBuilder
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.userRouting(userPresenter: UserPresenter) {

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

        put("/{userId}/filter") {
            val userId = call.parameters["userId"]
            val body = call.receive<UserFilterRequest>()

            userPresenter.storeFilter(
                body.toDto(userId.toString()),
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
}
