package com.example.doubles

import com.example.infra.interfaces.IPropertiesRepository
import domain.entities.House

class PropertiesRepositoryDouble(val properties: List<House> = listOf()) : IPropertiesRepository {


    override fun getAllProperties() = properties

}
