package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Point(val lat: Double, val lon: Double) {

}
