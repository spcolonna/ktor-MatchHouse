package com.example.domain.useCases.houses

import com.example.infra.interfaces.IHouseRepository

class GetNearbyHousesUseCase(private val repository: IHouseRepository) {
    fun execute(lat: Double, lon: Double, distance: Double) =
        repository.findNearbyHouses(lat,lon,distance)


}
