package at.htl.nvs.kotlinwebscraperserver

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.Date
import java.util.StringTokenizer

class HTTPServer(private val connection: Socket) : Runnable {

    override fun run() {
        while (!connection.isClosed) { //only stop listening, if client has closed connection
            var scraper = WebcamScraper()
            var reader = BufferedReader(InputStreamReader(connection.getInputStream())) //to read request
            var writer = PrintWriter(connection.getOutputStream()) //to write response

            //Use tokenizer instead of splitang at space / tab / ...
            val input = reader.readLine()
            val parse = StringTokenizer(input)
            val method = parse.nextToken().toUpperCase()
            if (method == "GET") { //Only respond to get requests
                var html = "<!DOCTYPE html><html><body><video controls autoplay><source src=\"${scraper.scrap()}\"></source></video></body></html>"
                //Write Header
                writer.println("HTTP/1.1 200 OK")
                writer.println("Server: Java HTTP Server by Elias Buerger : 1.0")
                writer.println("Date: " + Date())
                writer.println("Content-type: text/html")
                writer.println("Content-length: ${html.length}")
                //Write body
                writer.println() //Split between header and body
                writer.println(html)
                //Send response
                writer.flush()
            }
        }
    }
}