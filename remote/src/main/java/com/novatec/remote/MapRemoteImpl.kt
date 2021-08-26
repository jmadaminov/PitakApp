package com.novatec.remote

import com.novatec.core.Constants.mapFilesLocation
import com.novatec.core.Constants.mapZIPFilesLocation
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.data.repository.MapRemote
import okhttp3.ResponseBody
import java.io.*
import java.util.zip.ZipInputStream
import javax.inject.Inject

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BufferooRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class MapRemoteImpl @Inject constructor(
    private val api: MapApiService) : MapRemote {

    override suspend fun downloadmaps(targetDir: String): ResultWrapper<String> {
        return try {
            val response = api.downloadFile()
            ResultWrapper.Success(saveFile(response.body(), targetDir))
        } catch (e: Exception) {
            ErrorWrapper.SystemError(e)
        }
    }


    fun saveFile(body: ResponseBody?,
                 targetDir: String): String {


        if (body == null)
            return ""
        var input: InputStream? = null
        try {
            input = body.byteStream()
            //val file = File(getCacheDir(), "cacheFileAppeal.srl")
            val fos = FileOutputStream(targetDir + mapZIPFilesLocation)
            fos.use { output ->
                val buffer = ByteArray(4 * 1024) // or other buffer size
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
            return targetDir
        } catch (e: Exception) {
            println("saveFile $e")
        } finally {
            input?.close()
            unzip(targetDir + mapZIPFilesLocation,
                  targetDir + mapFilesLocation)
        }
        return ""
    }


    @Throws(IOException::class)
    fun unzip(zipFilePath: String?, destDirectory: String) {
        val zipIn = ZipInputStream(FileInputStream(zipFilePath))
        var entry = zipIn.nextEntry
        // iterates over entries in the zip file
        while (entry != null) {
            val filePath = destDirectory + entry.name
            if (!entry.isDirectory) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath)
            } else {
                // if the entry is a directory, make the directory
                val dir = File(filePath)
                dir.mkdir()
            }
            zipIn.closeEntry()
            entry = zipIn.nextEntry
        }
        zipIn.close()
        File(zipFilePath).delete()
    }

    /**
     * Extracts a zip entry (file entry)
     */
    @Throws(IOException::class)
    fun extractFile(zipIn: ZipInputStream, filePath: String?) {
        val bos = BufferedOutputStream(FileOutputStream(filePath))
        val bytesIn = ByteArray(4096)
        var read = 0
        while (zipIn.read(bytesIn).also { read = it } != -1) {
            bos.write(bytesIn, 0, read)
        }
        bos.close()
    }

}