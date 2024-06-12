package com.bz.movies.kmp.network.utils

import java.io.IOException
import java.net.InetAddress
import java.net.Socket
import java.net.UnknownHostException
import javax.net.SocketFactory

// Create custom SocketFactory that avoid raising untagged socket detected with enabled strict mode
// https://github.com/square/okhttp/issues/3537
open class DelegatingSocketFactory(
    private val delegate: SocketFactory,
    private val configureSocket: (Socket) -> Socket,
) : SocketFactory() {
    @Throws(IOException::class)
    override fun createSocket(): Socket {
        val socket: Socket = delegate.createSocket()
        return configureSocket(socket)
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(
        host: String?,
        port: Int,
    ): Socket {
        val socket: Socket = delegate.createSocket(host, port)
        return configureSocket(socket)
    }

    @Throws(IOException::class, UnknownHostException::class)
    override fun createSocket(
        host: String?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int,
    ): Socket {
        val socket: Socket = delegate.createSocket(host, port, localAddress, localPort)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(
        host: InetAddress?,
        port: Int,
    ): Socket {
        val socket: Socket = delegate.createSocket(host, port)
        return configureSocket(socket)
    }

    @Throws(IOException::class)
    override fun createSocket(
        host: InetAddress?,
        port: Int,
        localAddress: InetAddress?,
        localPort: Int,
    ): Socket {
        val socket: Socket = delegate.createSocket(host, port, localAddress, localPort)
        return configureSocket(socket)
    }
}
