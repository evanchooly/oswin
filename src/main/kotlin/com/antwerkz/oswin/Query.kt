package com.antwerkz.oswin

import org.mongodb.operation.QueryOperation
import org.mongodb.operation.Find
import org.mongodb.codecs.DocumentCodec
import org.mongodb.Document
import org.mongodb.MongoCursor
import org.mongodb.ReadPreference
import org.mongodb.operation.QueryFlag
import java.util.EnumSet

public class Query(val collection: MongoCollection, val client: MongoClient, val readPreference: ReadPreference) {
  private val codec = DocumentCodec()
  private var skip: Int = 0
  private var limit: Int = 0
  private var options: QueryOptions = QueryOptions()

  fun invoke(): MongoCursor<Document> {
    return QueryOperation(collection.namespace, Find().readPreference(readPreference), codec, codec, client.getBufferProvider(), client.getSession(), false)
        .execute()!!
  }

  fun options(init: QueryOptions.() -> Unit): Query {
    options.init()
    return this
  }
}

public fun <B> String.eq(that: B): Pair<String, B> = Pair(this, that)

fun <T> MongoCursor<Document>.each(function : (Document) -> T) : Unit {
  while(hasNext()) {
    function(next())
  }
}

public class QueryOptions {
  val queryFlags: EnumSet<QueryFlag> = EnumSet.noneOf(javaClass<QueryFlag>())
  var batchSize: Int = 0
  var maxScan: Int = 0
  var maxTimeMS: Long = 0
  var min: Document? = null
  var max: Document? = null
  var isolated: Boolean = false
  var comment: String? = null
  var explain: Boolean = false
  var hint: Document? = null
  var returnKey: Boolean = false
  var showDiskLoc: Boolean = false
  var snapshot: Boolean = false
}

