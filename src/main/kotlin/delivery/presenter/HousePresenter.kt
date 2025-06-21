package com.example.delivery.presenter

import com.example.delivery.dtos.HouseDto
import com.example.delivery.request.CreateHouseRequest
import com.example.delivery.request.UserPositionRequest
import com.example.domain.useCases.property.GetHouseByIdUseCase
import delivery.dtos.CreatePropertyDto
import delivery.response.ResponseBuilder
import domain.entities.Point
import domain.useCases.property.CreatePropertyUseCase
import domain.useCases.property.GetHousesUseCase
import java.util.*


class HousePresenter(
    private val createPropertyUseCase: CreatePropertyUseCase,
    private val getHousesUseCase: GetHousesUseCase,
    private val getHouseByIdUseCase: GetHouseByIdUseCase
) {
    fun nearbyHouses(body: UserPositionRequest, responseBuilder: ResponseBuilder) {
        println("Buscando casas cerca de lat: $body.lat, lon: $body.lon")

        val nearbyHouses = listOf(
            HouseDto(
                id = UUID.randomUUID().toString(),
                title = "Chalet Moderno en Carrasco",
                point = Point(lat = -34.88, lon = -56.05),
                price = 350000,
                bedrooms = 4,
                bathrooms = 3,
                area = 220.0
            ),
            HouseDto(
                id = UUID.randomUUID().toString(),
                title = "Apartamento Céntrico",
                point = Point(lat = -34.90, lon = -56.18),
                price = 180000,
                bedrooms = 2,
                bathrooms = 1,
                area = 75.5
            ),
            HouseDto(
                id = UUID.randomUUID().toString(),
                title = "Casa con Jardín en el Prado",
                point = Point(lat = -34.85, lon = -56.20),
                price = 320000,
                bedrooms = 5,
                bathrooms = 4,
                area = 300.0
            )
        )

        responseBuilder.onValid(nearbyHouses)
    }

    fun createHouse(request: CreateHouseRequest, responseBuilder: ResponseBuilder) {
        try {
            val dto = CreatePropertyDto.fromRequest(request)
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

    fun getHouseById(houseId: String): HouseDto? {
        if (getHouseByIdUseCase.validate(houseId))
            return HouseDto.from(getHouseByIdUseCase.execute(houseId))
        else
            return null
    }
}
