package com.antwerkz.oswin


class MongoDatabase(val client: MongoClient, val name: String) {
    fun getCollection(name: String) : MongoCollection {
        return MongoCollection(name, client, this)
    }
}