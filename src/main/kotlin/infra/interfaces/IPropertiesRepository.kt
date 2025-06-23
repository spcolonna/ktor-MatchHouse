package com.example.infra.interfaces

import domain.entities.House

interface IPropertiesRepository {
    fun getAllProperties(): List<House>

}
