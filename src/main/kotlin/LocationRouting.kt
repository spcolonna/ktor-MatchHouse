package com.example

import com.example.delivery.presenter.LocationPresenter
import com.example.delivery.request.*
import delivery.response.ResponseBuilder
import io.ktor.server.request.*
import io.ktor.server.routing.*

fun Route.locationRouting(locationPresenter: LocationPresenter) {

    route("/locations") {
        get("/countries") {
            locationPresenter.getCountries(ResponseBuilder(call))
        }

        get("/countries/{countryName}/departments") {
            val countryName = call.parameters["countryName"]
            locationPresenter.getDepartments(countryName.toString(), ResponseBuilder(call))
        }

        get("/countries/{countryName}/departments/{departmentName}/neighborhoods") {
            val countryName = call.parameters["countryName"]
            val departmentName = call.parameters["departmentName"]
            locationPresenter.getNeighborhoods(countryName.toString(), departmentName.toString(), ResponseBuilder(call))
        }
    }
}
