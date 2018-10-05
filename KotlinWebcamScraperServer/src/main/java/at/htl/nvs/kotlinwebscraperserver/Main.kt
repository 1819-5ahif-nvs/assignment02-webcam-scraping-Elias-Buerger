package at.htl.nvs.kotlinwebscraperserver

import java.net.ServerSocket

fun main(args: Array<String>) {
    val serverSocket = ServerSocket(80) //start http server
    println("Server started")
    println("Listening for connections on port : 80 ...")

    while (true) {
        val server = HTTPServer(serverSocket.accept()) //accept connections
        val thread = Thread(server)
        thread.start() //handle requests async
    }
}