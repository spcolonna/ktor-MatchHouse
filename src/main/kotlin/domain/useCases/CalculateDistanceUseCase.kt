package domain.useCases

import domain.entities.Point
import kotlin.math.*

class CalculateDistanceUseCase {
    fun execute(point1: Point, point2: Point): Int {
            val R = 6371.0 // Radio de la Tierra en kilómetros

            val lat1Rad = Math.toRadians(point1.lat)
            val lon1Rad = Math.toRadians(point1.lon)
            val lat2Rad = Math.toRadians(point2.lat)
            val lon2Rad = Math.toRadians(point2.lon)

            val dLat = lat2Rad - lat1Rad
            val dLon = lon2Rad - lon1Rad

            val a = sin(dLat / 2).pow(2) + cos(lat1Rad) * cos(lat2Rad) * sin(dLon / 2).pow(2)
            val c = 2 * atan2(sqrt(a), sqrt(1 - a))

            return (R * c).toInt() // Distancia en kilómetros
        }

    }


