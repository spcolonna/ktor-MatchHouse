package com.example.infra.interfaces

interface IDiscoveryListRepository {
    fun addHouses(userId: String, houseIds: List<String>)

}
