package com.antwerkz.kongo

import org.testng.annotations.Test as test

public class MongoClientTest {
  test fun insert() {
    val mongoClient = MongoClient()

    val collection = mongoClient.getDatabase("kongo").getCollection("test")
    collection.insert(document("a" to "b", "number" to 12L))

    val mongoCursor = collection.find()
        .options {
      batchSize = 4
      maxScan = 50000
    }
        .invoke()
    println("mongoCursor = ${mongoCursor}")
    mongoCursor.each({document -> println(document)})
  }
}