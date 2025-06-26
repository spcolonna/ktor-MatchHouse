package com.example.doubles

import com.example.infra.interfaces.IDiscoveryListRepository

class DiscoveryListRepositoryDouble : IDiscoveryListRepository {

    var houseList: MutableList<String> = mutableListOf()
    var userIdStored: String = ""

    override fun addHouses(userId: String, houseIds: List<String>) {
        houseList.addAll(houseIds)
        userIdStored = userId
    }

}
