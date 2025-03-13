package org.keane

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.keane.model.PersonRepository

fun Application.configureSerialization(repo: PersonRepository) {
    install(ContentNegotiation) {
        json()
    }

    routing {
/*
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
*/
        route("/person") {
            get {
                val people = repo.getAll()
                call.respond(people)
            }

            get("/byId/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val person = repo.getById(id)
                if (person == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    call.respond(person)
                }
            }

            post {
                try {
                    val person = call.receive<org.keane.model.Person>()
                    val id = repo.create(person)
                    call.respond(HttpStatusCode.Created, id)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (repo.delete(id)) {
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
