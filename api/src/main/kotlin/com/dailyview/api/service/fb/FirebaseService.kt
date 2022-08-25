package com.dailyview.api.service.fb

import com.dailyview.convertPublicURL
import com.google.firebase.cloud.StorageClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Service
class FirebaseService(
    @Value("\${app.firebase-bucket}")
    val firebaseBucket: String
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun uploadFiles(file: MultipartFile, fileName: String): String {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)
        val content = ByteArrayInputStream(file.bytes)
        try {
            return content.use { bucket.create(fileName, it, file.contentType) }.mediaLink
                .convertPublicURL()
        } catch (e: Exception) {
            logger.warn("uploadFiles", e)
            throw e
        }
    }
}
