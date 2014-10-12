package com.antwerkz.oswin

import org.mongodb.ReadPreference
import java.util.ArrayList

class QueryBuilder(val collection: MongoCollection, val client: MongoClient,
    val readPreference: ReadPreference = ReadPreference.primary()!!) {

  private val operators = ArrayList<QueryOperator>();

  QueryBuilder() {

  }

  public fun and(vararg operations: QueryOperator): QueryOperator {

  }
  public fun or(vararg operations: QueryOperator): QueryOperator = OrOperator(operations)

  public fun <B> eq(that: B): QueryOperator = EqualOperator(this, that)
  public fun <B> gt(that: B): QueryOperator = GreaterThanOperator(this, that)
  public fun <B> gte(that: B): QueryOperator = GreaterThanEqualOperator(this, that)
  public fun <B> lt(that: B): QueryOperator = LessThanOperator(this, that)
  public fun <B> lte(that: B): QueryOperator = LessThanEqualOperator(this, that)
  public fun <B> ne(that: B): QueryOperator = NotEqualOperator(this, that)
  public fun <B> foundIn(that: Array<B>): QueryOperator = InOperator(this, that)
  public fun <B> nin(that: Array<B>): QueryOperator = NotInOperator(this, that)
}
