import com.github.fge.filesystem.fs.GenericFileSystem
import sun.net.www.content.text.Generic
import java.net.URI
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

fun start():String {

    val uri = URI.create("onedrive://foo/")
    val repository = OnedriveFSRepository("onedrive")
    val provider = OnedriveFSProvider(repository)
    val fs = provider.newFileSystem(uri, mapOf("strr" to "num"))
    val src = Paths.get(System.getProperty("user.home"), "Example3.java");
    val dst = fs.getPath("onedrive://foo/newOne.java");
    Files.copy(src,dst)
    return "OK"
}

fun main(args : Array<String>) {
    start()
}

