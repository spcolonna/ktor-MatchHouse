package com.example.domain.useCases.locations

import com.example.infra.interfaces.ILocationRepository

class GetNeighborhoodsUseCase(private val locationRepository: ILocationRepository) {
    fun execute(countryName: String, departmentName: String) =
        locationRepository.getNeighborhood(countryName, departmentName)

}
