package com.example.domain.useCases

import com.example.infra.interfaces.IDiscoveryListRepository
import com.example.infra.interfaces.IHouseRepository
import domain.entities.Point
import domain.useCases.CalculateDistanceUseCase

class AddHouseToDiscoveryQueueUseCase(
    private val discoveryListRepository: IDiscoveryListRepository,
    private val houseRepository: IHouseRepository,
    private val calculateDistanceUseCase: CalculateDistanceUseCase
) {
    private val maxDistance = 100

    fun execute(userId: String, userPosition: Point) {
        val houses = houseRepository.getHouses()
        val houseIds = houses.filter { calculateDistanceUseCase.execute(userPosition, it.point) <= maxDistance }.map { it.id }
        discoveryListRepository.addHouses(userId, houseIds)
    }

}
