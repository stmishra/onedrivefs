import khttp.extensions.fileLike
import khttp.responses.Response
import java.util.*

class OnedriveOauthClient {
    val CLIENT_ID =  "YOUR CLIENT ID HERE"
    val REDIRECT_URI = "https://login.microsoftonline.com/common/oauth2/nativeclient"

    val oauthUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize?"+
   "client_id=" + CLIENT_ID +
    "&response_type=code"+
    "&redirect_uri=" + REDIRECT_URI +
    "&response_mode=query"+
    "&scope=files.readwrite.all"+
    "&state=12345"
    var accessToken: String? = null;

    fun connect (): String? {
        val response = khttp.get(oauthUrl);
        val inputScanner = Scanner(System.`in`)
        println("===" + "Azure AD Authentication Workflow")
        println("Fetching Authorization URL")
        val authorizationUrl = response.url
        println("Got the auth URL")
        println("Now go and authorize fs thingies here")
        println(authorizationUrl)
        println("An paste the auth code here ")
        println(">>")
        val code = inputScanner.nextLine()
        // Now make a request for the accessToken.
        val accessTokenUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token"
        val response2 = oAuthPost (url =accessTokenUrl,

                data = mapOf("client_id" to  CLIENT_ID,
                        "scope" to "files.readwrite.all",
                        "code" to code ,
                        "redirect_uri" to "https://login.microsoftonline.com/common/oauth2/nativeclient",
                        "grant_type" to "authorization_code"
                        ),dontSendBearer=true)
        val responseObject = response2.jsonObject
        this.accessToken = responseObject.getString("access_token")
        return this.accessToken
    }

    fun oAuthPost(
            url:String,
            data : Map<String,String>, dontSendBearer: Boolean =false) : Response {
        if (dontSendBearer)
           return khttp.post(url, data = data)

        val headers = mapOf("Authorization" to "Bearer " + accessToken)
        return khttp.post(url = url, data=data,headers = headers,files = listOf("Hello,World".fileLike("hello.csv")))
    }

    fun oAuthPostWithFileUpload(
            url:String,
            data : Map<String,String>, dontSendBearer: Boolean =false,
            fileContents:String? = null,fileName:String = "Default") : Response {

        if (dontSendBearer)
            return khttp.post(url, data = data)

        val headers = mapOf("Authorization" to "Bearer " + accessToken, "Content-Type" to "text/plain",
                "Content-Disposition" to "related")

        return khttp.put(url = url, headers = headers,files = listOf(fileContents!!.fileLike(fileName)))
    }

    fun oAuthGet(url: String, data : Map<String, String>) : Response {
        val headers = mapOf("Authorization" to "Bearer " + accessToken)
        return khttp.get(url = url, data=data,headers = headers)
    }
}


fun main(args: Array<String>){
    val client = OnedriveOauthClient()
    client.connect()
    val response = client.oAuthGet("https://graph.microsoft.com/v1.0/me/drive/root/children",mapOf())
    println(response.text)

}
