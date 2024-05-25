import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://www.bitmesra.ac.in/"

fun main() = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getUnsafeOkHttpClient().build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    val api = retrofit.create(Api::class.java)
    val task = CoroutineScope(Dispatchers.IO).launch {
        val response = api.getPageResponse()
        getFacultyList(response)
    }
    task.join()
}

fun getFacultyList(context: String) {
    val doc = Jsoup.parse(context)
    val element = doc.select(".faculty-shortdesc")
    for (i in element) {
        getFacultyDetails(i)
        println("-------------------------------------------------")
    }
}

fun getFacultyDetails(e: Element) {
    val name = e.getElementsByClass("profile-name-faculty").firstOrNull()?.text() ?: "N/A"
    val profileData = e.getElementsByClass("profile-data-faculty").firstOrNull()?.text() ?: "N/A"
    val email = e.select(".profile-name-faculty-mail a").firstOrNull()?.attr("href")?.removePrefix("mailto:") ?: "N/A"
    val imageUrl = e.select(".faculty-image img").firstOrNull()?.attr("src") ?: "N/A"
    val areaOfInterest = e.select(".faculty-info.area-interest p").firstOrNull()?.text() ?: "N/A"
    val profileUrl = e.select(".view-profile-button a").firstOrNull()?.attr("href") ?: "N/A"
    println(
        """
        $name , $profileData , ${email.trim()}
        $areaOfInterest
        ${if (imageUrl != "N/A") "$BASE_URL$imageUrl" else "N/A"}
        ${if (profileUrl != "N/A") "$BASE_URL$profileUrl" else "N/A"}
    """.trimIndent()
    )
}

interface Api {
    @GET("Show_Faculty_List?cid=7&deptid=145")
    suspend fun getPageResponse(): String
}