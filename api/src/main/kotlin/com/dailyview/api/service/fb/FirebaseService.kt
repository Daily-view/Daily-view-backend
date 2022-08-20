package com.dailyview.api.service.fb

import com.google.firebase.cloud.StorageClient
import java.io.ByteArrayInputStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FirebaseService(
    @Value("\${app.firebase-bucket}")
    val firebaseBucket: String
) {

    fun uploadFiles(file: MultipartFile, fileName: String): String {
        val bucket = StorageClient.getInstance().bucket(firebaseBucket)
        val content = ByteArrayInputStream(file.bytes)
        val blob = bucket.create(fileName, content, file.contentType)
        return blob.mediaLink
    }
}