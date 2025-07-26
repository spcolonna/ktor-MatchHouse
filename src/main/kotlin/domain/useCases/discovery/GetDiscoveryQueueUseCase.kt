package com.example.domain.useCases.discovery

import com.example.domain.useCases.property.GetHouseByIdUseCase
import com.example.infra.interfaces.IDiscoveryListRepository
import domain.entities.House
import domain.useCases.property.GetHousesUseCase

class GetDiscoveryQueueUseCase(private val discoveryListRepository: IDiscoveryListRepository, private val getHouseUseCase : GetHouseByIdUseCase) {
    fun execute(userId : String ) : List<House>{
    val housesId = discoveryListRepository.getDiscoveryQueue(userId)
    val houses = housesId.map{getHouseUseCase.execute(it)}
        return houses
    }
}
