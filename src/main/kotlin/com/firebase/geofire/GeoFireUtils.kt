package com.firebase.geofire

import kotlin.math.*

object GeoFireUtils {
    private const val EARTH_MERIDIONAL_CIRCUMFERENCE = 40007860.0
    private const val EARTH_EQ_RADIUS = 6378137.0
    private const val EPSILON = 1e-12

    fun getGeoHashForLocation(location: GeoLocation): String {
        return GeoHash.encode(location.latitude, location.longitude)
    }

    fun distance(location1: GeoLocation, location2: GeoLocation): Double {
        val lat1 = location1.latitude * PI / 180.0
        val lat2 = location2.latitude * PI / 180.0
        val dLon = (location2.longitude - location1.longitude) * PI / 180.0
        return EARTH_EQ_RADIUS * acos(
            sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(dLon)
        )
    }

    fun getGeoHashQueryBounds(center: GeoLocation, radius: Double): List<GeoHashQuery> {
        val queryBounds = mutableListOf<GeoHashQuery>()
        val bits = max(1, boundingBoxBits(center, radius))
        val precision = ceil(bits / log2(32.0)).toInt()

        val geohash = GeoHash.encode(center.latitude, center.longitude, precision)
        val neighbors = GeoHash.neighbors(geohash) + geohash

        for (neighbor in neighbors) {
            queryBounds.add(GeoHashQuery(neighbor, neighbor + "~"))
        }

        return queryBounds
    }

    private fun boundingBoxBits(location: GeoLocation, size: Double): Int {
        val latDeltaDegrees = size / 111320.0
        val latitudeNorth = min(90.0, location.latitude + latDeltaDegrees)
        val latitudeSouth = max(-90.0, location.latitude - latDeltaDegrees)

        val bitsLat = floor(log2(360.0 / latDeltaDegrees)).toInt()
        val bitsLonNorth = floor(log2(360.0 / metersToLongitudeDegrees(size, latitudeNorth))).toInt()
        val bitsLonSouth = floor(log2(360.0 / metersToLongitudeDegrees(size, latitudeSouth))).toInt()

        return min(bitsLat, bitsLonNorth.coerceAtLeast(bitsLonSouth))
    }

    private fun metersToLongitudeDegrees(distance: Double, latitude: Double): Double {
        val radians = latitude * PI / 180.0
        val num = cos(radians) * EARTH_EQ_RADIUS * 2 * PI / 360.0
        val denom = 1 / sqrt(1 - 0.00669447819799 * sin(radians).pow(2))
        val deltaDeg = num * denom
        return min(360.0, distance / deltaDeg)
    }

    fun getDistanceBetween(location1: GeoLocation, location2: GeoLocation): Double {
        val lat1 = location1.latitude * PI / 180.0
        val lat2 = location2.latitude * PI / 180.0
        val deltaLat = lat2 - lat1
        val deltaLon = (location2.longitude - location1.longitude) * PI / 180.0

        val a = sin(deltaLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(deltaLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_EQ_RADIUS * c
    }

}

