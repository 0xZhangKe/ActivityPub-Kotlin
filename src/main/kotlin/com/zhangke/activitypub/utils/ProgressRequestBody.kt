package com.zhangke.activitypub.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.ByteArrayInputStream

class ProgressRequestBody(
    private val fileSize: Long,
    private val byteArray: ByteArray,
    private val fileMediaType: String?,
    private val progressCallback: (Float) -> Unit,
) : RequestBody() {

    override fun contentType(): MediaType? {
        return fileMediaType?.toMediaTypeOrNull()
    }

    override fun contentLength(): Long {
        return byteArray.size.toLong()
    }

    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var uploaded: Long = 0
        var read: Int
        val inputStream = ByteArrayInputStream(byteArray)
        read = inputStream.read(buffer)
        val notifyScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
        while (read != -1) {
            uploaded += read.toLong()
            notifyScope.launch(Dispatchers.Main) {
                progressCallback(uploaded.toFloat() / fileSize)
            }
            sink.write(buffer, 0, read)
            read = inputStream.read(buffer)
        }
        notifyScope.cancel()
    }
}
