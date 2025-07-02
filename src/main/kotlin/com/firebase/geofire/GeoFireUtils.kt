package com.firebase.geofire

import kotlin.math.*

object GeoFireUtils {
    private const val EARTH_EQ_RADIUS = 6378137.0

    fun getDistanceBetween(location1: GeoLocation, location2: GeoLocation): Double {
        val lat1 = Math.toRadians(location1.latitude)
        val lon1 = Math.toRadians(location1.longitude)
        val lat2 = Math.toRadians(location2.latitude)
        val lon2 = Math.toRadians(location2.longitude)
        val dLat = lat2 - lat1
        val dLon = lon2 - lon1
        val a = sin(dLat / 2).pow(2) + cos(lat1) * cos(lat2) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_EQ_RADIUS * c
    }

    fun getGeoHashForLocation(location: GeoLocation): String {
        return GeoHash.encode(location.latitude, location.longitude)
    }

    fun getGeoHashQueryBounds(center: GeoLocation, radiusInM: Double): List<GeoHashQuery> {
        val bits = max(1, boundingBoxBits(center, radiusInM))
        val precision = ceil(bits / 5.0).toInt()
        val geohash = GeoHash.encode(center.latitude, center.longitude, precision)
        val query = GeoHashQuery(geohash, geohash + "~")
        val neighbors = GeoHash.neighbors(geohash).map { GeoHashQuery(it, it + "~") }
        return neighbors + query
    }

    private fun metersToLongitudeDegrees(distance: Double, latitude: Double): Double {
        val radians = Math.toRadians(latitude)
        val num = cos(radians) * EARTH_EQ_RADIUS * 2.0 * PI / 360.0
        val den = 1.0 / sqrt(1.0 - 0.00669447819799 * sin(radians).pow(2))
        val deltaDeg = num * den
        return if (deltaDeg < 1e-12) distance else distance / deltaDeg
    }

    private fun boundingBoxBits(center: GeoLocation, radiusInM: Double): Int {
        val latDegrees = radiusInM / 111320.0
        val latitudeNorth = min(90.0, center.latitude + latDegrees)
        val latitudeSouth = max(-90.0, center.latitude - latDegrees)
        val bitsLat = floor(log2(360.0 / (latDegrees * 2.0))).toInt()
        val bitsLonNorth = floor(log2(360.0 / metersToLongitudeDegrees(radiusInM * 2.0, latitudeNorth))).toInt()
        val bitsLonSouth = floor(log2(360.0 / metersToLongitudeDegrees(radiusInM * 2.0, latitudeSouth))).toInt()
        return min(bitsLat, min(bitsLonNorth, bitsLonSouth))
    }
}