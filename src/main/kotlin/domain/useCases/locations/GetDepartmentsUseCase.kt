package com.example.domain.useCases.locations

import com.example.infra.interfaces.ILocationRepository

class GetDepartmentsUseCase(private val locationRepository: ILocationRepository) {
    fun execute(countryName: String) = locationRepository.getDepartment(countryName)

}
