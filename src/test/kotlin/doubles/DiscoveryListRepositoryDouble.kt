package com.example.doubles

import com.example.infra.interfaces.IDiscoveryListRepository

class DiscoveryListRepositoryDouble : IDiscoveryListRepository {

    var houseList: MutableList<String> = mutableListOf()
    override fun addHouses(houseIds: List<String>) {
        houseList.addAll(houseIds)
    }

}
