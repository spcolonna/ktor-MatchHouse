package com.example.delivery.presenter

import com.example.domain.useCases.locations.GetCountriesUseCase
import com.example.domain.useCases.locations.GetDepartmentsUseCase
import com.example.domain.useCases.locations.GetNeighborhoodsUseCase
import delivery.response.ResponseBuilder

class LocationPresenter(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getNeighborhoodsUseCase: GetNeighborhoodsUseCase
)
{
    fun getCountries(responseBuilder: ResponseBuilder) {
        try {
            responseBuilder.onValid(getCountriesUseCase.execute())
        }catch (e: Exception){
            responseBuilder.onError("Error al buscar paises")
        }
    }

    fun getDepartments(countryName: String, responseBuilder: ResponseBuilder) {
        try {
            responseBuilder.onValid(getDepartmentsUseCase.execute(countryName))
        }catch (e: Exception){
            responseBuilder.onError("Error al buscar departamentos")
        }
    }

    fun getNeighborhoods(countryName: String, departmentName: String, responseBuilder: ResponseBuilder) {
        try {
            responseBuilder.onValid(getNeighborhoodsUseCase.execute(countryName, departmentName))
        }catch (e: Exception){
            responseBuilder.onError("Error al buscar barrios")
        }
    }

}
