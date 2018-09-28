import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.IOException
import java.nio.file.Path
import java.util.concurrent.atomic.AtomicBoolean


class OnedriveOutputStream(val request: OnedriveOauthClient, path: String) : OutputStream() {
    var outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
    val path = path

    override fun write(b: Int) {
        outputStream.write(b)
    }

    @Throws(IOException::class)
    override fun flush() {
        outputStream.flush()
    }

    val closeCalled = AtomicBoolean(false)
    @Throws(IOException::class)
    override fun close() {
        /*
         * Reentrancy: check if .close() has been called already...
         */
        if (closeCalled.getAndSet(true))
            return
        println ("path " + path)

        val response  = request.oAuthPostWithFileUpload("https://graph.microsoft.com/v1.0/me/drive/root:/"+path+":/content",mapOf(),fileContents = outputStream.toString(),fileName=path)
        println(response.text)
    }

}