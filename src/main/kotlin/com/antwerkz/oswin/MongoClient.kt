package com.antwerkz.oswin

import org.mongodb.ReadPreference
import org.mongodb.WriteConcern
import org.mongodb.connection.PowerOfTwoBufferPool
import org.mongodb.connection.BufferProvider
import org.mongodb.session.Session
import org.mongodb.connection.ServerAddress
import org.mongodb.connection.Cluster
import org.mongodb.session.ClusterSession
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import org.mongodb.connection.ServerSettings
import org.mongodb.connection.ConnectionPoolSettings
import org.mongodb.connection.SocketStreamFactory
import org.mongodb.connection.SocketSettings
import org.mongodb.connection.SSLSettings
import org.mongodb.connection.DefaultClusterFactory
import org.mongodb.connection.ClusterSettings
import org.mongodb.Document

public class MongoClient(
    val serverAddress: ServerAddress = ServerAddress("localhost", 27017),
    val readPreference: ReadPreference = ReadPreference.primary()!!,
    val writeConcern: WriteConcern = WriteConcern.ACKNOWLEDGED) {

  val powerOfTwoBufferPool = PowerOfTwoBufferPool()
  val executorService : ExecutorService = Executors.newCachedThreadPool()
  val cluster: Cluster;

  {
    val streamFactory = SocketStreamFactory(SocketSettings.builder()?.build(), SSLSettings.builder()?.build())

    val clusterBuilder = ClusterSettings.builder()
        ?.hosts(listOf(serverAddress))

    val cpSettingsBuilder = ConnectionPoolSettings.builder()
      ?.maxSize(20)
      ?.maxWaitQueueSize(20)

    cluster = DefaultClusterFactory().create(clusterBuilder?.build(), ServerSettings.builder()!!.build(),
        cpSettingsBuilder?.build(), streamFactory, streamFactory, listOf(), getBufferProvider(),
        null, null, null)!!
  }

  fun getDatabase(name: String): MongoDatabase {
    return MongoDatabase(this, name)
  }

  fun getBufferProvider(): BufferProvider {
    return powerOfTwoBufferPool
  }

  fun getSession(): Session {
    return ClusterSession(cluster, executorService)
  }
}

public fun document(vararg values: Pair<String, Any>): Document {
    val answer = Document()
    for (v in values) {
        answer.put(v.first, v.second)
    }
    return answer
}