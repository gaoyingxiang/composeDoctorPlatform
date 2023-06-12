package log

import androidx.compose.ui.awt.ComposeWindow
import com.alibaba.fastjson.JSONObject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.io.*
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import javax.swing.JFileChooser
import javax.swing.SwingUtilities
import javax.swing.UIManager

class Helper {
    fun decodeFile(inputFile:File,dir:String):MutableList<LoganLogItem> {
        val dataLs = mutableListOf<LoganLogItem>()
        //输出文件地址
        val outPutFile = File("${dir}\\decode.log")
        if (!outPutFile.exists()) {
            outPutFile.createNewFile()
        }else{
            outPutFile.delete()
            outPutFile.createNewFile()
        }
        //解析上传的文件。
        val loganProtocol = LoganParser2(FileInputStream(inputFile), outPutFile)
        val str = loganProtocol.process()

        try {
            FileInputStream(outPutFile).use { input ->
                BufferedReader(
                    InputStreamReader(
                        input,
                        StandardCharsets.UTF_8
                    )
                ).use { bufferedReader ->
                    dataLs.clear()
                    var str: String?
                    val j = 0L
                    while (bufferedReader.readLine().also { str = it } != null) {
                        str?.let { s->
                            kotlin.runCatching {
                                JSONObject.parseObject(JSONObject.toJSON(str).toString(),LoganLogItem::class.java).apply {
                                   l?.let { i->
                                       val time = try {
                                           i.toLong()
                                       }catch (e:Exception){
                                           0L
                                       }
                                       l = formatDateTimeForLong(time)
                                   }
                                }
                            }.onSuccess { item->
                                dataLs.add(item)
                            }.onFailure {
                                println(it)
                            }
                        }
                        println(str)
                    }
//                    //用完就删。
//                    if (outPutFile.exists()) {
//                        outPutFile.delete()
//                    }
                }
            }
        } catch (e: Exception) {
            println(e.message)
        }

        println(str)
        return dataLs
    }


    /**
     * 文件选择器
     */
     fun showFileSelector(
        onFileSelected: (String,File) -> Unit
    ) {
        JFileChooser().apply {
            //设置页面风格
            try {
                val lookAndFeel = UIManager.getSystemLookAndFeelClassName()
                UIManager.setLookAndFeel(lookAndFeel)
                SwingUtilities.updateComponentTreeUI(this)
            } catch (e: Throwable) {
                e.printStackTrace()
            }

            fileSelectionMode = JFileChooser.FILES_ONLY
            isMultiSelectionEnabled = false
//            fileFilter = FileNameExtensionFilter("文件过滤", *suffixList)
            val result = showOpenDialog(ComposeWindow())

            if (result == JFileChooser.APPROVE_OPTION) {
                val dir = this.currentDirectory
                val file = this.selectedFile
                println("Current apk dir: ${dir.absolutePath} ${dir.name}")
                println("Current apk name: ${file.absolutePath} ${file.name}")
                onFileSelected(dir.absolutePath,file)
            }
        }
    }

    /**
     * 时间转日期时间字符串
     * @param date
     * @return
     */
    private fun formatDateTimeForLong(date: Long?): String? {
        if (Objects.isNull(date)) {
            return ""
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val nowTime = Date(date!!)
        return sdf.format(nowTime)
    }
}