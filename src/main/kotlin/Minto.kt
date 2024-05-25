import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

const val BASE_URL2 = "https://en.wikipedia.org/"

fun main() = runBlocking {
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL2)
        .client(getUnsafeOkHttpClient().build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    val api = retrofit.create(Api::class.java)
    val task = CoroutineScope(Dispatchers.IO).launch {
        val response = api.getPageResponse()
        getDescription(response)
    }
    task.join()
}
fun getDescription(response : String){
    val doc= Jsoup.parse(response)
    println(doc.title())
}
//fun getFacultyList(context: String) {
//    val doc = Jsoup.parse(context)
//    val element = doc.select(".faculty-shortdesc")
//    for (i in element) {
//        getFacultyDetails(i)
//        println("-------------------------------------------------")
//    }
//}

//fun getFacultyDetails(e: Element) {
//    val name = e.getElementsByClass("profile-name-faculty").firstOrNull()?.text() ?: "N/A"
//    val profileData = e.getElementsByClass("profile-data-faculty").firstOrNull()?.text() ?: "N/A"
//    val email = e.select(".profile-name-faculty-mail a").firstOrNull()?.attr("href")?.removePrefix("mailto:") ?: "N/A"
//    val imageUrl = e.select(".faculty-image img").firstOrNull()?.attr("src") ?: "N/A"
//    val areaOfInterest = e.select(".faculty-info.area-interest p").firstOrNull()?.text() ?: "N/A"
//    val profileUrl = e.select(".view-profile-button a").firstOrNull()?.attr("href") ?: "N/A"
//    println(
//        """
//        $name , $profileData , ${email.trim()}
//        $areaOfInterest
//        ${if (imageUrl != "N/A") "$BASE_URL$imageUrl" else "N/A"}
//        ${if (profileUrl != "N/A") "$BASE_URL$profileUrl" else "N/A"}
//    """.trimIndent()
//    )
//}

interface Api2 {
    @GET("wiki/Shakya")
    suspend fun getPageResponse(): String
}