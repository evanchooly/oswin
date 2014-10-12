package com.antwerkz.oswin

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