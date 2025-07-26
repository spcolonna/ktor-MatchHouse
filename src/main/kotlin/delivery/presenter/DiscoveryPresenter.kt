package com.example.delivery.presenter

import com.example.delivery.request.DiscoveryPingRequest
import com.example.domain.useCases.AddHouseToDiscoveryQueueUseCase
import com.example.domain.useCases.discovery.GetDiscoveryQueueUseCase
import delivery.response.ResponseBuilder
import domain.entities.Point

class DiscoveryPresenter(private val addHouseToDiscoveryQueue: AddHouseToDiscoveryQueueUseCase,
    private val getDiscoveryQueue: GetDiscoveryQueueUseCase
) {
    fun handleLocationPing(request: DiscoveryPingRequest, responseBuilder: ResponseBuilder) {
        try {
            responseBuilder.onValid(addHouseToDiscoveryQueue.execute(request.userId, Point(request.lat, request.lon)))
        }catch (e: Exception){
            responseBuilder.onError(e)
        }
    }

    fun getDiscoveryHouses(userId: String, responseBuilder: ResponseBuilder) {
        responseBuilder.onValid(getDiscoveryQueue.execute(userId))
    }
}