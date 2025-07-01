package com.example

import com.example.delivery.presenter.DiscoveryPresenter
import com.example.delivery.presenter.HousePresenter
import com.example.delivery.presenter.LocationPresenter
import delivery.presenter.UserPresenter
import delivery.providers.UseCaseProvider
import io.ktor.server.application.*
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
        UseCaseProvider.getFavourite(),
        UseCaseProvider.getDeleteFavourite()
    )
    val discoveryPresenter = DiscoveryPresenter(
        UseCaseProvider.getAddHouseToDiscoveryQueue()
    )
    val locationPresenter = LocationPresenter(
        UseCaseProvider.getCountries(),
        UseCaseProvider.getDepartments(),
        UseCaseProvider.getNeighborhoods(),
        )



    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        userRouting(userPresenter)
        houseRouting(housePresenter)
        discoveryRouting(discoveryPresenter, housePresenter)
        locationRouting(locationPresenter)
    }
}
