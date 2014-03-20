package com.antwerkz.kongo

import org.mongodb.connection.Connection
import org.bson.ByteBuf
import org.mongodb.connection.ResponseBuffers
import org.mongodb.connection.SingleResultCallback

public class KongoConnection : Connection {
  override fun sendMessage(byteBuffers: List<ByteBuf>?, lastRequestId: Int) {
    throw UnsupportedOperationException()
  }
  override fun receiveMessage(responseTo: Int): ResponseBuffers? {
    throw UnsupportedOperationException()
  }
  override fun sendMessageAsync(byteBuffers: List<ByteBuf>?, lastRequestId: Int, callback: SingleResultCallback<Void>?) {
    throw UnsupportedOperationException()
  }
  override fun receiveMessageAsync(responseTo: Int, callback: SingleResultCallback<ResponseBuffers>?) {
    throw UnsupportedOperationException()
  }
  override fun getServerAddress(): org.mongodb.connection.ServerAddress? {
    throw UnsupportedOperationException()
  }
  override fun getId(): String? {
    throw UnsupportedOperationException()
  }
  override fun close() {
  }
  override fun isClosed(): Boolean {
    throw UnsupportedOperationException()
  }
}

