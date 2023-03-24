package log

import java.io.*
import java.nio.BufferUnderflowException
import java.nio.ByteBuffer
import java.util.zip.GZIPInputStream
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class LoganParser2(stream: InputStream, file:File) {
    private val ENCRYPT_CONTENT_START = '\u0001'
    private val AES_ALGORITHM_TYPE = "AES/CBC/NoPadding"
    private var wrap: ByteBuffer? = null
    private var fileOutputStream: FileOutputStream? = null
    init {
        try {
            wrap = ByteBuffer.wrap(IOUtils.toByteArray(stream))
            fileOutputStream = FileOutputStream(file)
        } catch (e: IOException) {
        }
    }

    fun process(): String? {
        while (wrap!!.hasRemaining()) {
            while (wrap!!.get() == ENCRYPT_CONTENT_START.toByte()) {
                val encrypt = ByteArray(wrap!!.int)
                if (!tryGetEncryptContent(encrypt) || !decryptAndAppendFile(encrypt)) {
                    return "失败"
                }
            }
        }
        return "成功"
    }

    private fun tryGetEncryptContent(encrypt: ByteArray): Boolean {
        try {
            wrap!![encrypt]
        } catch (e: BufferUnderflowException) {
//            LOGGER.error(e);
            return false
        }
        return true
    }

    private fun decryptAndAppendFile(encrypt: ByteArray): Boolean {
        var result = false
        try {
            val aesEncryptCipher = Cipher.getInstance(AES_ALGORITHM_TYPE)
            val secureParam: Tuple<String, String> = getSecureParam()
            val secretKeySpec = SecretKeySpec(secureParam.first?.toByteArray(), "AES")
            aesEncryptCipher.init(
                Cipher.DECRYPT_MODE, secretKeySpec, IvParameterSpec(secureParam.second?.toByteArray())
            )
            val compressed = aesEncryptCipher?.doFinal(encrypt)
            val plainText: ByteArray = decompress(compressed)
            result = true
            fileOutputStream?.write(plainText)
            fileOutputStream?.flush()
        } catch (e: java.lang.Exception) {
//            LOGGER.error(e);
        }
        return result
    }

    private fun decompress(contentBytes: ByteArray?): ByteArray{
        try {
            ByteArrayOutputStream().use { out ->
                IOUtils.copy(GZIPInputStream(ByteArrayInputStream(contentBytes)), out)
                return out.toByteArray()
            }
        } catch (e: IOException) {
        }
        return ByteArray(0)
    }

    private fun getSecureParam(): Tuple<String, String> {
        val tuple: Tuple<String, String> = Tuple()
        //        配置的解密KEY
        tuple.first = "0123456789012345"
        tuple.second = "0123456789012345"
        return tuple
    }
}