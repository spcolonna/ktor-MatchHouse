package domain.useCases

import domain.entities.Property

interface IPropertiesRepository {
    fun getAllProperties(): List<Property>

}
