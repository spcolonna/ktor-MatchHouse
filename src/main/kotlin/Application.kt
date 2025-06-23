package com.example

import com.example.infra.services.FirebaseAdmin
import io.ktor.server.application.*

fun main(args: Array<String>) {
    val firebaseAdmin = FirebaseAdmin()
    firebaseAdmin.initializeFirebase()

    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
}
