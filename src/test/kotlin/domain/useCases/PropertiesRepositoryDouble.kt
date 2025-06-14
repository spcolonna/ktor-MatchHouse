package domain.useCases

import domain.entities.Property

class PropertiesRepositoryDouble(val properties: List<Property> = listOf()) : IPropertiesRepository{


    override fun getAllProperties() = properties

}
