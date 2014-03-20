package com.antwerkz.kongo


class MongoDatabase(client: MongoClient, name: String) {
    val mongoClient: MongoClient
    public val name: String

    {
        this.mongoClient = client
        this.name = name
    }

    fun getCollection(name: String) : MongoCollection {
        return MongoCollection(name, this.mongoClient, this)
    }
}