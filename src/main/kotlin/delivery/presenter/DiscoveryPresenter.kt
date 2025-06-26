package com.example.delivery.presenter

import com.example.delivery.request.DiscoveryPingRequest
import com.example.domain.useCases.AddHouseToDiscoveryQueueUseCase
import delivery.response.ResponseBuilder
import domain.entities.Point

class DiscoveryPresenter(private val addHouseToDiscoveryQueue: AddHouseToDiscoveryQueueUseCase) {
    fun handleLocationPing(request: DiscoveryPingRequest, responseBuilder: ResponseBuilder) {
        try {
            responseBuilder.onValid(addHouseToDiscoveryQueue.execute(request.userId, Point(request.lat, request.lon)))
        }catch (e: Exception){
            responseBuilder.onError(e)
        }
    }
}