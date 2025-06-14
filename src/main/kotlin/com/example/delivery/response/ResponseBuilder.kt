package com.example.com.example.delivery.response

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking

class ResponseBuilder(private val call: ApplicationCall) {

    fun onValid(element: Any) = runBlocking {
        call.respond(HttpStatusCode.OK, element)
    }

    fun onError()  = runBlocking {
        call.respond(HttpStatusCode.Conflict)
    }
}