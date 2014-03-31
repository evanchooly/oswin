package com.antwerkz

import com.antwerkz.oswin.MongoClient

import org.testng.annotations.Test as test
import com.antwerkz.oswin.document
import com.antwerkz.oswin.eq
import com.antwerkz.oswin.and
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import com.antwerkz.oswin.lte
import org.mongodb.MongoCursor
import org.mongodb.Document
import com.antwerkz.oswin.each
import com.antwerkz.oswin.or
import com.antwerkz.oswin.lt
import com.antwerkz.oswin.gt

public class MongoClientTest {
  test fun insert() {
    val mongoClient = MongoClient()

    val collection = mongoClient.getDatabase("oswin").getCollection("test")

    collection.drop()

    for (i in 1..20) {
      collection.insert(document("_id" to i, "a" to "b", "number" to i))
    }

    assertEquals(document("a" to "b", "_id" to 4, "number" to 4), collection.find("number" eq 4)
        .invoke().next())

    assertFalse(collection.find(and("number" eq 12L, "a" eq "bed"))
        .invoke().hasNext())

    assertEquals(13, count(collection.find(and("number" lte 13, "a" eq "b"))
        .invoke()))

    assertEquals(13, count(collection.find(or("number" lt 13, "number" gt 19))
        .invoke()))
  }

  fun count(mongoCursor: MongoCursor<Document>): Int {
    var count = 0
    mongoCursor.each({ count++ })
    return count
  }
}