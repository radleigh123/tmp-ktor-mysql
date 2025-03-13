package org.keane.model

interface PersonRepository {
    fun getAll(): List<Person>

    fun getById(id: Int): Person?

    fun create(person: Person): Int

    fun read(id: Int): Person?

    fun update(id: Int, person: Person)

    fun delete(id: Int): Boolean
}