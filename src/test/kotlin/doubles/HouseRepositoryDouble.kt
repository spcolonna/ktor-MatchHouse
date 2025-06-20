package com.example.doubles
import com.example.infra.interfaces.IHouseRepository
import domain.entities.Property

class HouseRepositoryDouble : IHouseRepository {
    private var houses = mutableListOf<Property>()
    private var houseExist: Boolean = false

    fun withListOfProperties(expected: List<Property>): IHouseRepository {
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

}
