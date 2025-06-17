package infra.interfaces

import domain.entities.Property

interface ICreatePropertyRepository {
    fun store(property: Property): String

}