package org.keane.model

class FakePersonRepository : PersonRepository {
    private val people = mutableListOf<Person>(
        Person(1, "John", 25, "john@email.com", "123-456-7890", "1234 Elm St."),
        Person(2, "Jane", 30, "jane@email.com", "234-231-7479", "2298 Oak St."),
        Person(3, "Bob", 15, "bob@email.com", "345-123-4567", "3456 Pine St."),
        Person(4, "Alice", 40, "alice@email.com", "456-789-1234", "4567 Maple St.")
    )

    override fun getAll(): List<Person> = people

    /**
     * Get a person by id
     * @param id the id of the person to get
     * @return the person with the given id, or null if no such person exists
     */
    override fun getById(id: Int): Person? = people.firstOrNull { it.id == id }

    /**
     * Create a new person
     * @param person the person to create
     * @return the id of the created person
     */
    override fun create(person: Person): Int {
        val id = people.maxOfOrNull { it.id }!! + 1
        people.add(person.copy(id = id))
        return id
    }

    /**
     * Read a person by id
     * @param id the id of the person to read
     * @return the person with the given id, or null if no such person exists
     */
    override fun read(id: Int): Person? = people.firstOrNull { it.id == id }

    /**
     * Update a person by id
     * @param id the id of the person to update
     * @param person the new person data
     */
    override fun update(id: Int, person: Person) {
        val index = people.indexOfFirst { it.id == id }
        if (index != -1) {
            people[index] = person.copy(id = id)
        }
    }

    /**
     * Delete a person by id
     * @param id the id of the person to delete
     */
    override fun delete(id: Int): Boolean {
        return people.removeIf { it.id == id }
    }
}