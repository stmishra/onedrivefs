import com.github.fge.filesystem.driver.FileSystemDriver
import com.github.fge.filesystem.filestore.FileStoreBase
import com.github.fge.filesystem.path.PathElements
import com.github.fge.filesystem.path.PathElementsFactory
import com.github.fge.filesystem.path.matchers.PathMatcherProvider
import com.github.fge.filesystem.provider.FileSystemRepositoryBase
import java.net.URI
import java.nio.file.attribute.FileAttributeView

class OnedriveFSRepository(scheme: String) : FileSystemRepositoryBase(scheme) {
    val client :OnedriveOauthClient  = OnedriveOauthClient()

    override fun createDriver(uri: URI?, env: MutableMap<String, *>?): FileSystemDriver {
       return this.createOnedriveFileSystemDriver(client.connect()!!)
     }

    private fun createOnedriveFileSystemDriver (accessToken: String ): OnedriveFileSystemDriver {
        val uri = URI.create("ondedrive://foo/")
        val pathElementsFactory = object : PathElementsFactory("://" ,"/", ".."){
            override fun isSelf(name: String?): Boolean {
                return false;
            }

            override fun rootAndNames(path: String?): Array<String> {
                println("Root is " + path)
                return path!!.split("://").toTypedArray();

            }

            override fun isParent(name: String?): Boolean {
                return false;
            }

            override fun getRootPathElements(): PathElements {
                return toPathElements("onedrive://foo/")
            }

            override fun splitNames(names: String?): Array<String> {
                return names!!.split("/").toTypedArray();
            }

            override fun isValidName(name: String?): Boolean {
                return true;
            }

            override fun isAbsolute(pathElements: PathElements?): Boolean {
                return true;
            }
        }
        val fileStore = object : FileStoreBase("onedrive_file_store", false) {
            override fun getTotalSpace(): Long {
                return 200000L;
            }

            override fun getUsableSpace(): Long {
                return 100000L
            }

            override fun supportsFileAttributeView(type: Class<out FileAttributeView>?): Boolean {
                return false;
            }

            override fun supportsFileAttributeView(name: String?): Boolean {
                return false;
            }

            override fun getUnallocatedSpace(): Long {
                return 10000L
            }
        }

        val pathMatcherProvider = PathMatcherProvider()
        val fsDriver = OnedriveFileSystemDriver(client,uri, pathElementsFactory,fileStore, pathMatcherProvider)
        return fsDriver
    }
}
