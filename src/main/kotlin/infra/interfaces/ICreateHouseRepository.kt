package infra.interfaces

import domain.entities.House

interface ICreateHouseRepository {
    fun store(property: House): String

}