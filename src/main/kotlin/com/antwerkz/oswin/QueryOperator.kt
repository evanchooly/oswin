package com.antwerkz.oswin

import org.mongodb.Document

public fun and(vararg operations: QueryOperator): QueryOperator = AndOperator(operations)
public fun or(vararg operations: QueryOperator): QueryOperator = OrOperator(operations)

public fun <B> String.eq(that: B): QueryOperator = EqualOperator(this, that)
public fun <B> String.gt(that: B): QueryOperator = GreaterThanOperator(this, that)
public fun <B> String.gte(that: B): QueryOperator = GreaterThanEqualOperator(this, that)
public fun <B> String.lt(that: B): QueryOperator = LessThanOperator(this, that)
public fun <B> String.lte(that: B): QueryOperator = LessThanEqualOperator(this, that)
public fun <B> String.ne(that: B): QueryOperator = NotEqualOperator(this, that)
public fun <B> String.foundIn(that: Array<B>): QueryOperator = InOperator(this, that)
public fun <B> String.nin(that: Array<B>): QueryOperator = NotInOperator(this, that)

//public val mod: String = "$mod"
//public val all: String = "$all"
//public val size: String = "$size"
//public val exists: String = "$exists"
//public val elem_match: String = "$elemMatch"

public trait QueryOperator {
  fun toDocument() : Document
}

public class EqualOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), operand)
  }
}

public class GreaterThanOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), Document("\$gt", operand));
  }
}

public class GreaterThanEqualOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), Document("\$gte", operand));
  }
}

public class LessThanOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), Document("\$lt", operand));
  }
}

public class LessThanEqualOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), Document("\$lte", operand));
  }
}
public class NotEqualOperator(private val field: String, private val operand: Any) : QueryOperator {
  override fun toDocument() : Document {
    return Document(field.toString(), Document("\$ne", operand));
  }
}

public class AndOperator(private val operators: Array<QueryOperator>  ) : QueryOperator {
  override fun toDocument(): Document {
    return Document("\$and", operators.map { it.toDocument() })
  }
}

public class OrOperator(private val operators: Array<QueryOperator>  ) : QueryOperator {
  override fun toDocument(): Document {
    return Document("\$or", operators.map { it.toDocument() })
  }
}

public class InOperator<B>(private val field: String, private val values: Array<B>) : QueryOperator {
  override fun toDocument(): Document {
    return Document(field, Document("\$in", values.map{it}))
  }
}
public class NotInOperator<B>(private val field: String, private val values: Array<B>  ) : QueryOperator {
  override fun toDocument(): Document {
    return Document(field, Document("\$nin", values.map{it}))
  }
}