package at.htl.nvs.kotlinwebscraperserver

import org.jsoup.Jsoup

class WebcamScraper {

    fun scrap(): String {
        //make get request to url
        val document = Jsoup.connect("https://webtv.feratel.com/webtv/?cam=5132&design=v3&c0=0&c2=1&lg=en&s=0")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0") //Use this to act like human (site does not allow bots)
                .get()
        val video = document.getElementById("fer_video") //get video element
        val source = video.select("source").attr("src"); //get source element and lookup src attribute
        return source
    }
}