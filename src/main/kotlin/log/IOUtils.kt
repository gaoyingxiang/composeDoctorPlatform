package log

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

object IOUtils {
    fun copy(input: InputStream?, output: OutputStream): Int {
        val count: Long = copyLarge(input, output)
        return if (count > 2147483647L) -1 else count.toInt()
    }

    private fun copyLarge(input: InputStream?, output: OutputStream): Long {
        return copy(input, output, 4096)
    }

    private fun copy(input: InputStream?, output: OutputStream, bufferSize: Int): Long {
        return copyLarge(input, output, ByteArray(bufferSize))
    }

    fun copyLarge(input: InputStream?, output: OutputStream, buffer: ByteArray?): Long {
        var n: Int = -1
        var count: Long = 0L
        while (-1 != input?.read(buffer).also { n = it?:-1 }) {
            output.write(buffer, 0, n)
            count += n.toLong()
        }
        return count
    }

    fun toByteArray(input: InputStream?): ByteArray? {
        val output = ByteArrayOutputStream()
        var var2: Throwable? = null
        val var3: ByteArray
        try {
            copy(input, output as OutputStream)
            var3 = output.toByteArray()
        } catch (var12: Throwable) {
            var2 = var12
            throw var12
        } finally {
            if (output != null) {
                if (var2 != null) {
                    try {
                        output.close()
                    } catch (var11: Throwable) {
                        var2.addSuppressed(var11)
                    }
                } else {
                    output.close()
                }
            }
        }
        return var3
    }
}