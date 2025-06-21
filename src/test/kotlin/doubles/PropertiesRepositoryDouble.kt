package com.example.doubles

import com.example.infra.interfaces.IPropertiesRepository
import domain.entities.Property

class PropertiesRepositoryDouble(val properties: List<Property> = listOf()) : IPropertiesRepository {


    override fun getAllProperties() = properties

}
