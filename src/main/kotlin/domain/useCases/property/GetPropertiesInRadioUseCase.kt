package com.example.domain.useCases.property

import com.example.infra.interfaces.IPropertiesRepository
import domain.entities.Point
import domain.entities.House
import domain.useCases.CalculateDistanceUseCase

class GetPropertiesInRadioUseCase(
    private val calculateDistance: CalculateDistanceUseCase,
    private val propertiesRepo: IPropertiesRepository
) {

    fun execute(userLocation: Point, expectedDistance: Int): List<House> {
        return propertiesRepo.getAllProperties()
            .filter { calculateDistance.execute(userLocation, it.point) <= expectedDistance }
    }

}
