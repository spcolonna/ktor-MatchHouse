package com.example.delivery.presenter

import com.example.delivery.dtos.HouseDto
import com.example.delivery.request.CreateHouseRequest
import com.example.delivery.request.UserPositionRequest
import com.example.domain.useCases.houses.GetNearbyHousesUseCase
import com.example.domain.useCases.property.GetHouseByIdUseCase
import com.example.domain.useCases.property.GetUserHousesUseCase
import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation
import delivery.dtos.CreateHouseDto
import delivery.response.ResponseBuilder
import domain.entities.Point
import domain.useCases.property.CreateHouseUseCase
import domain.useCases.property.GetHousesUseCase
import java.util.*


class HousePresenter(
    private val createPropertyUseCase: CreateHouseUseCase,
    private val getHousesUseCase: GetHousesUseCase,
    private val getHouseByIdUseCase: GetHouseByIdUseCase,
    private val getUserHousesUseCase: GetUserHousesUseCase,
    private val getNearbyHousesUseCase: GetNearbyHousesUseCase
) {
    fun nearbyHouses(body: UserPositionRequest, responseBuilder: ResponseBuilder) {
        val lat = body.lat ?: return responseBuilder.onError("Latitud requerida.")
        val lon = body.lon ?: return responseBuilder.onError("Longitud requerida.")

        try {
            val nearbyHouses = getNearbyHousesUseCase.execute(lat, lon, 10000.0)

            val sortedAndLimited = nearbyHouses.sortedBy { house ->
                GeoFireUtils.getDistanceBetween(
                    GeoLocation(house.point.lat, house.point.lon),
                    GeoLocation(lat, lon)
                )
            }.take(10)

            responseBuilder.onValid(sortedAndLimited.map { HouseDto.from(it) })
        } catch (e: Exception) {
            println("Error al buscar casas cercanas: ${e.message}")
            responseBuilder.onError("No se pudieron encontrar casas cercanas.")
        }
    }

    fun createHouse(request: CreateHouseRequest, responseBuilder: ResponseBuilder) {
        try {
            val dto = CreateHouseDto.fromRequest(request)
            val newHouseId = createPropertyUseCase.execute(dto)
            responseBuilder.onValid(mapOf("status" to "ok", "houseId" to newHouseId))
        } catch (e: Exception) {
            println("Error al crear la casa: ${e.message}")
            responseBuilder.onError("No se pudo crear la propiedad.")
        }
    }

    fun getAllHouses(responseBuilder: ResponseBuilder) {
        try {
            val houses = getHousesUseCase.execute()
            responseBuilder.onValid(houses)
        } catch (e: Exception) {
            println("Error al crear la casa: ${e.message}")
            responseBuilder.onError("No se pudo crear la propiedad.")
        }
    }

    fun getHouseById(houseId: String) =
        if (getHouseByIdUseCase.validate(houseId))
            HouseDto.from(getHouseByIdUseCase.execute(houseId))
        else
            null

    fun getUserHouses(userId: String, responseBuilder: ResponseBuilder) =
        if(getUserHousesUseCase.validate(userId)){
            val houses = getUserHousesUseCase.execute(userId)
            responseBuilder.onValid(houses.map { HouseDto.from(it) })
        }else{
            responseBuilder.onError("Usuario no encontrado")
        }
}
