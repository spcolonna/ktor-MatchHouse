package com.example.delivery.request

@kotlinx.serialization.Serializable
class DiscoveryPingRequest(
    val userId: String,
    val lat: Double,
    val lon: Double
)