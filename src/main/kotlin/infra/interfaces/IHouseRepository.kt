package com.example.infra.interfaces

import domain.entities.House

interface IHouseRepository {
    fun getHouses(): List<House>
    fun houseExist(houseId: String): Boolean
    fun getHouseById(houseId: String): House

}
