package com.example.infra.interfaces

interface ILocationRepository {
    fun getCountries(): List<String>
    fun getDepartment(countryName: String): List<String>
    fun getNeighborhood(countryName: String, departmentName: String): List<String>
}