package org.keane

import io.ktor.server.application.*
import org.keane.model.FakePersonRepository

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = FakePersonRepository()

    configureSerialization(repository)
    configureDatabases()
    configureRouting()
}
