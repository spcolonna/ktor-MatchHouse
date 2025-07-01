package com.example.domain.useCases.locations

import com.example.infra.interfaces.ILocationRepository


class GetCountriesUseCase(private val locationRepository: ILocationRepository) {
    fun execute() = locationRepository.getCountries()

}
