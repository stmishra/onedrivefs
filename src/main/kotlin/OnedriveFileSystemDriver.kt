import com.github.fge.filesystem.driver.FileSystemDriverBase
import com.github.fge.filesystem.path.PathElementsFactory
import com.github.fge.filesystem.path.matchers.PathMatcherProvider
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URI
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileAttribute
import java.nio.file.attribute.FileAttributeView

class OnedriveFileSystemDriver(mService: OnedriveOauthClient, uri: URI?, pathElementsFactory: PathElementsFactory?, fileStore: FileStore?, pathMatcherProvider: PathMatcherProvider?) : FileSystemDriverBase(uri, pathElementsFactory, fileStore, pathMatcherProvider) {
    val mService = mService
    override fun checkAccess(path: Path?, vararg modes: AccessMode?) {
        println("Hello World!!")
        throw IOException("Doesnt exist yet.")
    }

    override fun newInputStream(path: Path?, vararg options: OpenOption?): InputStream {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun copy(source: Path?, target: Path?, vararg options: CopyOption?) {
        println(source)
        println(target)

    }

    override fun <V : FileAttributeView?> getFileAttributeView(path: Path?, type: Class<V>?, vararg options: LinkOption?): V? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isHidden(path: Path?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun close() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newDirectoryStream(dir: Path?, filter: DirectoryStream.Filter<in Path>?): DirectoryStream<Path> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(path: Path?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <A : BasicFileAttributes?> readAttributes(path: Path?, type: Class<A>?, vararg options: LinkOption?): A {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readAttributes(path: Path?, attributes: String?, vararg options: LinkOption?): MutableMap<String, Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newOutputStream(path: Path?, vararg options: OpenOption?): OutputStream {
         println("Path in newOutputStream is " + path)
        return OnedriveOutputStream(mService,path!!.fileName.toString())
        //This needs to be implemented to upload a file.
    }


    override fun setAttribute(path: Path?, attribute: String?, value: Any?, vararg options: LinkOption?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun move(source: Path?, target: Path?, vararg options: CopyOption?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createDirectory(dir: Path?, vararg attrs: FileAttribute<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}