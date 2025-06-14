package com.example.infra.services

import domain.interfaces.IIdGenerator

class IdGenerator : IIdGenerator {
    override fun execute(): String {
        return "algun id que se me ocurra"
    }
}