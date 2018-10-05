package at.htl.nvs.webcamscraperapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadVideo()
    }

    private fun loadVideo() {
        thread {
            val mp4 = scrapLink() //Network access only possible in thread
            runOnUiThread { //Ui elements only editable on ui thread
                videoView.setVideoPath(mp4)
                videoView.setMediaController(MediaController(this))
                videoView.requestFocus()
                videoView.start()
            }
        }
    }

    private fun scrapLink(): String {
        //make get request to url
        val document = Jsoup.connect("https://webtv.feratel.com/webtv/?cam=5132&design=v3&c0=0&c2=1&lg=en&s=0")
                .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0") //Use this to act like human (site does not allow bots)
                .get()
        val video = document.getElementById("fer_video") //get video element
        val source = video.select("source").attr("src"); //get source element and lookup src attribute
        return source
    }
}
