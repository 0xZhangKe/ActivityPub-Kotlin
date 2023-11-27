package com.zhangke.activitypub.utils

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.InputStream

class ProgressRequestBody(
    private val fileSize: Long,
    private val inputStream: InputStream,
    private val fileMediaType: String?,
    private val progressCallback: (Float) -> Unit,
) : RequestBody() {

    override fun contentType(): MediaType? {
        return fileMediaType?.toMediaTypeOrNull()
    }

    override fun writeTo(sink: BufferedSink) {
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var uploaded: Long = 0
        var read: Int
        read = inputStream.read(buffer)
        while (read != -1) {
            uploaded += read.toLong()
            progressCallback(uploaded.toFloat() / fileSize)
            sink.write(buffer, 0, read)
            read = inputStream.read(buffer)
        }
    }
}
