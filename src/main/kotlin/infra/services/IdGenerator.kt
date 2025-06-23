package com.example.infra.services

import domain.interfaces.IIdGenerator
import java.util.*

class IdGenerator : IIdGenerator {
    override fun execute() = UUID.randomUUID().toString()
}