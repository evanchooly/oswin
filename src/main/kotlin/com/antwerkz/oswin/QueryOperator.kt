package com.antwerkz.oswin

import org.mongodb.Document

public fun <B> String.eq(that: B): QueryOperator = QueryOperator(this, that)

public open class QueryOperator(val field: Any, val operand: Any) {
  fun toDocument() : Document {
    return Document(field.toString(), operand)
  }
}

public class EqualOperator(field: String, operand: Any) : QueryOperator(field, operand)