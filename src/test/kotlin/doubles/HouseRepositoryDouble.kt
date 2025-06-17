package com.example.doubles
import com.example.infra.interfaces.IHouseRepository
import domain.entities.Property

class HouseRepositoryDouble : IHouseRepository {
    private var houses = mutableListOf<Property>()

    fun with(expected: List<Property>): IHouseRepository {
        houses = expected.toMutableList()
        return this
    }

    override fun getHouses() = houses

}
