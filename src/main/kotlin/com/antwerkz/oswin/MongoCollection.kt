package com.antwerkz.oswin

import org.mongodb.Document
import org.mongodb.operation.InsertRequest
import org.mongodb.operation.InsertOperation
import org.mongodb.MongoNamespace
import org.mongodb.codecs.CollectibleDocumentCodec
import org.mongodb.WriteResult
import org.mongodb.codecs.PrimitiveCodecs
import org.mongodb.codecs.ObjectIdGenerator
import org.mongodb.WriteConcern
import org.mongodb.ReadPreference
import org.mongodb.operation.DropCollectionOperation
import org.mongodb.CommandResult


class MongoCollection(val name: String, private val mongoClient: MongoClient, db: MongoDatabase) {
  public val namespace: MongoNamespace = MongoNamespace(db.name, name)

  fun insert(document: Document, writeConcern: WriteConcern = WriteConcern.ACKNOWLEDGED): WriteResult {
    return InsertOperation(namespace, true, writeConcern, listOf(InsertRequest(document)),
        CollectibleDocumentCodec(PrimitiveCodecs.createDefault(), ObjectIdGenerator()),
        mongoClient.getBufferProvider(), mongoClient.getSession(), false)
        .execute()!!

  }

  fun drop() {
    DropCollectionOperation(namespace, mongoClient.getBufferProvider(), mongoClient.getSession(), true)
      .execute()
  }

  fun find(vararg operators: QueryOperator, readPreference: ReadPreference = ReadPreference.primary()!!): Query {

    val query = Query(operators, this, mongoClient, readPreference)
    return query;
  }
}