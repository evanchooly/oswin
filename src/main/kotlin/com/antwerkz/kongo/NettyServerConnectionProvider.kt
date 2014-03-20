package com.antwerkz.kongo

import org.mongodb.connection.ServerDescription;
import org.mongodb.session.ServerConnectionProvider
import org.mongodb.connection.ServerDescription
import org.mongodb.connection.Connection
import org.mongodb.MongoFuture
import io.netty.bootstrap.Bootstrap
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.channel.ChannelOption
import io.netty.channel.socket.SocketChannel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import org.bson.ByteBuf
import org.mongodb.connection.ResponseBuffers
import org.mongodb.connection.SingleResultCallback
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelHandlerAdapter
import org.mongodb.connection.ServerAddress
import org.mongodb.connection.ServerConnectionState

public class NettyServerConnectionProvider(private val serverAddress: ServerAddress) : ServerConnectionProvider {
  private val b: Bootstrap

  {
    b = Bootstrap(); // (1)
    b.group(NioEventLoopGroup()); // (2)
    b.channel(javaClass<NioSocketChannel>()); // (3)
    b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
    b.handler(MongoChannelInitializer());

    // Start the client.

    println("what up?")
  }

  override fun getServerDescription(): ServerDescription? {
    return ServerDescription.builder()
      ?.address(serverAddress)
      ?.state(ServerConnectionState.CONNECTING)
      ?.build()
  }

  override fun getConnection(): Connection {
    val channelFuture = b.connect(serverAddress.getHost(), serverAddress.getPort())
    val channel = channelFuture?.sync()?.channel()

    return KongoConnection()
  }

  override fun getConnectionAsync(): MongoFuture<Connection>? {
    throw UnsupportedOperationException()
  }

}

class MongoChannelInitializer() : ChannelInitializer<SocketChannel>() {
  override fun initChannel(ch: SocketChannel?) {
    ch?.pipeline()?.addLast(object : ChannelInboundHandlerAdapter() {

      override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        super.channelRead(ctx, msg)
      }
    });
  }
}