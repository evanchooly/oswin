package com.antwerkz.oswin

import org.mongodb.operation.QueryOperation
import org.mongodb.operation.Find
import org.mongodb.codecs.DocumentCodec
import org.mongodb.Document
import org.mongodb.MongoCursor
import org.mongodb.ReadPreference
import org.mongodb.operation.QueryFlag
import java.util.EnumSet

public class Query(private val operators: Array<QueryOperator>, val collection: MongoCollection,
    val client: MongoClient, val readPreference: ReadPreference) {

  private val codec = DocumentCodec()
  private var skip: Int = 0
  private var limit: Int = 0
  private var options: QueryOptions = QueryOptions()

  fun invoke(): MongoCursor<Document> {
    var doc  = Document()
    operators.forEach { operator ->
      doc.putAll(operator.toDocument()) }
    return QueryOperation(collection.namespace, Find(doc).readPreference(readPreference), codec, codec)
        .execute(client.getSession())!!
  }

  fun options(init: QueryOptions.() -> Unit): Query {
    options.init()
    return this
  }
}

fun <T> MongoCursor<Document>.each(function: (Document) -> T): Unit {
  while (hasNext()) {
    function(next())
  }
}

