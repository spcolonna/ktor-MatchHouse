package com.example.doubles
import com.example.infra.interfaces.IHouseRepository
import domain.entities.House

class HouseRepositoryDouble : IHouseRepository {
    private var houses = mutableListOf<House>()
    private var houseExist: Boolean = false

    fun withListOfProperties(expected: List<House>): HouseRepositoryDouble {
        houses = expected.toMutableList()
        return this
    }

    fun withHouseExist(houseExist: Boolean) : HouseRepositoryDouble{
        this.houseExist = houseExist
        return this
    }

    override fun getHouses() = houses

    override fun houseExist(houseId: String): Boolean {
        return houseExist;
    }

    override fun getHouseById(houseId: String) = houses.first { it.id == houseId }

}
