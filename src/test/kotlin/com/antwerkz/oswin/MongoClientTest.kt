package com.antwerkz

import com.antwerkz.oswin.MongoClient

import org.testng.annotations.Test as test
import com.antwerkz.oswin.document
import com.antwerkz.oswin.eq
import com.antwerkz.oswin.each
import kotlin.test.assertEquals

public class MongoClientTest {
  test fun insert() {
    val mongoClient = MongoClient()

    val collection = mongoClient.getDatabase("oswin").getCollection("test")

    collection.drop()

    collection.insert(document("a" to "b", "number" to 12L))

    val mongoCursor = collection.find("number" eq 12L, "a" eq "b")
        .options {
      batchSize = 4
      maxScan = 50000
    }
        .invoke()
    assertEquals(1, mongoCursor.count({ true }))
  }
}