package com.example.infra.interfaces

import domain.entities.Property

interface IPropertiesRepository {
    fun getAllProperties(): List<Property>

}
