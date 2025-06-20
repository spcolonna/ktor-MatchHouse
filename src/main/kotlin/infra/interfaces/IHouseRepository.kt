package com.example.infra.interfaces

import domain.entities.Property

interface IHouseRepository {
    fun getHouses(): List<Property>
    fun houseExist(houseId: String): Boolean

}
