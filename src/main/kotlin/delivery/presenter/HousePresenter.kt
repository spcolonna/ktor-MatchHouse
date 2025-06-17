package com.example.delivery.presenter

import com.example.delivery.dtos.House
import com.example.delivery.request.CreateHouseRequest
import com.example.delivery.request.UserPositionRequest
import delivery.dtos.CreatePropertyDto
import delivery.response.ResponseBuilder
import domain.useCases.property.CreatePropertyUseCase
import java.util.*


class HousePresenter(val createPropertyUseCase: CreatePropertyUseCase
) {
    fun nearbyHouses(body: UserPositionRequest, responseBuilder: ResponseBuilder) {
        println("Buscando casas cerca de lat: $body.lat, lon: $body.lon")

        val nearbyHouses = listOf(
            House(UUID.randomUUID().toString(), "Chalet Moderno", "Av. Principal 123", 250000.0, "url_a_imagen_1", 1.2),
            House(UUID.randomUUID().toString(), "Apartamento Céntrico", "Calle Secundaria 45", 180000.0, "url_a_imagen_2", 2.5),
            House(UUID.randomUUID().toString(), "Casa con Jardín", "Ruta 8 km 22", 320000.0, "url_a_imagen_3", 3.1),
            House(UUID.randomUUID().toString(), "Loft Industrial", "Distrito de Diseño", 210000.0, "url_a_imagen_4", 4.8),
            House(UUID.randomUUID().toString(), "Vivienda Familiar", "Residencial Los Pinos", 285000.0, "url_a_imagen_5", 5.2)
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
}
